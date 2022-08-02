package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_아이디_지정_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.woowacourse.gongcheck.core.application.response.SpaceResponse;
import com.woowacourse.gongcheck.core.application.response.SpacesResponse;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.presentation.request.SpaceChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpaceCreateRequest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

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
                    .apply(document("spaces/list",
                            responseFields(
                                    fieldWithPath("spaces.[].id").type(JsonFieldType.NUMBER)
                                            .description("Space Id"),
                                    fieldWithPath("spaces.[].name").type(JsonFieldType.STRING)
                                            .description("Space 이름"),
                                    fieldWithPath("spaces.[].imageUrl").type(JsonFieldType.STRING)
                                            .description("Space Image Url")
                            )))
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
                    .apply(document("spaces/create/success",
                            requestFields(
                                    fieldWithPath("name").type(JsonFieldType.STRING)
                                            .description("Space 이름"),
                                    fieldWithPath("imageUrl").type(JsonFieldType.STRING)
                                            .description("Space Image Url")
                            )
                    ))
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
                    .when().get("/api/spaces/{spaceId}", 1)
                    .then().log().all()
                    .apply(document("spaces/find",
                            pathParameters(
                                    parameterWithName("spaceId").description("조회할 Space Id")),
                            responseFields(
                                    fieldWithPath("id").type(JsonFieldType.NUMBER)
                                            .description("Space Id"),
                                    fieldWithPath("name").type(JsonFieldType.STRING)
                                            .description("Space 이름"),
                                    fieldWithPath("imageUrl").type(JsonFieldType.STRING)
                                            .description("Space Image Url")
                            )
                    ))
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
                    .when().put("/api/spaces/{spaceId}", 1)
                    .then().log().all()
                    .apply(document("spaces/change/success",
                            pathParameters(
                                    parameterWithName("spaceId").description("수정할 Space Id")),
                            requestFields(
                                    fieldWithPath("name").type(JsonFieldType.STRING)
                                            .description("Space 이름"),
                                    fieldWithPath("imageUrl").type(JsonFieldType.STRING)
                                            .description("Space Image Url")
                            )
                    ))
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
                .when().delete("/api/spaces/{spaceId}", 1)
                .then().log().all()
                .apply(document("spaces/delete",
                        pathParameters(
                                parameterWithName("spaceId").description("삭제할 Space Id"))
                ))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
