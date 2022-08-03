package com.woowacourse.gongcheck.infrastructure.imageuploader;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class OwnServerImageUploaderTest {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.server-path}")
    private String serverPath;

    @Autowired
    private ResourceLoader resourceLoader;

    private OwnServerImageUploader ownServerImageUploader;

    @BeforeEach
    void setUp() throws IOException {
        String storageLocation = resourceLoader.getResource(uploadDir).getURI().getPath();
        String imagePathPrefix = resourceLoader.getResource(serverPath).getURI().getPath();
        ownServerImageUploader = new OwnServerImageUploader(Paths.get(storageLocation), imagePathPrefix);
    }

    private void removeFile(final String fileUrl) {
        File file = new File(fileUrl);
        file.deleteOnExit();
    }

    @Nested
    class storeImage_메소드는 {

        @Nested
        class 저장하고자하는_이미지_파일이_입력된_경우 {

            private MultipartFile image;

            @BeforeEach
            void setUp() {
                image = new MockMultipartFile("images",
                        "jamsil.jpg",
                        "images/jpg",
                        "123".getBytes(StandardCharsets.UTF_8));
            }

            @Test
            void 이미지를_저장하고_이미지_경로를_반환한다() {
                ImageUrlResponse actual = ownServerImageUploader.upload(image, "");

                assertThat(actual.getImageUrl()).isNotNull();
                removeFile(actual.getImageUrl());
            }
        }
    }
}
