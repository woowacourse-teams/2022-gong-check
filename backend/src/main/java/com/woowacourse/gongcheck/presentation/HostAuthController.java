package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.HostAuthService;
import com.woowacourse.gongcheck.application.response.TokenResponse;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HostAuthController {

    private final HostAuthService hostAuthService;

    public HostAuthController(final HostAuthService hostAuthService) {
        this.hostAuthService = hostAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody final TokenRequest tokenRequest) {
        return ResponseEntity.ok(hostAuthService.createToken(tokenRequest));
    }
}
