package com.woowacourse.gongcheck.presentation;

import com.woowacourse.gongcheck.application.GuestAuthService;
import com.woowacourse.gongcheck.application.JwtTokenProvider;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest({
        GuestAuthController.class
})
class ControllerTest {

    protected MockMvcRequestSpecification given;

    @MockBean
    protected GuestAuthService guestAuthService;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setDocsGiven(WebApplicationContext webApplicationContext) {
        given = RestAssuredMockMvc.given()
                .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .build()).log().all();
    }
}
