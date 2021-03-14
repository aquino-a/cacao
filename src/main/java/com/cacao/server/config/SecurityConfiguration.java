package com.cacao.server.config;

import com.cacao.server.model.User;
import com.cacao.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
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
        var btr = new DefaultBearerTokenResolver();
        btr.setAllowFormEncodedBodyParameter(true);
        btr.setAllowUriQueryParameter(true);
        return btr;
    }

    Converter<Jwt,? extends AbstractAuthenticationToken> jwtAuthenticationConverter = new Converter<Jwt, AbstractAuthenticationToken>() {

        private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        @Override
        public AbstractAuthenticationToken convert(Jwt jwt) {
            var user = User.fromJwt(jwt, jwtGrantedAuthoritiesConverter.convert(jwt));
            return user;
        }
    };

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
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                ;

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

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
