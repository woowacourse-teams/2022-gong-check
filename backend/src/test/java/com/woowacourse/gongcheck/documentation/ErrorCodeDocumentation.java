package com.woowacourse.gongcheck.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.woowacourse.gongcheck.documentation.support.ErrorCodeFieldsSnippet;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class ErrorCodeDocumentation extends DocumentationTest {

    @Test
    public void errorCodes() throws IOException {
        ErrorCodeFieldsSnippet errorCodeFieldsSnippet = new ErrorCodeFieldsSnippet("error-code", "error-code-template");
        docsGiven
                .when().get("/test/errorCodes")
                .then().log().all()
                .apply(document("errorCode", errorCodeFieldsSnippet));
    }
}
