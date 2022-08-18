package com.woowacourse.imagestorage.strategy.convert.handler;

import com.woowacourse.imagestorage.exception.FileIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Gif2WebpHandler extends WebpHandler {

    private static final String GIF_TO_WEBP = "gif2webp";

    private static final Path binary;

    static {
        try {
            binary = createPlaceholder(GIF_TO_WEBP);
            installBinary(binary, GIF_TO_WEBP);
        } catch (IOException exception) {
            throw new FileIOException("gif to webp binary 파일을 읽을 수 없습니다.");
        }
    }

    public byte[] convert(byte[] bytes) throws IOException {
        Path source = Files.createTempFile("input", "gif").toAbsolutePath();
        Path target = Files.createTempFile("to_webp", "webp").toAbsolutePath();
        try {
            Files.write(source, bytes, StandardOpenOption.CREATE);
            convert(source, target);
            return Files.readAllBytes(target);
        } finally {
            source.toFile()
                    .delete();
            target.toFile()
                    .delete();
        }
    }

    private void convert(Path source, Path target) throws IOException {
        Path stdout = Files.createTempFile("stdout", "webp");
        List<String> commands = new ArrayList<>();
        commands.add(binary.toAbsolutePath().toString());
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
        checkSuccessProcess(stdout, process);
    }
}
