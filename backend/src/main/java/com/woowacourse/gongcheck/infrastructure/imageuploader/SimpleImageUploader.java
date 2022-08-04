package com.woowacourse.gongcheck.infrastructure.imageuploader;

import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import org.springframework.web.multipart.MultipartFile;

public class SimpleImageUploader implements ImageUploader {
    @Override
    public ImageUrlResponse upload(MultipartFile file, String directoryName) {
        return ImageUrlResponse.from("https://user-images.githubusercontent.com/48307960/178979416-449c8a6e-5c8b-4d14-91e6-c19718024206.png");
    }
}
