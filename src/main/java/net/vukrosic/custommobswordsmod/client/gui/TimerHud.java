package net.vukrosic.custommobswordsmod.client.gui;
// https://github.com/johnpyp/speedrun-timer/blob/master/src/main/java/com/johnpyp/speedruntimer/Hud.java
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class TimerHud {
    private final List<TextStuff> textList;
    private final TextRenderer textRenderer;
    private final MatrixStack matrixStack;
    private final int xOffset;
    private final int yOffset;
    private int maxLen;
    private int lastY;
    private int lastX;

    TimerHud(TextRenderer textRenderer, int xOffset, int yOffset) {
        this.textRenderer = textRenderer;
        this.xOffset = lastX = xOffset;
        this.yOffset = lastY = yOffset;
        matrixStack = new MatrixStack();
        textList = new ArrayList<>();
    }

    public TimerHud print(String text, int color) {
        maxLen = Math.max(maxLen, textRenderer.getWidth(text));
        textList.add(new TextStuff(text, lastX, lastY, color));
        lastX = lastX + textRenderer.getWidth(text);
        return this;
    }

    public TimerHud println(String text, int heightOffset, int color) {
        maxLen = Math.max(maxLen, textRenderer.getWidth(text));
        lastY = lastY + heightOffset;
        lastX = xOffset;
        textList.add(new TextStuff(text, lastX, lastY, color));
        return this;
    }

    public void render(int backgroundPadding, int backgroundColor, double backgroundTransparency) {
        drawBackground(backgroundPadding, backgroundColor, backgroundTransparency);
        for (TextStuff textStuff : textList) {
            textRenderer.drawWithShadow(
                    matrixStack, textStuff.text, textStuff.x, textStuff.y, textStuff.color);
        }
    }

    public TimerHud insertSpace(int height) {
        lastY = lastY + height;
        return this;
    }

    private void drawBackground(int padding, int color, double transparency) {
        int alphaColor = ((int)(transparency * 255) << 24) & 0xFF000000 | color;
        DrawableHelper.fill(
                matrixStack,
                xOffset - padding,
                yOffset - padding,
                xOffset + maxLen + padding,
                lastY + textRenderer.fontHeight + padding,
                alphaColor);
    }

    static final class TextStuff {
        public final String text;
        public final int x;
        public final int y;
        public final int color;

        TextStuff(String text, int x, int y, int color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
}
