package no.sivertsensoftware.userregistration.config;

import static org.springframework.security.config.Customizer.withDefaults;

//import org.keycloak.authorization.client.util.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
//@EnableWebMvc
public class SecurityConfig {

    @Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

        http
            .csrf(cust -> cust.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(mvc.pattern(HttpMethod.GET, "/**")).hasAnyAuthority("SCOPE_userreg-read","SCOPE_userreg-write")
                //.requestMatchers(mvc.pattern(HttpMethod.GET, "/frontend/**")).hasAnyAuthority("SCOPE_userreg-read","SCOPE_userreg-write")
                .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/users/**")).hasAnyAuthority("SCOPE_userreg-read","SCOPE_userreg-write")
                .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/users/**")).hasAnyAuthority("SCOPE_userreg-write")
                .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/users/**")).hasAnyAuthority("SCOPE_userreg-write")
                .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/users/**")).hasAnyAuthority("SCOPE_userreg-write")

                //.requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("SCOPE_userreg-read","SCOPE_userreg-write")
                //.requestMatchers(HttpMethod.POST,"/api/users/**").hasAuthority("SCOPE_userreg-write")
                //.requestMatchers(HttpMethod.PUT,"/api/users/**").hasAuthority("SCOPE_userreg-write")
                //.requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAuthority("SCOPE_userreg-write")
                .anyRequest()
                .authenticated()
            )
            .oauth2Login(withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
            ))
            .logout(logout -> logout
				.logoutSuccessHandler(oidcLogoutSuccessHandler())
			);
        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
				new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);

		// Sets the location that the End-User's User Agent will be redirected to
		// after the logout has been performed at the Provider
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

		return oidcLogoutSuccessHandler;
	}

    /*
     * Original Security Filter Chain
     */
    // @Bean
    // SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    //     http
    //             .csrf(cust -> cust.disable())
    //             .cors(cors -> cors.disable())
    //             .sessionManagement((session) ->
    //                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests(authorizeRequests -> authorizeRequests
    //                 .requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("SCOPE_userreg-read","SCOPE_userreg-write")
    //                 .requestMatchers(HttpMethod.POST,"/api/users/**").hasAuthority("SCOPE_userreg-write")
    //                 .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAuthority("SCOPE_userreg-write")
    //                 .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAuthority("SCOPE_userreg-write")
    //                 //.requestMatchers("/login").permitAll()
    //                 //.requestMatchers("/logout").permitAll()
    //                 //.requestMatchers("/oauth2").permitAll()
    //                 .anyRequest()
    //                 .authenticated()
    //             )
    //             //.oauth2Login(withDefaults())
    //             // .logout((logout) ->
    //             //     logout.deleteCookies("remove")
    //             //     .invalidateHttpSession(false)
    //             //     .logoutUrl("/logout")
    //             //     .logoutSuccessUrl("/")
    //             //     .addLogoutHandler(keycloakLogoutHandler)
    //             // )
    //             .oauth2ResourceServer(oauth2 -> oauth2.jwt(
    //                     jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
    //             ));
    //     return http.build();
    // }

    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    //     return http.getSharedObject(AuthenticationManagerBuilder.class)
    //         .build();
    // }
}