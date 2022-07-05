package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.SpaceService;
import com.woowacourse.gongcheck.application.response.SpacesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpaceController {

    private final SpaceService spaceService;

    public SpaceController(final SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping("/spaces")
    public ResponseEntity<SpacesResponse> showSpaces(@AuthenticationPrincipal final Long hostId,
                                                     final Pageable pageable) {
        SpacesResponse response = spaceService.findPage(hostId, pageable);
        return ResponseEntity.ok(response);
    }
}
