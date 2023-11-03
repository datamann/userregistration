// package no.sivertsensoftware.userregistration.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.reactive.function.client.WebClient;

// @Configuration
// @EnableConfigurationProperties(OpaProperties.class)
// public class OpaConfiguration {

//     private final OpaProperties opaProperties;
    
//     public OpaConfiguration(OpaProperties opaProperties) {
//         this.opaProperties = opaProperties;
//     }

//     @Bean
//     public WebClient opaWebClient(WebClient.Builder builder) {
        
//         return builder
//           .baseUrl(opaProperties.getEndpoint())
//           .build();
//     }
// }
