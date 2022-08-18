package com.woowacourse.imagestorage.strategy.convert.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SystemUtils;

public abstract class WebpHandler {

    protected static Path createPlaceholder(String name) throws IOException {
        return Files.createTempFile(name, "binary");
    }

    protected static void installBinary(Path output, String source) throws IOException {
        InputStream in = WebpHandler.class.getResourceAsStream(getBinaryPath(source));
        Files.copy(in, output, StandardCopyOption.REPLACE_EXISTING);
        in.close();

        if (!SystemUtils.IS_OS_WINDOWS) {
            setExecutable(output);
        }
    }

    private static String getBinaryPath(String binaryName) {
        if (SystemUtils.IS_OS_WINDOWS) {
            return "/dist_webp_binaries/window/" + binaryName + ".exe";
        }
        if (SystemUtils.IS_OS_MAC) {
            return "/dist_webp_binaries/mac/" + binaryName;
        }
        String osArch = System.getProperty("os.arch");
        if (osArch.equals("aarch64")) {
            return "/dist_webp_binaries/linux_arm/" + binaryName;
        }
        return "/dist_webp_binaries/linux/" + binaryName;
    }

    private static boolean setExecutable(Path output) throws IOException {
        try {
            return new ProcessBuilder("chmod", "+x", output.toAbsolutePath().toString())
                    .start()
                    .waitFor(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }
}
