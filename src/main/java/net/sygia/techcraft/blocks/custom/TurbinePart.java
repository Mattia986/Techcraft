package net.sygia.techcraft.blocks.custom;

import net.minecraft.util.StringRepresentable;

public enum TurbinePart implements StringRepresentable {
    LOWER, MIDDLE, UPPER;

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}
