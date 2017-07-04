package net.gegy1000.wearables.server.util;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.text.TextFormatting;

import java.awt.Color;

public class WearableColourUtils {
    public static final int[] TEXT_COLOURS = new int[16];

    static {
        for (int i = 0; i < 16; ++i) {
            int offset = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + offset;
            int green = (i >> 1 & 1) * 170 + offset;
            int blue = (i & 1) * 170 + offset;
            if (i == 6) {
                red += 85;
            }
            TEXT_COLOURS[i] = WearableColourUtils.fromRGB(red, green, blue);
        }
    }

    public static int fromRGBFloatArray(float[] rgb) {
        int red = (int) (rgb[0] * 255);
        int green = (int) (rgb[1] * 255);
        int blue = (int) (rgb[2] * 255);
        return WearableColourUtils.fromRGB(red, green, blue);
    }

    public static int fromRGB(int red, int green, int blue) {
        return (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
    }

    public static int fromHSVFloatArray(float[] hsv) {
        return Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
    }

    public static float[] toRGBFloatArray(int colour) {
        int red = (colour >> 16) & 0xFF;
        int green = (colour >> 8) & 0xFF;
        int blue = colour & 0xFF;
        return new float[] { red / 255.0F, green / 255.0F, blue / 255.0F };
    }

    public static float[] toHSVFloatArray(float[] colour) {
        return Color.RGBtoHSB((int) (colour[0] * 255), (int) (colour[1] * 255), (int) (colour[2] * 255), null);
    }

    public static float[] toHSVFloatArray(int colour) {
        int red = (colour >> 16) & 0xFF;
        int green = (colour >> 8) & 0xFF;
        int blue = colour & 0xFF;
        return Color.RGBtoHSB(red, green, blue, null);
    }

    public static int getDyeColour(EnumDyeColor color) {
        return WearableColourUtils.fromRGBFloatArray(EntitySheep.getDyeRgb(color));
    }

    public static TextFormatting getClosest(int colour) {
        return WearableColourUtils.getClosest(WearableColourUtils.toRGBFloatArray(colour));
    }

    public static TextFormatting getClosest(float[] colour) {
        colour = WearableColourUtils.toHSVFloatArray(colour);
        TextFormatting closest = TextFormatting.WHITE;
        float closestDelta = Float.MAX_VALUE;
        for (int i = 0; i < TEXT_COLOURS.length; i++) {
            float[] dyeColour = WearableColourUtils.toHSVFloatArray(WearableColourUtils.TEXT_COLOURS[i]);
            float deltaHue = Math.abs(dyeColour[0] - colour[0]);
            float deltaSaturation = Math.abs(dyeColour[1] - colour[1]);
            float deltaBrightness = Math.abs(dyeColour[2] - colour[2]);
            float delta = deltaHue + deltaSaturation + deltaBrightness;
            if (delta < closestDelta) {
                closestDelta = delta;
                closest = TextFormatting.fromColorIndex(i);
            }
        }
        return closest;
    }

    public static int blend(int first, int second, float ratio) {
        float[] firstArray = WearableColourUtils.toRGBFloatArray(first);
        float[] secondArray = WearableColourUtils.toRGBFloatArray(second);
        float[] blend = WearableColourUtils.blend(firstArray, secondArray, ratio);
        return WearableColourUtils.fromRGBFloatArray(blend);
    }

    public static float[] blend(float[] first, float[] second, float ratio) {
        float invertedRatio = 1.0F - ratio;
        float blendedRed = first[0] * invertedRatio + second[0] * ratio;
        float blendedGreen = first[1] * invertedRatio + second[1] * ratio;
        float blendedBlue = first[2] * invertedRatio + second[2] * ratio;
        return new float[] { blendedRed, blendedGreen, blendedBlue };
    }
}
