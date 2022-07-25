package com.woowacourse.gongcheck.core.application;


import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(MultipartFile file, String directoryName);
}
