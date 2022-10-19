package com.woowacourse.gongcheck.core.application.response;

import com.woowacourse.gongcheck.core.domain.host.Host;
import lombok.Getter;

@Getter
public class HostProfileResponse {

    private String imageUrl;
    private String nickname;

    private HostProfileResponse() {
    }

    private HostProfileResponse(final String imageUrl, final String nickname) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
    }

    public static HostProfileResponse from(final Host host) {
        return new HostProfileResponse(host.getImageUrl(), host.getNickname().getValue());
    }
}
