package com.woowacourse.gongcheck.core.application;


import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    ImageUrlResponse upload(MultipartFile file, String directoryName);
}
