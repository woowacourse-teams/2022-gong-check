package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.auth.presentation.AuthenticationPrincipal;
import com.woowacourse.gongcheck.auth.presentation.aop.HostOnly;
import com.woowacourse.gongcheck.core.application.HostService;
import com.woowacourse.gongcheck.core.application.response.HostResponse;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HostController {

    private final HostService hostService;

    public HostController(final HostService hostService) {
        this.hostService = hostService;
    }

    @PatchMapping("/spacePassword")
    @HostOnly
    public ResponseEntity<Void> changeSpacePassword(@AuthenticationPrincipal final Long hostId,
                                                    @Valid @RequestBody final SpacePasswordChangeRequest request) {
        hostService.changeSpacePassword(hostId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hosts/me")
    @HostOnly
    public ResponseEntity<HostResponse> showHost(@AuthenticationPrincipal final Long hostId) {
        return ResponseEntity.ok(HostResponse.from(hostService.getHostId(hostId)));
    }
}
