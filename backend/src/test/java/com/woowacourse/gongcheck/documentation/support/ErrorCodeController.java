package com.woowacourse.gongcheck.documentation.support;

import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ErrorCodeController {

    @GetMapping("/errorCodes")
    public CustomDocumentationResponse<ErrorCodes> findErrorCodes() {
        Map<String, String> errorCodes = getDocs(ErrorCode.values());
        return CustomDocumentationResponse.of(new ErrorCodes(errorCodes));
    }

    private Map<String, String> getDocs(ErrorCode[] errorCodes) {
        return Arrays.stream(errorCodes)
                .collect(Collectors.toMap(ErrorCode::name, ErrorCode::getDescription));
    }
}
