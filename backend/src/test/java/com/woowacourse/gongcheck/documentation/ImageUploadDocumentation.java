package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class ImageUploadDocumentation extends DocumentationTest {

    @Test
    void 이미지를_업로드한다() throws IOException {
        File fakeImage = File.createTempFile("temp", ".jpg");
        when(imageUploader.upload(any(), anyString()))
                .thenReturn(ImageUrlResponse.from("https://image.gongcheck.com/12sdf124sx"));
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header(AUTHORIZATION, "Bearer jwt.token.here")
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("images", fakeImage)
                .when().post("/api/imageUpload")
                .then().log().all()
                .apply(document("image-upload",
                        requestParts(partWithName("images")
                                .description("The version of the image")),
                        responseFields(
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("저장된 Image Url")
                        )
                ))
                .statusCode(HttpStatus.OK.value());
    }
}
