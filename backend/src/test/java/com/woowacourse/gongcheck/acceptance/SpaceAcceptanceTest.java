package com.woowacourse.gongcheck.acceptance;

import static com.woowacourse.gongcheck.acceptance.AuthSupport.Host_토큰을_요청한다;
import static com.woowacourse.gongcheck.acceptance.AuthSupport.토큰을_요청한다;

import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import io.restassured.RestAssured;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SpaceAcceptanceTest extends AcceptanceTest {

    @Test
    void Host_토큰으로_Space를_조회한다() {
        String token = Host_토큰을_요청한다(new TokenRequest("code")).getToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces?page=0&size=5")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Host_토큰으로_Space를_생성한다() throws IOException {
        File fakeImage = File.createTempFile("temp", ".jpg");

        String token = Host_토큰을_요청한다(new TokenRequest("code")).getToken();

        RestAssured
                .given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("name", "잠실 캠퍼스")
                .multiPart("image", fakeImage)
                .auth().oauth2(token)
                .when().post("/api/spaces")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void Host_토큰으로_한_Host가_이미_존재하는_이름의_Space를_생성하면_에러_응답을_반환한다() throws IOException {
        File fakeImage = File.createTempFile("temp", ".jpg");

        String token = Host_토큰을_요청한다(new TokenRequest("code")).getToken();

        RestAssured
                .given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("name", "잠실 캠퍼스")
                .multiPart("image", fakeImage)
                .auth().oauth2(token)
                .when().post("/api/spaces")
                .then().log().all();

        RestAssured
                .given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("name", "잠실 캠퍼스")
                .multiPart("image", fakeImage)
                .auth().oauth2(token)
                .when().post("/api/spaces")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void Host_토큰으로_단일_Space를_조회한다() {
        String token = Host_토큰을_요청한다(new TokenRequest("code")).getToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Host_토큰으로_Space를_삭제한다() {
        String token = Host_토큰을_요청한다(new TokenRequest("code")).getToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/api/spaces/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void Guest_토큰으로_Space를_조회한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces?page=0&size=5")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Guest_토큰으로_Space를_생성한다() throws IOException {
        File fakeImage = File.createTempFile("temp", ".jpg");

        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("name", "잠실 캠퍼스")
                .multiPart("image", fakeImage)
                .auth().oauth2(token)
                .when().post("/api/spaces")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Guest_토큰으로_단일_Space를_조회한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().get("/api/spaces/1")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void Guest_토큰으로_Space를_삭제한다() {
        String token = 토큰을_요청한다(new GuestEnterRequest("1234"));

        RestAssured
                .given().log().all()
                .auth().oauth2(token)
                .when().delete("/api/spaces/1")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
