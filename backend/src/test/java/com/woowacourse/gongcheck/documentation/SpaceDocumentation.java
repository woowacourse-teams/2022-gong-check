package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_아이디_지정_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.core.application.response.SpaceResponse;
import com.woowacourse.gongcheck.core.application.response.SpacesResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.presentation.request.SpaceChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpaceCreateRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SpaceDocumentation extends DocumentationTest {

    @Nested
    class Space를_조회한다 {

        @Test
        void Space_조회에_성공한다() {
            Host host = Host_생성("1234", 1234L);
            when(spaceService.findSpaces(anyLong())).thenReturn(
                    SpacesResponse.from(List.of(
                            Space_아이디_지정_생성(1L, host, "잠실"),
                            Space_아이디_지정_생성(2L, host, "선릉"))
                    )
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .when().get("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/list"))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class Space를_생성한다 {

        @Test
        void Space_생성에_성공한다() {
            SpaceCreateRequest request = new SpaceCreateRequest("잠실 캠퍼스", "https://image.gongcheck.shop/123sdf5");
            when(spaceService.createSpace(anyLong(), any())).thenReturn(1L);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/create/success"))
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void Space_이름이_null_인_경우_생성에_실패한다() {
            SpaceCreateRequest request = new SpaceCreateRequest(null, "https://image.gongcheck.shop/123sdf5");
            when(spaceService.createSpace(anyLong(), any())).thenReturn(1L);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/create/fail/name_null"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void Space_이름이_빈_값_인_경우_생성에_실패한다() {
            SpaceCreateRequest request = new SpaceCreateRequest("", "https://image.gongcheck.shop/123sdf5");
            when(spaceService.createSpace(anyLong(), any())).thenReturn(1L);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/create/fail/name_blank"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class 단일_Space를_조회한다 {

        @Test
        void 조회에_성공한다() {
            Host host = Host_생성("1234", 1234L);
            when(spaceService.findSpace(anyLong(), anyLong()))
                    .thenReturn(SpaceResponse.from(Space_아이디_지정_생성(1L, host, "잠실 캠퍼스")));
            when(authenticationContext.getPrincipal())
                    .thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .when().get("/api/spaces/1")
                    .then().log().all()
                    .apply(document("spaces/find"))
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class Space를_수정한다 {

        @Test
        void Space_수정에_성공한다() {
            SpaceChangeRequest request = new SpaceChangeRequest("잠실 캠퍼스", "https://image.gongcheck.shop/123sdf5");
            doNothing().when(spaceService).changeSpace(anyLong(), anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/api/spaces/1")
                    .then().log().all()
                    .apply(document("spaces/change/success"))
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void Space_이름이_null_인_경우_수정에_실패한다() {
            SpaceChangeRequest request = new SpaceChangeRequest(null, "https://image.gongcheck.shop/123sdf5");
            doNothing().when(spaceService).changeSpace(anyLong(), anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header(AUTHORIZATION, "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/api/spaces/1")
                    .then().log().all()
                    .apply(document("spaces/change/fail/name_null"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void Space를_삭제한다() {
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
