package com.woowacourse.gongcheck.presentation.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SpaceCreateRequest {

    private String name;
    private MultipartFile image;

    private SpaceCreateRequest() {
    }

    public SpaceCreateRequest(String name, MultipartFile image) {
        this.name = name;
        this.image = image;
    }
}
