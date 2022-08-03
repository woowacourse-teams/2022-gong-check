package com.woowacourse.gongcheck.infrastructure.imageuploader;

import com.woowacourse.gongcheck.core.application.ImageUploader;
import com.woowacourse.gongcheck.core.application.response.ImageUrlResponse;
import com.woowacourse.gongcheck.core.domain.image.imageFile.ImageFile;
import com.woowacourse.gongcheck.exception.BusinessException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class OwnServerImageUploader implements ImageUploader {

    private final Path storageLocation;
    private final String imagePathPrefix;

    public OwnServerImageUploader(final Path storageLocation, final String imagePathPrefix) {
        this.storageLocation = storageLocation;
        this.imagePathPrefix = imagePathPrefix;
    }

    @Override
    public ImageUrlResponse upload(final MultipartFile image, final String directoryName) {
        ImageFile imageFile = ImageFile.from(image);

        try {
            String imageFileInputName = imageFile.randomName();
            Path fileStorageLocation = storageLocation.resolve(imageFileInputName);
            Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            return ImageUrlResponse.from(imagePathPrefix + imageFileInputName);
        } catch (IOException exception) {
            throw new BusinessException("파일 업로드 시 문제가 발생했습니다.");
        }
    }
}
