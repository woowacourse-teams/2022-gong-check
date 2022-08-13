package com.woowacourse.imagestorage.presentation;

import com.woowacourse.imagestorage.application.ImageService;
import com.woowacourse.imagestorage.application.response.ImageResponse;
import java.util.concurrent.TimeUnit;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private static final String DEFAULT_RESIZE_WIDTH = "500";
    private static final int CACHE_CONTROL_MAX_AGE = 30;

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/api/image-upload")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile file) {

        ImageResponse imageResponse = imageService.storeImage(file);
        return ResponseEntity.ok(imageResponse.getImagePath());
    }

    @GetMapping(value = "/api/resize/{imageUrl}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getResizeImage(@PathVariable String imageUrl,
                                                 @RequestParam(required = false, defaultValue = DEFAULT_RESIZE_WIDTH) int width) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(CACHE_CONTROL_MAX_AGE, TimeUnit.DAYS))
                .body(imageService.resizeImage(imageUrl, width));
    }
}
