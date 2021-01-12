package com.cacao.server.config;

import com.cacao.server.model.User;
import com.cacao.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${cors.allowedOrigins:http://localhost:4200,http://localhost:8080}")
    private String[] allowedOrigins;

    @Autowired
    BearerTokenResolver bearerTokenResolver;

    @Autowired
    UserService userService;

    @Bean
    BearerTokenResolver bearerTokenResolver(){
      return request -> request.getParameter("token");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth ->
                auth.antMatchers("/api/**")
                        .authenticated())
                .cors(config -> {
                    config.configurationSource(corsConfigurationSource());
                })
                .oauth2ResourceServer(oauth ->
                    oauth.bearerTokenResolver(bearerTokenResolver)
                        .jwt())
                .addFilterAfter(
                        (request, response, filterChain) -> {
                            var auth = SecurityContextHolder.getContext().getAuthentication();
                            if(auth != null && auth instanceof JwtAuthenticationToken){
                                var jwtAuth = (JwtAuthenticationToken) auth;
                                var user = User.fromJwtAuthenticationToken(jwtAuth);
                                SecurityContextHolder.getContext().setAuthentication(user);
                                CompletableFuture.runAsync(() -> userService.AddUser(user));
                            }
                            filterChain.doFilter(request, response);
                        }
                        , BearerTokenAuthenticationFilter.class);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        //TODO wire in origin property
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        var allAllowedConfiguration = new CorsConfiguration();
        allAllowedConfiguration.setAllowedOrigins(Arrays.asList("*"));
        allAllowedConfiguration.setAllowedMethods(Arrays.asList("*"));
        allAllowedConfiguration.setAllowedHeaders(Arrays.asList("*"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/**", allAllowedConfiguration);

        return source;
    }


}
