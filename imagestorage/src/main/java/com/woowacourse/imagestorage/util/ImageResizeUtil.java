package com.woowacourse.imagestorage.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageResizeUtil {

    private ImageResizeUtil() {
        throw new AssertionError();
    }

    public static BufferedImage resizedByWidth(final BufferedImage image, final int width) {
        double resizeRatio = calculateRatio(image.getWidth(), width);
        int scaledWidth = resizeLengthByRatio(image.getWidth(), resizeRatio);
        int scaledHeight = resizeLengthByRatio(image.getHeight(), resizeRatio);
        return resizeImage(image, scaledWidth, scaledHeight);
    }

    private static double calculateRatio(final int originWitdh, final int changeWidth) {
        return (double) changeWidth / originWitdh;
    }

    private static int resizeLengthByRatio(final int length, final double resizeRatio) {
        return (int) (length * resizeRatio);
    }

    private static BufferedImage resizeImage(final BufferedImage image, final int width, final int height) {
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
