package com.woowacourse.gongcheck.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongcheck.auth.application.response.GuestTokenResponse;
import com.woowacourse.gongcheck.auth.presentation.request.GuestEnterRequest;
import com.woowacourse.gongcheck.cucumber.AcceptanceSteps;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

public class GuestAuthStepDefinitions extends AcceptanceSteps {

    @When("Space의 패스워드를 입력하면")
    public void Space의_패스워드를_입력하면() {
        GuestEnterRequest guestEnterRequest = new GuestEnterRequest("1234");
        String entranceCode = entranceCodeProvider.createEntranceCode(1L);

        context.invokeHttpPost("/api/hosts/" + entranceCode + "/enter/", guestEnterRequest);
    }

    @Then("엑세스 토큰을 받는다")
    public void 엑세스_토큰을_받는다() {
        assertAll(
                () -> assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(
                        context.response.body().jsonPath().getObject(".", GuestTokenResponse.class)).isNotNull()
        );
    }
}
