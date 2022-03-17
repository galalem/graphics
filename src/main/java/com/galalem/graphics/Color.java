package com.galalem.graphics;

import androidx.annotation.IntRange;

@SuppressWarnings("unused")
public class Color {

    private final int color;
    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;
    private final String Hex;


    /**
     * Create a color from the given integer value
     * @param color integer value
     */
    public Color(int color) {
        this.color = color;
        this.alpha = (color >> 24) & 0xff;
        this.red = (color >> 16) & 0xff;
        this.green = (color >> 8) & 0xff;
        this.blue = color & 0xff;
        this.Hex = valueOf(color);
    }

    /**
     * Create a color from the given hexadecimal value
     * @param hex hexadecimal value
     *            Note: it can be preceded with '#', it is not case sensitive
     *            Format violation will result int transparent color
     */
    public Color(String hex) {
        this(valueOf(hex));
    }

    /**
     * Create an opaque color from the given RGB values
     * @param red red color saturation value (range 0~255)
     * @param green green color saturation value (range 0~255)
     * @param blue blue color saturation value (range 0~255)
     */
    public Color(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green, @IntRange(from = 0, to = 255) int blue) {
        this(red, green, blue, 0xff);
    }

    /**
     * Create an color from the given RGB values
     * @param red red color saturation value (range 0~255)
     * @param green green color saturation value (range 0~255)
     * @param blue blue color saturation value (range 0~255)
     * @param alpha opacity value (range 0~255)
     */
    public Color(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green, @IntRange(from = 0, to = 255) int blue, @IntRange(from = 0, to = 255) int alpha) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.color = valueOf(red, green, blue, alpha);
        this.Hex = valueOf(color);
    }

    public int getIntValue() {
        return color;
    }

    public String getHexValue() {
        return Hex;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public static int valueOf(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green, @IntRange(from = 0, to = 255) int blue) {
        return valueOf(red, green, blue, 0xff);
    }

    public static int valueOf(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green, @IntRange(from = 0, to = 255) int blue, @IntRange(from = 0, to = 255) int alpha) {
        return (alpha & 0xff) << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
    }

    public static int valueOf(String colorString) {
        if (!colorString.matches("#?[0-9a-fA-F]{3,8}"))
            return 0;

        if (colorString.charAt(0) == '#')
            colorString = colorString.substring(1);

        if (colorString.length() == 7 || colorString.length() == 5)
            return 0;

        colorString = colorString.toUpperCase();

        return Long.valueOf((colorString.length() % 3 == 0 ? "FF" : "") + (colorString.length() < 6 ? duplicateChars(colorString) : colorString), 16).intValue();
    }

    public static String valueOf(int color) {
        long value = color;
        if (value < 0) value = value & 0xffffffffL;
        StringBuilder builder = new StringBuilder(Long.toString(value, 16).toUpperCase());
        while (builder.length() < 8)
            builder.insert(0,0);
        if (builder.toString().startsWith("FF"))
            builder.delete(0,2);
        return builder.toString();
    }

    private static String duplicateChars(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
            builder.append(str.charAt(i)).append(str.charAt(i));
        return builder.toString();
    }
}
