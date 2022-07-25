package com.woowacourse.gongcheck.auth.presentation;

import com.woowacourse.gongcheck.auth.application.HostAuthService;
import com.woowacourse.gongcheck.auth.application.response.TokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.TokenRequest;
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
    public ResponseEntity<TokenResponse> login(@RequestBody final TokenRequest request) {
        return ResponseEntity.ok(hostAuthService.createToken(request));
    }
}
