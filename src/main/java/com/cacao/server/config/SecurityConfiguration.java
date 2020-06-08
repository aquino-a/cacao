package com.cacao.server.config;

import com.cacao.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    BearerTokenResolver bearerTokenResolver;

    @Bean
    BearerTokenResolver bearerTokenResolver(){
      return request -> request.getParameter("token");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth ->
                auth.antMatchers("/api/**")
                        .authenticated())
                .oauth2ResourceServer(oauth ->
                    oauth.bearerTokenResolver(bearerTokenResolver)
                        .jwt())
                .addFilterAfter(
                        (request, response, filterChain) -> {
                            var jwtAuth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                            if(jwtAuth != null){
                                var user = User.fromJwtAuthenticationToken(jwtAuth);
                                SecurityContextHolder.getContext().setAuthentication(user);
                            }
                            filterChain.doFilter(request, response);
                        }
                        , BearerTokenAuthenticationFilter.class);
    }
}
