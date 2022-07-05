package com.woowacourse.gongcheck.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.woowacourse.gongcheck.application.GuestAuthService;
import com.woowacourse.gongcheck.application.JjwtTokenProvider;
import com.woowacourse.gongcheck.application.SpaceService;
import com.woowacourse.gongcheck.presentation.AuthenticationContext;
import com.woowacourse.gongcheck.presentation.GuestAuthController;
import com.woowacourse.gongcheck.presentation.SpaceController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest({
        GuestAuthController.class,
        SpaceController.class
})
@ExtendWith(RestDocumentationExtension.class)
class DocumentationTest {

    protected MockMvcRequestSpecification docsGiven;

    @MockBean
    protected GuestAuthService guestAuthService;

    @MockBean
    protected SpaceService spaceService;

    @MockBean
    protected JjwtTokenProvider jwtTokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @BeforeEach
    void setDocsGiven(final WebApplicationContext webApplicationContext,
                      final RestDocumentationContextProvider restDocumentation) {
        docsGiven = RestAssuredMockMvc.given()
                .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .apply(documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint()))
                        .build()).log().all();
    }
}
