package com.woowacourse.gongcheck.core.presentation.request;

import lombok.Getter;

@Getter
public class AllCheckRequest {

    private Long sectionId;

    private AllCheckRequest() {
    }

    public AllCheckRequest(Long sectionId) {
        this.sectionId = sectionId;
    }
}
