package no.sivertsensoftware.userregistration.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	String jwkSetUri;

    @Autowired
    private OPAAuthorizationManager opaAuthorizationManager;

    @Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    MvcRequestMatcher.Builder mvc (HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @SuppressWarnings("removal")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

        http
            .csrf(cust -> cust.disable())
            .cors(cors -> cors.disable())

            .authorizeHttpRequests(authorizeRequests -> authorizeRequests

                .requestMatchers(mvc.pattern(HttpMethod.GET, "/**")).access(opaAuthorizationManager)
                .requestMatchers(mvc.pattern(HttpMethod.GET, "/login/oauth2/code/**")).access(opaAuthorizationManager)
                .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/users/**")).access(opaAuthorizationManager)
                .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/users/**")).access(opaAuthorizationManager)
                .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/users/**")).access(opaAuthorizationManager)
                .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/users/**")).access(opaAuthorizationManager)
                .anyRequest().authenticated()
            )
            .oauth2Login((oauth2Login) -> oauth2Login
                .userInfoEndpoint((userInfo) -> userInfo
                    .userAuthoritiesMapper(userAuthoritiesMapperForKeycloak())
                )
		    )
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
		OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
		return oidcLogoutSuccessHandler;
	}

    /*
     * Scope to Roles converter for oauth2Login. User Realm Role -> Token Claim Name changed to "realm_access\.roles", Access Token Claim enabled.
     */
    // @Bean
    // JwtAuthenticationConverter jwtAuthenticationConverter() {
    //     System.out.println("--------- SecurityConfig I'm entering jwtAuthenticationConverter.");

    //     var claimName = "realm_access.roles";
    //     var preFix = "ROLE_";

    //     final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    //     grantedAuthoritiesConverter.setAuthoritiesClaimName(claimName);
    //     grantedAuthoritiesConverter.setAuthorityPrefix(preFix);

    //     final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    //     jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    //     return jwtAuthenticationConverter;
    // }

    /*
     * Scope to Roles converter for oauth2ResourceServer. User Realm Role -> Token Claim Name changed to "realm_access\.roles", UserInfo claim enabled
     * Mapper for Roles
     */
    private GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            
            // Saving a copy authorities for later use
            //customOidcUserAuthority.setCustomAuthority(authorities);
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
    /*
     * Mapper for SCOPEs
     */

    // private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
    //     return (authorities) -> {
    //         Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

    //         authorities.forEach((authority) -> {
    //             GrantedAuthority mappedAuthority;

    //             if (authority instanceof OidcUserAuthority) {
    //                 OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
    //                 mappedAuthority = new OidcUserAuthority("OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
    //             } else if (authority instanceof OAuth2UserAuthority) {
    //                 OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
    //                 mappedAuthority = new OAuth2UserAuthority("OAUTH2_USER", userAuthority.getAttributes());
    //             } else {
    //                 mappedAuthority = authority;
    //             }
    //             mappedAuthorities.add(mappedAuthority);
    //         });
    //         return mappedAuthorities;
    //     };
    // }
}