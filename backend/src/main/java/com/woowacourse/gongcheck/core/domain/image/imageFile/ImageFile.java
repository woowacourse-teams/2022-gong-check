package com.woowacourse.gongcheck.core.domain.image.imageFile;

import static org.springframework.util.StringUtils.getFilenameExtension;

import com.woowacourse.gongcheck.exception.BusinessException;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public class ImageFile {

    private static final Pattern IMAGE_FILE_EXTENSION_PATTERN = Pattern.compile("^(png|jpeg|jpg|svg)$");

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

    private static void validateImageExtension(final MultipartFile multipartFile) {
        String fileExtension = getFilenameExtension(multipartFile.getOriginalFilename());
        assert fileExtension != null;
        if (!IMAGE_FILE_EXTENSION_PATTERN.matcher(fileExtension).matches()) {
            throw new BusinessException("이미지 파일 확장자만 들어올 수 있습니다.");
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
