package com.mob.crossing.block;

import net.minecraft.util.StringRepresentable;

public enum PetrifiedDirtStage implements StringRepresentable {
    NORMAL("normal"),
    GROWING("growing"),
    GROWN("grown"),
    HOLE("hole");

    private final String name;

    PetrifiedDirtStage(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
