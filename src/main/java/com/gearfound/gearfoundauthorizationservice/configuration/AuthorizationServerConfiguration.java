package com.gearfound.gearfoundauthorizationservice.configuration;

import com.gearfound.gearfoundauthorizationservice.users.UserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.validation.constraints.NotNull;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @NotNull
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @NotNull
    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    private static String REALM = "GEARFOUND_REALM";

    private final TokenStore tokenStore;

    private final UserApprovalHandler userApprovalHandler;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;


    public AuthorizationServerConfiguration(TokenStore tokenStore,
                                            UserApprovalHandler userApprovalHandler,
                                            @Qualifier("authenticationManagerBean") AuthenticationManager authenticationManager,
                                            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenStore = tokenStore;
        this.userApprovalHandler = userApprovalHandler;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(secret))
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("openid", "read", "write", "trust")
                .accessTokenValiditySeconds(300)
                .refreshTokenValiditySeconds(30 * 60);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(REALM).passwordEncoder(passwordEncoder);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        CorsConfigurationSource source = corsConfigurationSource();
//        http.addFilterBefore(new CorsFilter(source), ChannelProcessingFilter.class);
//    }

    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        //more config
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
