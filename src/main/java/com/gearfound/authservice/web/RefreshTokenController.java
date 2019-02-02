package com.gearfound.authservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
    public Mono<Void> revokeToken(@PathVariable("token") String refreshToken) {
        return Mono.fromCallable(() -> {
            tokenStore.removeRefreshToken(new DefaultOAuth2RefreshToken(refreshToken));
            return null;
        });
    }
}
