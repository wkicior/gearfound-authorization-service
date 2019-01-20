package com.gearfound.gearfoundauthorizationservice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class RefreshTokenControllerEndpointTest {

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    TokenStore tokenStore;

    @Test
    void revokeToken() throws Exception {
        mockMvc.perform(delete("/oauth/refresh-token/1234")).andExpect(status().isNoContent());
        Mockito.verify(tokenStore).removeRefreshToken(new DefaultOAuth2RefreshToken("1234"));
    }
}