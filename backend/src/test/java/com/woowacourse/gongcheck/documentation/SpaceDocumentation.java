package com.woowacourse.gongcheck.documentation;

import static com.woowacourse.gongcheck.fixture.FixtureFactory.Member_생성;
import static com.woowacourse.gongcheck.fixture.FixtureFactory.Space_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.application.response.SpacesResponse;
import com.woowacourse.gongcheck.domain.member.Member;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SpaceDocumentation extends DocumentationTest {

    @Test
    void 공간_조회() {

        Member host = Member_생성("1234");
        when(spaceService.findPage(anyLong(), any())).thenReturn(
                SpacesResponse.of(List.of(
                                Space_생성(host, "space1"),
                                Space_생성(host, "space2")),
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
