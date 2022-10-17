package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_아이디_지정_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.woowacourse.gongcheck.auth.domain.Authority;
import com.woowacourse.gongcheck.core.application.response.HostProfileResponse;
import com.woowacourse.gongcheck.core.presentation.request.HostProfileChangeRequest;
import com.woowacourse.gongcheck.core.presentation.request.SpacePasswordChangeRequest;
import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class HostDocumentation extends DocumentationTest {

    @Nested
    class 비밀번호_변경 {

        @Test
        void 비밀번호_변경에_성공한다() {
            doNothing().when(hostService).changeSpacePassword(anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new SpacePasswordChangeRequest("1234"))
                    .when().patch("/api/spacePassword")
                    .then().log().all()
                    .apply(document("hosts/spacePassword_update/success"))
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void 비밀번호_길이가_맞지_않는_경우_변경에_실패한다() {
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다.", ErrorCode.SP03))
                    .when(hostService)
                    .changeSpacePassword(anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new SpacePasswordChangeRequest("12345"))
                    .when().patch("/api/spacePassword")
                    .then().log().all()
                    .apply(document("hosts/spacePassword_update/fail/password_length"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 비밀번호_형식이_맞지_않는_경우_변경에_실패한다() {
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다.", ErrorCode.SP03))
                    .when(hostService)
                    .changeSpacePassword(anyLong(), any());
            when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new SpacePasswordChangeRequest("가나다라"))
                    .when().patch("/api/spacePassword")
                    .then().log().all()
                    .apply(document("hosts/spacePassword_update/fail/password_pattern"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    void 호스트_입장코드를_조회한다() {
        when(hostService.createEntranceCode(anyLong())).thenReturn("random_entrance_code");
        when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .when().get("/api/hosts/entranceCode")
                .then().log().all()
                .apply(document("hosts/entranceCode",
                        responseFields(
                                fieldWithPath("entranceCode").type(JsonFieldType.STRING).description("입장코드")
                        )
                ))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 호스트_프로필을_조회한다() {
        when(hostService.findProfile(anyLong())).thenReturn(HostProfileResponse.from(
                Host_아이디_지정_생성(1L, "0000", 1L)));

        docsGiven
                .when().get("/api/hosts/1/profile")
                .then().log().all()
                .apply(document("hosts/profile",
                        responseFields(
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("Github Image Url"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("Github Login Name")
                        )
                ))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 호스트_프로필을_변경한다() {
        when(jwtTokenProvider.extractAuthority(anyString())).thenReturn(Authority.HOST);
        doNothing().when(hostService).changeProfile(anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new HostProfileChangeRequest("changedName"))
                .when().put("/api/hosts")
                .then().log().all()
                .apply(document("hosts/profile/change",
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 nickname")
                        )))
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
