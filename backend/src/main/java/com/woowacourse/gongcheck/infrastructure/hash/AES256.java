package com.woowacourse.gongcheck.infrastructure.hash;

import com.woowacourse.gongcheck.auth.application.Hashable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AES256 implements Hashable {

    private static final int SECRET_KEY_SIZE = 32;
    private static final int INITIALIZATION_VECTOR_SIZE = 16;
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_MODE = "CBC";
    private static final String PADDING = "PKCS5Padding";
    private static final String transformation = String.format("%s/%s/%s", ALGORITHM, CIPHER_MODE, PADDING);

    private final SecretKeySpec secretKeySpec;
    private final IvParameterSpec ivParamSpec;

    public AES256(@Value("${security.hash.secret-key}") final String secretKey) {
        if (secretKey.length() < SECRET_KEY_SIZE) {
            throw new IllegalArgumentException(
                    "Minimum key size is " + SECRET_KEY_SIZE + ", current key size :" + secretKey.length());
        }
        secretKeySpec = new SecretKeySpec(secretKey.substring(0, SECRET_KEY_SIZE).getBytes(), ALGORITHM);
        ivParamSpec = new IvParameterSpec(secretKey.substring(0, INITIALIZATION_VECTOR_SIZE).getBytes());
    }

    @Override
    public String encode(String input) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParamSpec);
            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64URLSafeString(encrypted);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            // TODO: 2022/08/02 Infrastructure 예외로 변경 필요
            log.error("encoding error, input = {}, message = {}", input, e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String decode(String input) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParamSpec);
            byte[] decodedBytes = Base64.decodeBase64URLSafe(input);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            // TODO: 2022/08/02 Infrastructure 예외로 변경 필요
            log.error("decoding error, input = {}, message = {}", input, e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }
}
