package net.vukrosic.custommobswordsmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.vukrosic.custommobswordsmod.CustomMobSwordsMod;

public class BlackScreen extends HandledScreen<BlackScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(CustomMobSwordsMod.MOD_ID, "textures/gui/black_screen.png");

    public BlackScreen(BlackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }


    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        //RenderSystem.setShaderColor(0, 0, 0, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth);
        int y = (height - backgroundHeight);
        drawTexture(matrices, 0, 0, width, height, width, height);

    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
