package com.woowacourse.imagestorage.application;

import com.woowacourse.imagestorage.application.response.ImageResponse;
import com.woowacourse.imagestorage.domain.ImageFile;
import com.woowacourse.imagestorage.exception.FileIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

    private final Path storageLocation;
    private final String imagePathPrefix;

    public ImageService(@Value("${file.upload-dir}") final String storageLocation,
                        @Value("${file.server-path}") final String imagePathPrefix) {
        this.storageLocation = Paths.get(storageLocation);
        this.imagePathPrefix = imagePathPrefix;
    }

    public ImageResponse storeImage(final MultipartFile image) {
        try {
            ImageFile imageFile = ImageFile.from(image);

            String imageFileInputName = imageFile.randomName();
            Path fileStorageLocation = storageLocation.resolve(imageFileInputName);
            Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            return new ImageResponse(imagePathPrefix + imageFileInputName);
        } catch (IOException exception) {
            throw new FileIOException("이미지 저장 시 예외가 발생했습니다.");
        }
    }
}
