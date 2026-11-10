package com.mob.crossing.block;

import com.mob.crossing.MobCrossing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class PetrifiedDirtBlockEntity extends BlockEntity {
    public static final int BRUSHES_REQUIRED = 5;
    private static final int BRUSH_RESET_TICKS = 40;
    private static final int BRUSH_COOLDOWN_TICKS = 10;
    private int brushCount;
    private long brushEndsAtTick;
    private long brushCooldownEndsAtTick;

    public PetrifiedDirtBlockEntity(BlockPos pos, BlockState state) {
        super(MobCrossing.PETRIFIED_DIRT_BLOCK_ENTITY.get(), pos, state);
    }

    public void startBrushing(long currentTick) {
        this.brushCount++;
        this.brushEndsAtTick = currentTick + BRUSH_RESET_TICKS;
        this.brushCooldownEndsAtTick = currentTick + BRUSH_COOLDOWN_TICKS;
    }

    public void checkTimeout(long currentTick) {
        if (brushCount > 0 && currentTick > brushEndsAtTick) {
            brushCount = 0;
            brushEndsAtTick = 0;
            brushCooldownEndsAtTick = 0;
            BlockState state = getBlockState();
            if (state.getValue(PetrifiedDirtBlock.STAGE) == PetrifiedDirtStage.GROWN) {
                level.setBlock(worldPosition, state.setValue(BlockStateProperties.DUSTED, 0), 3);
            }
        }
    }

    public boolean canBrush(long currentTick) {
        return brushCount == 0 || currentTick >= brushCooldownEndsAtTick;
    }

    public int getBrushCount() {
        return brushCount;
    }
}
