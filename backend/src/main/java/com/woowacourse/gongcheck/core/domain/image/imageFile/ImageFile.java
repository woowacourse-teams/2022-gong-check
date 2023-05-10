package com.woowacourse.gongcheck.core.domain.image.imageFile;

import static org.springframework.util.StringUtils.getFilenameExtension;

import com.woowacourse.gongcheck.exception.BusinessException;
import com.woowacourse.gongcheck.exception.ErrorCode;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageFile {

    private static final Pattern IMAGE_FILE_EXTENSION_PATTERN = Pattern.compile("^(png|jpeg|jpg|svg|gif)$");

    private final String originFileName;
    private final String contentType;
    private final String extension;
    private final byte[] imageBytes;

    public ImageFile(final String originFileName, final String contentType, final String extension,
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
        validateImageExtension(multipartFile);

        try {
            return new ImageFile(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                    getFilenameExtension(multipartFile.getOriginalFilename()), multipartFile.getBytes());
        } catch (IOException exception) {
            String message = String.format("잘못된 파일입니다. multipartFile = %s", multipartFile.getName());
            throw new BusinessException(message, ErrorCode.IM05);
        }
    }

    private static void validateNullFile(final MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile)) {
            String message = "이미지 파일은 null이 들어올 수 없습니다.";
            throw new BusinessException(message, ErrorCode.IM01);
        }
    }

    private static void validateEmptyFile(final MultipartFile multipartFile) {
        if (multipartFile.getSize() == 0) {
            String message = String.format("이미지 파일은 빈값이 들어올 수 없습니다. multipartFile = %s", multipartFile.getName());
            throw new BusinessException(message, ErrorCode.IM02);
        }
    }

    private static void validateNullFileName(final MultipartFile multipartFile) {
        if (Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            String message = String.format("이미지 파일 이름은 빈값이 들어올 수 없습니다. multipartFile = %s", multipartFile.getName());
            throw new BusinessException(message, ErrorCode.IM03);
        }
    }

    private static void validateImageExtension(final MultipartFile multipartFile) {
        String fileExtension = getFilenameExtension(multipartFile.getOriginalFilename());
        assert fileExtension != null;
        if (!IMAGE_FILE_EXTENSION_PATTERN.matcher(fileExtension).matches()) {
            String message = String.format("이미지 파일 확장자만 들어올 수 있습니다. fileExtension = %s", fileExtension);
            throw new BusinessException(message, ErrorCode.IM04);
        }
    }

    public ByteArrayResource inputStream() {
        return new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return randomName();
            }
        };
    }

    private String randomName() {
        return UUID.randomUUID().toString() + "." + extension;
    }
}
