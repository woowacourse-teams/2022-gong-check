package com.woowacourse.gongcheck.infrastructure.imageuploader;

import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import com.woowacourse.gongcheck.core.domain.image.imageFile.ImageFile;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class OwnServerImageUploader implements ImageUploader {

    private final WebClient webClient;

    public OwnServerImageUploader(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ImageUrlResponse upload(final MultipartFile image, final String directoryName) {
        ImageFile imageFile = ImageFile.from(image);
        return ImageUrlResponse.from(postToImageServer(imageFile));
    }

    private String postToImageServer(final ImageFile imageFile) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", imageFile.inputStream());

        return webClient.post()
                .uri("/api/image-upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
