package com.woowacourse.gongcheck.infrastructure.imageuploader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class OwnServerImageUploaderTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;

    @InjectMocks
    private OwnServerImageUploader imageUploader;

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
                when(webClient.post()
                        .uri("/api/image-upload")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .body(any())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block())
                        .thenReturn("https://localhost:8080/images/ajsicjnasdioc.jpg");
            }

            @Test
            void 이미지를_저장하고_이미지_경로를_반환한다() {
                ImageUrlResponse actual = imageUploader.upload(image, "");

                assertThat(actual.getImageUrl()).isNotNull();
            }
        }
    }
}
