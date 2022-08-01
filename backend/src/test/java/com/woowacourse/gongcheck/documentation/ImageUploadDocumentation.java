package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ImageUploadDocumentation extends DocumentationTest {

    @Test
    void 이미지를_업로드한다() throws IOException {
        File fakeImage = File.createTempFile("temp", ".jpg");
        when(imageUploader.upload(any(), anyString())).thenReturn("https://image.gongcheck.com/12sdf124sx");
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header(AUTHORIZATION, "Bearer jwt.token.here")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", fakeImage)
                .when().post("/api/image-upload")
                .then().log().all()
                .apply(document("image-upload"))
                .statusCode(HttpStatus.OK.value());
    }
}
