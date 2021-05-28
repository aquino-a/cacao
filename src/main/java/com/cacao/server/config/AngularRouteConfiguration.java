package com.cacao.server.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;


/**
 * Configures server to serve static angular app
 */
@Configuration
public class AngularRouteConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

      registry.addResourceHandler("/**/*")
        .addResourceLocations("classpath:/static/")
        .resourceChain(true)
        .addResolver(new PathResourceResolver() {
            @Override
            protected Resource getResource(String resourcePath,
                                           Resource location) throws IOException {
                Resource requestedResource = location.createRelative(resourcePath);
                return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                : new ClassPathResource("/static/index.html");
            }
        });
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        var tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");

                var collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        return tomcat;
    }
}