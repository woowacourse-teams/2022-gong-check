package com.woowacourse.gongcheck.core.application.response;

import lombok.Getter;

@Getter
public class EntranceCodeResponse {

    private String entranceCode;

    private EntranceCodeResponse() {
    }

    private EntranceCodeResponse(String entranceCode) {
        this.entranceCode = entranceCode;
    }

    public static EntranceCodeResponse from(final String entranceCode) {
        return new EntranceCodeResponse(entranceCode);
    }
}
