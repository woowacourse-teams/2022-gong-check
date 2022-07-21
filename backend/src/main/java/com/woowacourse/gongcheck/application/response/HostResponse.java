package com.woowacourse.gongcheck.application.response;

import lombok.Getter;

@Getter
public class HostResponse {

    private Long id;

    private HostResponse() {
    }

    private HostResponse(final Long id) {
        this.id = id;
    }

    public static HostResponse from(final Long id) {
        return new HostResponse(id);
    }
}
