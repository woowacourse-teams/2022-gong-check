package com.woowacourse.gongcheck.documentation.support;

import com.woowacourse.gongcheck.exception.ErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.snippet.TemplatedSnippet;

public class ErrorCodeFieldsSnippet extends TemplatedSnippet {

    public ErrorCodeFieldsSnippet(final String snippetName, final String templateName) {
        super(snippetName, templateName, null);
    }

    @Override
    protected Map<String, Object> createModel(final Operation operation) {
        Map<String, Object> model = new HashMap<>();
        List<Map<String, Object>> fields = new ArrayList<>();
        model.put("fields", fields);
        addErrorCodes(fields);
        return model;
    }

    private void addErrorCodes(final List<Map<String, Object>> fields) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            Map<String, Object> model = new HashMap<>();
            model.put("path", errorCode.name());
            model.put("type", "String");
            model.put("description", errorCode.getDescription());
            model.put("optional", false);
            fields.add(model);
        }
    }
}
