package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Job_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.JobsResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import com.woowacourse.gongcheck.domain.space.Space;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class JobDocumentation extends DocumentationTest {

    @Test
    void 작업_조회() {
        Host host = Host_생성("1234");
        Space space = Space_생성(host, "잠실");
        when(jobService.findPage(anyLong(), anyLong(), any())).thenReturn(
                JobsResponse.of(List.of(
                                Job_생성(space, "청소"),
                                Job_생성(space, "마감")),
                        true)
        );
        when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

        docsGiven
                .header("Authorization", "Bearer jwt.token.here")
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when().get("/api/spaces/1/jobs")
                .then().log().all()
                .apply(document("jobs/list"))
                .statusCode(HttpStatus.OK.value());
    }
}
