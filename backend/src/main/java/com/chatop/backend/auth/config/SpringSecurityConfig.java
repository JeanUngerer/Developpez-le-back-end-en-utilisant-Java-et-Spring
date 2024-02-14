package com.chatop.backend.auth.config;



import com.chatop.backend.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@Getter
@Setter
public class SpringSecurityConfig {

    private static List<String> clients = Arrays.asList("github");

    private final PasswordEncoder encoder;



    private final UserService myUserDetailsService;

    public SpringSecurityConfig(PasswordEncoder encoder, RsaKeyProperties rsaKeys, UserService myUserDetailsService) {
        this.encoder = encoder;
        this.myUserDetailsService = myUserDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login**",  "/authi").permitAll()
                        .anyRequest().permitAll()
                        )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .userDetailsService(myUserDetailsService)
                .httpBasic(withDefaults())
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.addExposedHeader("Token");
        configuration.addAllowedMethod(HttpMethod.PUT);

        configuration.setAllowCredentials(true);

        configuration.setAllowedOrigins( Collections.singletonList( "http://localhost:4200" ) );
        configuration.addAllowedOrigin ( "http://localhost:8080" );

        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}
