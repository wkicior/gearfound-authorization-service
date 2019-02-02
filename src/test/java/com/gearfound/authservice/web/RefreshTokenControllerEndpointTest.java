package com.gearfound.authservice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


@WebFluxTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestSecurityConfiguration.class, RefreshTokenController.class, ExceptionHandler.class})
class RefreshTokenControllerEndpointTest {

    @Autowired
    protected WebTestClient webClient;

    @MockBean
    TokenStore tokenStore;

    @Test
    void revokeToken() throws Exception {
        webClient.delete().uri("/oauth/refresh-token/1234")
                .exchange()
                .expectStatus().isNoContent();
        Mockito.verify(tokenStore).removeRefreshToken(new DefaultOAuth2RefreshToken("1234"));
    }
}