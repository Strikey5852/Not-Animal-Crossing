package com.mob.crossing.block;

import com.mob.crossing.MobCrossing;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class PetrifiedDirtBlock extends Block implements EntityBlock {
    public static final EnumProperty<PetrifiedDirtStage> STAGE = EnumProperty.create("stage", PetrifiedDirtStage.class);

    public PetrifiedDirtBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.DIRT)
                .strength(0.5F)
                .sound(SoundType.PACKED_MUD)
                .randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(STAGE, PetrifiedDirtStage.NORMAL));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE).add(BlockStateProperties.DUSTED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PetrifiedDirtBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == MobCrossing.PETRIFIED_DIRT_BLOCK_ENTITY.get() ? PetrifiedDirtBlock::tickServer : null;
    }

    public static <T extends BlockEntity> void tickServer(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof PetrifiedDirtBlockEntity dirtEntity) {
            dirtEntity.checkTimeout(level.getGameTime());
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        PetrifiedDirtStage stage = state.getValue(STAGE);
        if (stage == PetrifiedDirtStage.NORMAL) {
            if (random.nextInt(400) == 0) {
                level.setBlock(pos, state.setValue(STAGE, PetrifiedDirtStage.GROWING), 3);
            }
        } else if (stage == PetrifiedDirtStage.GROWING) {
            if (random.nextInt(400) == 0) {
                level.setBlock(pos, state.setValue(STAGE, PetrifiedDirtStage.GROWN), 3);
            }
        } else if (stage == PetrifiedDirtStage.HOLE) {
            if (random.nextInt(400) == 0) {
                level.setBlock(pos, state.setValue(STAGE, PetrifiedDirtStage.NORMAL), 3);
            }
        }
    }
}
