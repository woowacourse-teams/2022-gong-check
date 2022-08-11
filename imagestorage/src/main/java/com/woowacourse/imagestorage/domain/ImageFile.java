package com.woowacourse.imagestorage.domain;

import static org.springframework.util.StringUtils.getFilenameExtension;

import com.woowacourse.imagestorage.exception.BusinessException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

    private final String originFileName;
    private final String contentType;
    private final ImageExtension extension;
    private final byte[] imageBytes;

    public ImageFile(final String originFileName, final String contentType, final ImageExtension extension,
                     final byte[] imageBytes) {
        this.originFileName = originFileName;
        this.contentType = contentType;
        this.extension = extension;
        this.imageBytes = imageBytes;
    }

    public static ImageFile from(final MultipartFile multipartFile) {
        validateNullFile(multipartFile);
        validateEmptyFile(multipartFile);
        validateNullFileName(multipartFile);

        try {
            return new ImageFile(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                    ImageExtension.from(getFilenameExtension(multipartFile.getOriginalFilename())), multipartFile.getBytes());
        } catch (IOException exception) {
            throw new BusinessException("잘못된 파일입니다.");
        }
    }

    private static void validateNullFile(final MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile)) {
            throw new BusinessException("이미지 파일은 null이 들어올 수 없습니다.");
        }
    }

    private static void validateEmptyFile(final MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0) {
            throw new BusinessException("이미지 파일은 빈값이 들어올 수 없습니다.");
        }
    }

    private static void validateNullFileName(final MultipartFile multipartFile) {
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            throw new BusinessException("이미지 파일 이름은 빈값이 들어올 수 없습니다.");
        }
    }

    public ImageFile resizeImage(final int width) {
        return new ImageFile(
                originFileName,
                contentType,
                extension,
                extension.resizeImage(imageBytes, width)
        );
    }

    public InputStream inputStream() {
        return new ByteArrayInputStream(imageBytes);
    }

    public long length() {
        return imageBytes.length;
    }

    public String randomName() {
        return UUID.randomUUID().toString() + "." + extension;
    }

    public String contentType() {
        return contentType;
    }
}
