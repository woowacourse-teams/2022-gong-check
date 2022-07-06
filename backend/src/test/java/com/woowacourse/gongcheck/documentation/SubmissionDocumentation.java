package com.woowacourse.gongcheck.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.presentation.request.SubmissionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class SubmissionDocumentation extends DocumentationTest {

    @Test
    void 현재_진행중인_작업이_모두_완료된_상태로_제출한다() {
        doNothing().when(submissionService).submitJobCompletion(anyLong(), anyLong(), any());
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        SubmissionRequest submissionRequest = new SubmissionRequest("제출자");
        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(submissionRequest)
                .when().post("/api/jobs/1/complete")
                .then().log().all()
                .apply(document("submissions/submit"))
                .statusCode(HttpStatus.OK.value());
    }
}
