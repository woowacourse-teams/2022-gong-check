package com.woowacourse.gongcheck.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.woowacourse.gongcheck.auth.application.GuestAuthService;
import com.woowacourse.gongcheck.auth.application.HostAuthService;
import com.woowacourse.gongcheck.auth.domain.AuthenticationContext;
import com.woowacourse.gongcheck.auth.presentation.GuestAuthController;
import com.woowacourse.gongcheck.auth.presentation.HostAuthController;
import com.woowacourse.gongcheck.core.application.AlertService;
import com.woowacourse.gongcheck.core.application.HostService;
import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.core.application.JobService;
import com.woowacourse.gongcheck.core.application.SpaceService;
import com.woowacourse.gongcheck.core.application.SubmissionService;
import com.woowacourse.gongcheck.core.application.TaskService;
import com.woowacourse.gongcheck.core.presentation.HostController;
import com.woowacourse.gongcheck.core.presentation.JobController;
import com.woowacourse.gongcheck.core.presentation.SpaceController;
import com.woowacourse.gongcheck.core.presentation.SubmissionController;
import com.woowacourse.gongcheck.core.presentation.TaskController;
import com.woowacourse.gongcheck.infrastructure.jwt.JjwtTokenProvider;
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
        HostAuthController.class,
        GuestAuthController.class,
        SpaceController.class,
        JobController.class,
        TaskController.class,
        SubmissionController.class,
        HostController.class
})
@ExtendWith(RestDocumentationExtension.class)
class DocumentationTest {

    protected MockMvcRequestSpecification docsGiven;

    @MockBean
    protected HostAuthService hostAuthService;

    @MockBean
    protected GuestAuthService guestAuthService;

    @MockBean
    protected SpaceService spaceService;

    @MockBean
    protected JobService jobService;

    @MockBean
    protected TaskService taskService;

    @MockBean
    protected SubmissionService submissionService;

    @MockBean
    protected AlertService alertService;

    @MockBean
    protected HostService hostService;

    @MockBean
    protected JjwtTokenProvider jwtTokenProvider;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected ImageUploader imageUploader;

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
