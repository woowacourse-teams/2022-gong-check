package com.woowacourse.gongcheck.application;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SimpleImageUploader implements ImageUploader {
    @Override
    public String upload(MultipartFile file, String directoryName) {
        return "https://user-images.githubusercontent.com/48307960/178979416-449c8a6e-5c8b-4d14-91e6-c19718024206.png";
    }
}
