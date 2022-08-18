package com.woowacourse.imagestorage.application;

import com.woowacourse.imagestorage.application.response.ImageResponse;
import com.woowacourse.imagestorage.application.response.ImageSaveResponse;
import com.woowacourse.imagestorage.domain.ImageExtension;
import com.woowacourse.imagestorage.domain.ImageFile;
import com.woowacourse.imagestorage.exception.FileIOException;
import com.woowacourse.imagestorage.exception.FileIONotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

    private static final MediaType IMAGE_WEBP = new MediaType("image", "webp");

    private final Path storageLocation;
    private final String imagePathPrefix;

    public ImageService(@Value("${file.upload-dir}") final String storageLocation,
                        @Value("${file.server-path}") final String imagePathPrefix) {
        this.storageLocation = Paths.get(storageLocation);
        this.imagePathPrefix = imagePathPrefix;
    }

    public ImageSaveResponse storeImage(final MultipartFile image) {
        try {
            ImageFile imageFile = ImageFile.from(image);

            String imageFileInputName = imageFile.randomName();
            Path fileStorageLocation = resolvePath(imageFileInputName);
            Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            return new ImageSaveResponse(imagePathPrefix + imageFileInputName);
        } catch (IOException exception) {
            throw new FileIOException("이미지 저장 시 예외가 발생했습니다.");
        }
    }

    public ImageResponse resizeImage(final String imageUrl, final int width, final boolean isWebp) {
        try {
            Path fileStorageLocation = resolvePath(imageUrl);
            File file = fileStorageLocation.toFile();
            ImageExtension imageExtension = ImageExtension.from(FilenameUtils.getExtension(file.getName()));
            byte[] originImage = IOUtils.toByteArray(new FileInputStream(file));
            byte[] resizedImage = imageExtension.resizeImage(originImage, width);

            if (isWebp) {
                return ImageResponse.of(imageExtension.convertToWebp(resizedImage), IMAGE_WEBP);
            }
            return ImageResponse.of(resizedImage, imageExtension.getContentType());
        } catch (FileNotFoundException exception) {
            throw new FileIONotFoundException("파일 경로에 파일이 존재하지 않습니다.");
        } catch (IOException exception) {
            throw new FileIOException("이미지 파일 변환 시 예외가 발생했습니다.");
        }
    }

    private Path resolvePath(final String imageUrl) {
        return storageLocation.resolve(imageUrl);
    }
}
