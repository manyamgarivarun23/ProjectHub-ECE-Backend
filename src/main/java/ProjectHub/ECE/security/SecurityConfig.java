package ProjectHub.ECE.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(cors -> cors.configurationSource(
                    corsConfigurationSource()
            ))

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/",
                    "/login",
                    "/register",
                    "/api/test",
                    "/css/**",
                    "/js/**"
                ).permitAll()

                .requestMatchers(
                    "/feedback",
                    "/feedback/status",
                    "/api/feedback",
                    "/api/feedback/**"
                ).hasRole("FACULTY")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form

                .loginPage("/login")

                .successHandler((request, response, authentication) -> {

                    response.setStatus(200);
                    response.setContentType("application/json");

                    response.getWriter().write(
                            "{\"success\":true}"
                    );
                })

                .failureHandler((request, response, exception) -> {

                    response.setStatus(401);
                    response.setContentType("application/json");

                    response.getWriter().write(
                            "{\"success\":false,\"message\":\"Invalid email or password\"}"
                    );
                })

                .permitAll()
            )

            .logout(logout -> logout

                .logoutSuccessHandler((request, response, authentication) -> {

                    response.setStatus(200);
                    response.setContentType("application/json");

                    response.getWriter().write(
                            "{\"success\":true}"
                    );
                })

                .permitAll()
            );

        return http.build();
    }
}