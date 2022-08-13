package com.woowacourse.imagestorage.presentation;

import com.woowacourse.imagestorage.application.ImageService;
import com.woowacourse.imagestorage.application.response.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/api/image-upload")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile file) {

        ImageResponse imageResponse = imageService.storeImage(file);
        return ResponseEntity.ok(imageResponse.getImagePath());
    }
}
