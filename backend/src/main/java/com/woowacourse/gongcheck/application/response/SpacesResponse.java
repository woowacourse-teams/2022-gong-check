package com.woowacourse.gongcheck.application.response;

import static java.util.stream.Collectors.toList;

import com.woowacourse.gongcheck.domain.space.Space;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class SpacesResponse {

    private List<SpaceResponse> spaces;
    private boolean hasNext;

    private SpacesResponse() {
    }

    private SpacesResponse(final List<SpaceResponse> spaces, final boolean hasNext) {
        this.spaces = spaces;
        this.hasNext = hasNext;
    }

    public static SpacesResponse from(final Slice<Space> spaces) {
        return new SpacesResponse(
                spaces.getContent()
                        .stream()
                        .map(SpaceResponse::from)
                        .collect(toList()),
                spaces.hasNext()
        );
    }

    public static SpacesResponse of(final List<Space> spaces, final boolean hasNext) {
        return new SpacesResponse(
                spaces.stream()
                        .map(SpaceResponse::from)
                        .collect(toList()),
                hasNext
        );
    }
}
