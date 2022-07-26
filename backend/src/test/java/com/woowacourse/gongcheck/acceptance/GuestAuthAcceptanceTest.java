package com.woowacourse.gongcheck.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.core.domain.host.Host;
import com.woowacourse.gongcheck.core.domain.host.HostRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class GuestAuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private HostRepository hostRepository;

    @Test
    void 올바른_Space_비밀번호를_입력하면_토큰을_반환한다() {
        List<Host> all = hostRepository.findAll();
        for (Host host : all) {
            System.out.println(host.getId());
        }
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(guestEnterRequest)
                .when().post("/api/hosts/1/enter")
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getObject(".", GuestTokenResponse.class)).isNotNull()
        );
    }
}
