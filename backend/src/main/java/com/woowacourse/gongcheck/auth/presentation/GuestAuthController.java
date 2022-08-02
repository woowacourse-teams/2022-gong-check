package com.woowacourse.gongcheck.auth.presentation;

import com.woowacourse.gongcheck.auth.application.EnterCodeProvider;
import com.woowacourse.gongcheck.auth.application.GuestAuthService;
import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GuestAuthController {

    private final GuestAuthService guestAuthService;
    private final EnterCodeProvider enterCodeProvider;

    public GuestAuthController(GuestAuthService guestAuthService, EnterCodeProvider enterCodeProvider) {
        this.guestAuthService = guestAuthService;
        this.enterCodeProvider = enterCodeProvider;
    }

    @PostMapping("/hosts/{enterCode}/enter")
    public ResponseEntity<GuestTokenResponse> createGuestToken(@PathVariable final String enterCode,
                                                               @Valid @RequestBody final GuestEnterRequest request) {
        Long hostId = enterCodeProvider.parseId(enterCode);
        GuestTokenResponse response = guestAuthService.createToken(hostId, request);
        return ResponseEntity.ok(response);
    }
}
