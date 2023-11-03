package no.sivertsensoftware.userregistration.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USERS";

    @Autowired
    private OPAAuthorizationManager opaAuthorizationManager;

    @Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    MvcRequestMatcher.Builder mvc (HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception { //@Qualifier("opaWebClient")WebClient opaWebClient,

        http
            .csrf(cust -> cust.disable())
            .cors(cors -> cors.disable())

            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                //.requestMatchers(mvc.pattern(HttpMethod.GET, "/**")).hasAnyRole(USER,ADMIN)
                //.requestMatchers(HttpMethod.GET,"/**").access(opaAuthorizationManager)
                .requestMatchers(mvc.pattern(HttpMethod.GET, "/**")).access(opaAuthorizationManager)
                // .requestMatchers(mvc.pattern(HttpMethod.GET, "/login/oauth2/code/**")).hasAnyRole(USER,ADMIN)
                // .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/users/**")).hasAnyRole(USER,ADMIN)
                // .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/users/**")).hasRole(ADMIN)
                // .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/users/**")).hasRole(ADMIN)
                // .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/users/**")).hasRole(ADMIN)
                .anyRequest()
                .authenticated()
            )
            .oauth2Login(withDefaults())
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
            .logout(logout -> logout
				.logoutSuccessHandler(oidcLogoutSuccessHandler())
			);
        return (SecurityFilterChain) http.build();
    }

    // @Bean
	// public ServerLogoutSuccessHandler keycloakLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {
    //     OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
    //             new OidcClientInitiatedServerLogoutSuccessHandler(repository);
    //     oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
    //     return oidcLogoutSuccessHandler;
    // }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
				new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
		return oidcLogoutSuccessHandler;
	}


    /*
     * Scope to Roles converter for oauth2Login. User Realm Role -> Token Claim Name changed to "realm_access\.roles", Access Token Claim enabled.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        var claimName = "realm_access.roles";
        var preFix = "ROLE_";

        final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(claimName);
        grantedAuthoritiesConverter.setAuthorityPrefix(preFix);

        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /*
     * Scope to Roles converter for oauth2ResourceServer. User Realm Role -> Token Claim Name changed to "realm_access\.roles", UserInfo claim enabled
     */
    @Bean
    GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {   

        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            var authority = authorities.iterator().next();
            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                var userInfo = oidcUserAuthority.getUserInfo();
                if (userInfo.hasClaim("realm_access.roles")) {
                    var realmAccess = ((ClaimAccessor) userInfo).getClaimAsStringList("realm_access.roles");
                    mappedAuthorities.addAll(realmAccess.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList());
                }
            }
            return mappedAuthorities;
        };
    }

    // @Bean
    // public ReactiveAuthorizationManager<AuthorizationContext> opaAuthManager(WebClient opaWebClient) {
        
    //     return (auth, context) -> {
    //         return opaWebClient.post()
    //           .accept(MediaType.APPLICATION_JSON)
    //           .contentType(MediaType.APPLICATION_JSON)
    //           .body(toAuthorizationPayload(auth,context), Map.class)
    //           .exchangeToMono(this::toDecision);
    //     };
    // }

    // private Publisher<Map<String,Object>> toAuthorizationPayload(Mono<Authentication> auth, AuthorizationContext context) {
    //     return auth
    //       .defaultIfEmpty(new AnonymousAuthenticationToken("**ANONYMOUS**", new Object(), Arrays.asList(new SimpleGrantedAuthority("ANONYMOUS"))))
    //       .map( a -> {
              
    //         Map<String,String> headers = context.getExchange().getRequest()
    //             .getHeaders()
    //             .toSingleValueMap();
              
    //           Map<String,Object> attributes = ImmutableMap.<String,Object>builder()
    //             .put("principal",a.getName())
    //             .put("authorities",
    //                a.getAuthorities()
    //                  .stream()
    //                  .map(g -> g.getAuthority())
    //                  .collect(Collectors.toList()))
    //             .put("uri", context.getExchange().getRequest().getURI().getPath())
    //             .put("headers",headers)
    //             .build();
              
    //           Map<String,Object> input = ImmutableMap.<String,Object>builder()
    //             .put("input",attributes)
    //             .build();
             
    //           return input;
    //       });
    // }

    // private Mono<AuthorizationDecision> toDecision(ClientResponse response) {
        
    //     if ( !response.statusCode().is2xxSuccessful()) {
    //         return Mono.just(new AuthorizationDecision(false));
    //     }
        
    //     return response
    //      .bodyToMono(ObjectNode.class)
    //      .map(node -> {
    //          boolean authorized = node.path("result").path("authorized").asBoolean(false);
    //          return new AuthorizationDecision(authorized);
    //      });
        
    // }
}