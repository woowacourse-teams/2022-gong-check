package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.presentation.request.GuestEnterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class GuestAuthDocumentation extends DocumentationTest {

    @Test
    void 게스트_토큰_생성() {
        when(guestAuthService.createToken(anyLong(), any())).thenReturn(GuestTokenResponse.from("jwt.token.here"));
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(guestEnterRequest)
                .when().post("/api/hosts/1/enter")
                .then().log().all()
                .apply(document("guests/auth"))
                .statusCode(HttpStatus.OK.value());
    }
}
