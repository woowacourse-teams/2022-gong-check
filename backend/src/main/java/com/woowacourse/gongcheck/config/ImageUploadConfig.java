package com.woowacourse.gongcheck.config;

import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.infrastructure.imageuploader.OwnServerImageUploader;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageUploadConfig {

    private final Path storageLocation;
    private final String imagePathPrefix;

    public ImageUploadConfig(@Value("${file.upload-dir}") final String storageLocation,
                             @Value("${file.server-path}") final String imagePathPrefix) {
        this.storageLocation = Paths.get(storageLocation);
        this.imagePathPrefix = imagePathPrefix;
    }

    @Bean
    public ImageUploader imageUploader() {
        return new OwnServerImageUploader(storageLocation, imagePathPrefix);
    }
}
