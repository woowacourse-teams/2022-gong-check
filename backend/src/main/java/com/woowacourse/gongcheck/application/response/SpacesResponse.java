package com.woowacourse.gongcheck.application.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.domain.space.Space;
import java.util.List;
import lombok.Getter;

@Getter
public class SpacesResponse {

    private List<SpaceResponse> spaces;

    private SpacesResponse() {
    }

    private SpacesResponse(final List<SpaceResponse> spaces) {
        this.spaces = spaces;
    }

    public static SpacesResponse from(final List<Space> spaces) {
        return new SpacesResponse(
                spaces.stream()
                        .map(SpaceResponse::from)
                        .collect(toList())
        );
    }
}
