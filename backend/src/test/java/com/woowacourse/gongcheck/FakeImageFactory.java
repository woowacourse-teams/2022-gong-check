package com.woowacourse.gongcheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class FakeImageFactory {

    @NotNull
    public static File createFakeImage() throws IOException {
        File fakeImage = File.createTempFile("temp", ".jpg");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fakeImage))) {
            writer.write("1234");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fakeImage;
    }
}
