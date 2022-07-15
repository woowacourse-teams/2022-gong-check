package com.woowacourse.gongcheck.application;


import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(MultipartFile file, String directoryName);
}
