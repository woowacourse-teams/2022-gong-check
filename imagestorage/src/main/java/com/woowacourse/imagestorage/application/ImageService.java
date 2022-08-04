package com.woowacourse.imagestorage.application;

import com.woowacourse.imagestorage.application.response.ImageResponse;
import com.woowacourse.imagestorage.domain.ImageFile;
import com.woowacourse.imagestorage.exception.BusinessException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final Path storageLocation;
    private final String imagePathPrefix;

    public ImageService(@Value("${file.upload-dir}") final String storageLocation,
                        @Value("${file.server-path}") final String imagePathPrefix) {
        this.storageLocation = Paths.get(storageLocation);
        this.imagePathPrefix = imagePathPrefix;
    }

    public ImageResponse storeImage(MultipartFile image) {
        ImageFile imageFile = ImageFile.from(image);

        try {
            String imageFileInputName = imageFile.randomName();
            Path fileStorageLocation = storageLocation.resolve(imageFileInputName);
            Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            return new ImageResponse(imagePathPrefix + imageFileInputName);
        } catch (IOException exception) {
            throw new BusinessException("파일 업로드 시 문제가 발생했습니다.");
        }
    }
}
