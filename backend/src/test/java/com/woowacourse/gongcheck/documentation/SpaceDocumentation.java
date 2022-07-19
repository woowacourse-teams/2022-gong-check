package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_아이디_지정_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SpaceDocumentation extends DocumentationTest {

    @Nested
    class 공간을_조회한다 {

        @Test
        void 공간_조회에_성공한다() {
            Host host = Host_생성("1234", 1234L);
            when(spaceService.findPage(anyLong(), any())).thenReturn(
                    SpacesResponse.of(List.of(
                                    Space_아이디_지정_생성(1L, host, "잠실"),
                                    Space_아이디_지정_생성(2L, host, "선릉")),
                            true)
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .queryParam("page", 0)
                    .queryParam("size", 2)
                    .when().get("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/list"))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class 공간을_생성한다 {

        @Test
        void 공간_생성에_성공한다() throws IOException {
            File fakeImage = File.createTempFile("temp", ".jpg");
            when(spaceService.createSpace(anyLong(), any())).thenReturn(1L);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .param("name", "잠실 캠퍼스")
                    .multiPart("image", fakeImage)
                    .when().post("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/create"))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void 공간_이름이_null_인_경우_생성에_실패한다() {
            when(spaceService.createSpace(anyLong(), any())).thenReturn(1L);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .when().post("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/create"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 공간_이름이_빈_값_인_경우_생성에_실패한다() {
            when(spaceService.createSpace(anyLong(), any())).thenReturn(1L);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .param("name", "")
                    .when().post("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/create"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void 공간을_삭제한다() {
        doNothing().when(spaceService).removeSpace(anyLong(), anyLong());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header(AUTHORIZATION, "Bearer jwt.token.here")
                .when().delete("/api/spaces/1")
                .then().log().all()
                .apply(document("spaces/delete"))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
