package com.narendra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class DisputeConfiguration {

	@Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
	
	@Bean
    WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		
		http.authorizeHttpRequests(authRequests -> authRequests.requestMatchers(HttpMethod.GET, "/api/v1/users")
				// .hasAuthority("SCOPE_profile")
				.hasRole("developer").anyRequest().authenticated())
				.oauth2ResourceServer(oauth2Customizer -> oauth2Customizer
						.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
		return http.build();
	}
}
