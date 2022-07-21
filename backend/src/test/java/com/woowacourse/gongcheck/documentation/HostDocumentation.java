package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.presentation.request.SpacePasswordChangeRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class HostDocumentation extends DocumentationTest {

    @Nested
    class 비밀번호_변경 {

        @Test
        void 비밀번호_변경에_성공한다() {
            doNothing().when(hostService).changeSpacePassword(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new SpacePasswordChangeRequest("1234"))
                    .when().patch("/api/spacePassword")
                    .then().log().all()
                    .apply(document("host/spacePassword_update/success"))
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void 비밀번호_길이가_맞지_않는_경우_변경에_실패한다() {
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다."))
                    .when(hostService)
                    .changeSpacePassword(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new SpacePasswordChangeRequest("12345"))
                    .when().patch("/api/spacePassword")
                    .then().log().all()
                    .apply(document("host/spacePassword_update/fail/password_length"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 비밀번호_형식이_맞지_않는_경우_변경에_실패한다() {
            doThrow(new BusinessException("비밀번호는 네 자리 숫자로 이루어져야 합니다."))
                    .when(hostService)
                    .changeSpacePassword(anyLong(), any());
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new SpacePasswordChangeRequest("가나다라"))
                    .when().patch("/api/spacePassword")
                    .then().log().all()
                    .apply(document("host/spacePassword_update/fail/password_pattern"))
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
