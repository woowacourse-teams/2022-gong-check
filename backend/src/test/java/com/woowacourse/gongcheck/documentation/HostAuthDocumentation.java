package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.TokenResponse;
import com.woowacourse.gongcheck.presentation.request.TokenRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
                    .apply(document("hosts/auth/success"))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
