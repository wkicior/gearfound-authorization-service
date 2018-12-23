package com.gearfound.gearfoundauthorizationservice.configuration;

import com.gearfound.gearfoundauthorizationservice.users.UserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private static String REALM="CRM_REALM";
    private static final int ONE_DAY = 60 * 60 * 24;
    private static final int THIRTY_DAYS = 60 * 60 * 24 * 30;

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
                .withClient("crmClient1")
                .secret(passwordEncoder.encode("crmSuperSecret"))
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("openid", "read", "write", "trust")
                //.accessTokenValiditySeconds(ONE_DAY)
                .accessTokenValiditySeconds(300)
                .refreshTokenValiditySeconds(THIRTY_DAYS);
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

}
