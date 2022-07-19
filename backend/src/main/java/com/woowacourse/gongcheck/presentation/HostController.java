package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.HostService;
import com.woowacourse.gongcheck.presentation.request.SpacePasswordChangeRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> changeSpacePassword(@AuthenticationPrincipal final Long hostId,
                                                    @Valid @RequestBody final SpacePasswordChangeRequest request) {
        hostService.changeSpacePassword(hostId, request);
        return ResponseEntity.noContent().build();
    }
}
