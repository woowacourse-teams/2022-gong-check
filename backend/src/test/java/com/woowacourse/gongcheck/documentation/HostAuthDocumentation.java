package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.woowacourse.gongcheck.auth.application.response.TokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.TokenRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class HostAuthDocumentation extends DocumentationTest {

    @Nested
    class 호스트_토큰을_요청한다 {

        @Test
        void 로그인에_성공하면_호스트_토큰을_반환한다() {
            TokenRequest tokenRequest = new TokenRequest("authorization-code");
            TokenResponse tokenResponse = TokenResponse.of("jwt.token.here", true);
            when(hostAuthService.createToken(any())).thenReturn(tokenResponse);

            docsGiven
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(tokenRequest)
                    .when().post("api/login")
                    .then().log().all()
                    .apply(document("hosts/auth/success",
                            requestFields(
                                    fieldWithPath("code").type(JsonFieldType.STRING).description("Authorization Code")
                                            .attributes(key("nullable").value(true))),
                            responseFields(
                                    fieldWithPath("token").type(JsonFieldType.STRING).description("Access Token"),
                                    fieldWithPath("alreadyJoin").type(JsonFieldType.BOOLEAN).description("가입 여부")
                            )
                    ))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
