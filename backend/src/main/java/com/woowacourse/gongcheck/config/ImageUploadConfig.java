package com.woowacourse.gongcheck.config;

import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.infrastructure.imageuploader.OwnServerImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ImageUploadConfig {

    private final WebClient webClient;

    public ImageUploadConfig(@Value("${file.upload-url}") final String imageServerUrl) {
        this.webClient = WebClient.create(imageServerUrl);
    }

    @Bean
    public ImageUploader imageUploader() {
        return new OwnServerImageUploader(webClient);
    }
}
