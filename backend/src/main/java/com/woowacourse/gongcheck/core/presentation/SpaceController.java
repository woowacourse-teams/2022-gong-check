package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.auth.presentation.AuthenticationPrincipal;
import com.woowacourse.gongcheck.auth.presentation.HostOnly;
import com.woowacourse.gongcheck.core.application.SpaceService;
import com.woowacourse.gongcheck.core.application.response.SpaceResponse;
import com.woowacourse.gongcheck.core.application.response.SpacesResponse;
import com.woowacourse.gongcheck.core.presentation.request.SpaceChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpaceCreateRequest;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<SpacesResponse> showSpaces(@AuthenticationPrincipal final Long hostId) {
        SpacesResponse response = spaceService.findSpaces(hostId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/spaces")
    @HostOnly
    public ResponseEntity<Void> createSpace(@AuthenticationPrincipal final Long hostId,
                                            @Valid @RequestBody final SpaceCreateRequest request) {
        Long spaceId = spaceService.createSpace(hostId, request);
        return ResponseEntity.created(URI.create("/api/spaces/" + spaceId)).build();
    }

    @GetMapping("/spaces/{spaceId}")
    public ResponseEntity<SpaceResponse> showSpace(@AuthenticationPrincipal final Long hostId,
                                                   @PathVariable final Long spaceId) {
        SpaceResponse response = spaceService.findSpace(hostId, spaceId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/spaces/{spaceId}")
    public ResponseEntity<Void> changeSpace(@AuthenticationPrincipal final Long hostId,
                                            @PathVariable final Long spaceId,
                                            @Valid @RequestBody final SpaceChangeRequest request) {
        spaceService.changeSpace(hostId, spaceId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/spaces/{spaceId}")
    @HostOnly
    public ResponseEntity<Void> removeSpace(@AuthenticationPrincipal final Long hostId,
                                            @PathVariable final Long spaceId) {
        spaceService.removeSpace(hostId, spaceId);
        return ResponseEntity.noContent().build();
    }
}
