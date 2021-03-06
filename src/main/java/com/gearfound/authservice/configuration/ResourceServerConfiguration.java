package com.gearfound.authservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //-- define URL patterns to enable OAuth2 security
        http.
            anonymous().disable()
            .requestMatchers()
                .antMatchers("/user")
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user").access("hasRole('ADMIN') or hasRole('USER')")
                .and()
            .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
