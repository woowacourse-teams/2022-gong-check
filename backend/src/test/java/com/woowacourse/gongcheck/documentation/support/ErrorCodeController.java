package com.woowacourse.gongcheck.documentation.support;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ErrorCodeController {

    @GetMapping("/errorCodes")
    public void findErrorCodes() {
    }
}
