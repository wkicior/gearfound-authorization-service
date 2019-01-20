package com.gearfound.gearfoundauthorizationservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth/refresh-token")
public class RefreshTokenController {
    private final TokenStore tokenStore;

    public RefreshTokenController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{token}")
    public void revokeToken(@PathVariable("token") String refreshToken) {
        tokenStore.removeRefreshToken(new DefaultOAuth2RefreshToken(refreshToken));
    }
}
