package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.FakeImageFactory.createFakeImage;
import static com.woowacourse.gongcheck.acceptance.AuthSupport.Host_토큰을_요청한다;
import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;

import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import io.restassured.RestAssured;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class ImageUploadAcceptanceTest extends AcceptanceTest {

    @Test
    void Host_토큰으로_이미지를_업로드한다() throws IOException {
        File fakeImage = createFakeImage();
        String token = Host_토큰을_요청한다().getToken();

        RestAssured
                .given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", fakeImage, "image/jpg")
                .auth().oauth2(token)
                .when().post("/api/imageUpload")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Guest_토큰으로_이미지_업로드_시_예외가_발생한다() throws IOException {
        File fakeImage = createFakeImage();
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", fakeImage)
                .auth().oauth2(token)
                .when().post("/api/imageUpload")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
