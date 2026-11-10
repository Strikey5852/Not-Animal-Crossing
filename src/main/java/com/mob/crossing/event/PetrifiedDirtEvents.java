package com.mob.crossing.event;

import com.mob.crossing.MobCrossing;
import com.mob.crossing.block.PetrifiedDirtBlock;
import com.mob.crossing.block.PetrifiedDirtBlockEntity;
import com.mob.crossing.block.PetrifiedDirtStage;
import net.minecraft.core.BlockPos;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = MobCrossing.MODID)
public class PetrifiedDirtEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof PetrifiedDirtBlock)) {
            return;
        }

        if (state.getValue(PetrifiedDirtBlock.STAGE) != PetrifiedDirtStage.GROWN) {
            return;
        }

        if (!(stack.getItem() instanceof BrushItem)) {
            return;
        }

        // Remove the initial brush count increment from right-click to avoid double counting
        // Just set the visual state and start animation; ticker handles the counting
        level.setBlock(pos, state.setValue(BlockStateProperties.DUSTED, 1), 3);
        level.playSound(null, pos, SoundEvents.BRUSH_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
        stack.hurtAndBreak(1, player, InteractionHand.MAIN_HAND.equals(event.getHand()) ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    @SubscribeEvent
    public static void onUseItemTick(LivingEntityUseItemEvent.Tick event) {
        LivingEntity entity = event.getEntity();
        ItemStack stack = event.getItem();
        if (!(stack.getItem() instanceof BrushItem)) {
            return;
        }
        if (!(entity instanceof Player player)) {
            return;
        }

        Level level = player.level();
        long currentTick = level.getGameTime();

        // Process every 10 ticks (matching vanilla brush rhythm)
        if (currentTick % 10 != 0) {
            return;
        }

        // Raycast to find the block the player is looking at
        double reach = player.blockInteractionRange();
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getViewVector(1.0F);
        Vec3 end = start.add(look.x * reach, look.y * reach, look.z * reach);
        HitResult hitResult = level.clip(new ClipContext(
                start, end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player));

        if (!(hitResult instanceof BlockHitResult blockHitResult)) {
            return;
        }
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof PetrifiedDirtBlock)) {
            return;
        }

        if (state.getValue(PetrifiedDirtBlock.STAGE) != PetrifiedDirtStage.GROWN) {
            return;
        }

        if (state.getValue(BlockStateProperties.DUSTED) < 1) {
            return;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PetrifiedDirtBlockEntity dirtEntity)) {
            return;
        }

        if (!dirtEntity.canBrush(currentTick)) {
            return;
        }

        dirtEntity.startBrushing(currentTick);

        int dusted = dirtEntity.getBrushCount() < 3 ? 1 : 2;
        if (state.getValue(BlockStateProperties.DUSTED) != dusted) {
            level.setBlock(pos, state.setValue(BlockStateProperties.DUSTED, dusted), 3);
        }

        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

        if (dirtEntity.getBrushCount() >= PetrifiedDirtBlockEntity.BRUSHES_REQUIRED) {
            int roll = ThreadLocalRandom.current().nextInt(96);
            ItemStack drop = null;
            if (roll < 50) {
                drop = new ItemStack(MobCrossing.FOSSIL.get());
            } else if (roll < 75) {
                drop = new ItemStack(MobCrossing.BELL.get());
            } else if (roll < 87) {
                drop = new ItemStack(MobCrossing.BELL_BAG.get());
            } else if (roll < 93) {
                drop = new ItemStack(MobCrossing.MEDIUM_BELL_BAG.get());
            } else {
                drop = new ItemStack(MobCrossing.LARGE_BELL_BAG.get());
            }
            if (drop != null) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, drop));
            }
            level.setBlock(pos, state.setValue(PetrifiedDirtBlock.STAGE, PetrifiedDirtStage.HOLE)
                    .setValue(BlockStateProperties.DUSTED, 0), 3);
            level.levelEvent(3008, pos, net.minecraft.world.level.block.Block.getId(state));
        }
    }
}
