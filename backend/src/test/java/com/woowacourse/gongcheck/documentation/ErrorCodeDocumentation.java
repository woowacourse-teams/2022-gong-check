package com.woowacourse.gongcheck.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.woowacourse.gongcheck.documentation.support.ErrorCodeFieldsSnippet;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

class ErrorCodeDocumentation extends DocumentationTest {

    @Test
    public void enums() {
        List<FieldDescriptor> descriptors = Stream.of(ErrorCode.values())
                .map(errorCode -> fieldWithPath(errorCode.name()).description(errorCode.getDescription()))
                .collect(Collectors.toList());
        Map<String, Object> attributes = attributes(key("title").value("errorCode"));
        PayloadSubsectionExtractor<?> errorCode = beneathPath("data.errorCode").withSubsectionId("errorCode");
        docsGiven
                .when().get("/test/errorCodes")
                .then().log().all()
                .apply(document("errorCode",
                        new ErrorCodeFieldsSnippet("error-code", "error-code", descriptors, attributes, true, errorCode)));
    }
}
