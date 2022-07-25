package com.woowacourse.gongcheck.core.presentation.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SpaceCreateRequest {

    @NotNull
    @Size(min = 1, max = 20, message = "공간의 이름은 한글자 이상 20자 이하여야 합니다.")
    private String name;
    private MultipartFile image;

    private SpaceCreateRequest() {
    }

    public SpaceCreateRequest(final String name, final MultipartFile image) {
        this.name = name;
        this.image = image;
    }
}
