package net.vukrosic.custommobswordsmod.util.custom;

public interface InGameHudMixinExt {
    void setPreyHealth(float preyHealth);
    void setShowPreyHealthbar(boolean showPreyHealthbar);

    boolean getShowPreyHealthbar();
}
