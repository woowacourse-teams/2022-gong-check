package com.woowacourse.gongcheck.documentation.support;

public class CustomDocumentationResponse<T> {

    private final T data;

    private CustomDocumentationResponse(final T data) {
        this.data = data;
    }

    public static <T> CustomDocumentationResponse<T> of(T data) {
        return new CustomDocumentationResponse<>(data);
    }

    public T getData() {
        return data;
    }
}
