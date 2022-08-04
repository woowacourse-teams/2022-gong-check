package com.woowacourse.gongcheck.core.presentation;

import com.woowacourse.gongcheck.auth.presentation.aop.HostOnly;
import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class ImageUploadController {

    private final ImageUploader imageUploader;

    public ImageUploadController(final ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
    }

    @PostMapping(value = "/imageUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @HostOnly
    public ResponseEntity<ImageUrlResponse> uploadImage(@RequestPart final MultipartFile image) {
        return ResponseEntity.ok(imageUploader.upload(image, ""));
    }
}
