package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.GuestAuthService;
import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
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

    public GuestAuthController(final GuestAuthService guestAuthService) {
        this.guestAuthService = guestAuthService;
    }

    @PostMapping("/hosts/{hostId}/enter")
    public ResponseEntity<GuestTokenResponse> createGuestToken(@PathVariable final long hostId,
                                                               @Valid @RequestBody final GuestEnterRequest request) {
        GuestTokenResponse response = guestAuthService.createToken(hostId, request);
        return ResponseEntity.ok(response);
    }
}
