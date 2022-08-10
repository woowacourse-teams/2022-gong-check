package com.woowacourse.imagestorage.application;

import com.woowacourse.imagestorage.application.response.ImageResponse;
import com.woowacourse.imagestorage.domain.ImageFile;
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

    public ImageResponse storeImage(MultipartFile image) throws IOException {
        ImageFile imageFile = ImageFile.from(image)
                .resizeImage(500);

        String imageFileInputName = imageFile.randomName();
        Path fileStorageLocation = storageLocation.resolve(imageFileInputName);
        Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
        return new ImageResponse(imagePathPrefix + imageFileInputName);
    }
}
