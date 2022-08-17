package com.woowacourse.imagestorage.strategy.convert;

import com.woowacourse.imagestorage.exception.FileIOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SystemUtils;

public class Gif2WebpHandler {

    private static final String GIF_TO_WEBP = "gif2webp";

    private final Path binary;

    {
        try {
            binary = createPlaceholder();
            installBinary(binary);
        } catch (IOException exception) {
            throw new FileIOException("gif to webp binary 파일을 읽을 수 없습니다.");
        }
    }

    private static Path createPlaceholder() throws IOException {
        return Files.createTempFile(GIF_TO_WEBP, "binary");
    }

    private static void installBinary(Path output) throws IOException {
        InputStream in = Gif2WebpHandler.class.getResourceAsStream(getGif2WebpBinaryPath());
        Files.copy(in, output, StandardCopyOption.REPLACE_EXISTING);
        in.close();

        if (!SystemUtils.IS_OS_WINDOWS) {
            setExecutable(output);
        }
    }

    private static String getGif2WebpBinaryPath() {
        String os = "linux";
        if (SystemUtils.IS_OS_WINDOWS) {
            os = "window";
        }
        if (SystemUtils.IS_OS_MAC) {
            os = "mac";
        }
        return "/dist_webp_binaries/" + os + "/" + Gif2WebpHandler.GIF_TO_WEBP;
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

    public byte[] convert(byte[] bytes, int q, int m, boolean lossless) throws IOException {
        Path source = Files.createTempFile("input", "gif").toAbsolutePath();
        Path target = Files.createTempFile("to_webp", "webp").toAbsolutePath();
        try {
            Files.write(source, bytes, StandardOpenOption.CREATE);
            convert(source, target, q, m, lossless);
            return Files.readAllBytes(target);
        } finally {
            source.toFile()
                    .delete();
            target.toFile()
                    .delete();
        }
    }

    private void convert(Path source, Path target, int q, int m, boolean lossless) throws IOException {
        Path stdout = Files.createTempFile("stdout", "webp");
        List<String> commands = createCommands(q, m, lossless);
        commands.add(source.toAbsolutePath().toString());
        commands.add("-o");
        commands.add(target.toAbsolutePath().toString());

        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);
        builder.redirectOutput(stdout.toFile());

        Process process = builder.start();
        try {
            process.waitFor(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
        int exitStatus = process.exitValue();
        if (exitStatus != 0) {
            List<String> error = Files.readAllLines(stdout);
            throw new IOException(error.toString());
        }
    }

    private List<String> createCommands(int q, int m, boolean lossless) {
        List<String> commands = new ArrayList<>();
        commands.add(binary.toAbsolutePath().toString());
        if (q >= 0) {
            commands.add("-q");
            commands.add(q + "");
        }
        if (m >= 0) {
            commands.add("-m");
            commands.add(m + "");
        }
        if (lossless) {
            commands.add("-lossless");
        }
        return commands;
    }
}
