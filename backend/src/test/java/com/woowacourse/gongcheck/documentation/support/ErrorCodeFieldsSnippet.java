package com.woowacourse.gongcheck.documentation.support;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

public class ErrorCodeFieldsSnippet extends AbstractFieldsSnippet {

    public ErrorCodeFieldsSnippet(final String name,
                                  final String type,
                                  final List<FieldDescriptor> descriptors,
                                  final Map<String, Object> attributes,
                                  final boolean ignoreUndocumentedFields,
                                  final PayloadSubsectionExtractor<?> subsectionExtractor) {
        super(name, type, descriptors, attributes, ignoreUndocumentedFields, subsectionExtractor);
    }

    @Override
    protected MediaType getContentType(final Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(final Operation operation) {
        return operation.getResponse().getContent();
    }
}
