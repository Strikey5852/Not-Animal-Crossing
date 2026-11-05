package com.mob.crossing.block;

import com.mob.crossing.MobCrossing;
import com.mob.crossing.item.FossilItem;
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

    public void spawnFossil() {
        if (level == null || level.getServer() == null || level.isClientSide()) {
            return;
        }

        ItemStack fossil = new ItemStack(MobCrossing.FOSSIL.get());
        double d0 = EntityType.ITEM.getWidth();
        double d1 = 1.0 - d0;
        double d2 = d0 / 2.0;
        Direction direction = Direction.UP;
        BlockPos blockpos = worldPosition.relative(direction, 1);
        double d3 = blockpos.getX() + 0.5 * d1 + d2;
        double d4 = blockpos.getY() + 0.5 + EntityType.ITEM.getHeight() / 2.0;
        double d5 = blockpos.getZ() + 0.5 * d1 + d2;
        ItemEntity entity = new ItemEntity(level, d3, d4, d5, fossil);
        entity.setDeltaMovement(Vec3.ZERO);
        level.addFreshEntity(entity);
    }
}
