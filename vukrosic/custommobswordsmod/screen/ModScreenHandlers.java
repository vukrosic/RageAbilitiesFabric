package net.vukrosic.custommobswordsmod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<BlackScreenHandler> BLACK_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        BLACK_SCREEN_HANDLER = new ScreenHandlerType<>(BlackScreenHandler::new);
    }
}
