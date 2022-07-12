package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Host_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.host.Host;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SpaceDocumentation extends DocumentationTest {

    @Nested
    class 공간을_조회한다 {

        @Test
        void 공간_조회에_성공한다() {
            Host host = Host_생성("1234");
            when(spaceService.findPage(anyLong(), any())).thenReturn(
                    SpacesResponse.of(List.of(
                                    Space_생성(host, "잠실"),
                                    Space_생성(host, "선릉")),
                            true)
            );
            when(authenticationContext.getPrincipal()).thenReturn(String.valueOf(anyLong()));

            docsGiven
                    .header("Authorization", "Bearer jwt.token.here")
                    .queryParam("page", 0)
                    .queryParam("size", 2)
                    .when().get("/api/spaces")
                    .then().log().all()
                    .apply(document("spaces/list"))
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
