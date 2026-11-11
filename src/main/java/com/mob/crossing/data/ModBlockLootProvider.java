package com.mob.crossing.data;

import com.mob.crossing.MobCrossing;
import com.mob.crossing.block.WeedBlock;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class ModBlockLootProvider {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(true, new LootTableProvider(
            event.getGenerator().getPackOutput(),
            Set.of(),
            List.of(new LootTableProvider.SubProviderEntry(
                registries -> new BlockLootSubProvider(
                    Set.of(),
                    FeatureFlags.DEFAULT_FLAGS,
                    registries
                ) {
                    @Override
                    protected void generate() {
                        add(MobCrossing.WEED.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.WEED.get())
                                    .apply(
                                        List.of(0, 1, 2),
                                        (age) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) (age + 1)))
                                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(MobCrossing.WEED.get())
                                                .setProperties(
                                                    StatePropertiesPredicate.Builder.properties()
                                                        .hasProperty(WeedBlock.AGE, age)
                                                )
                                            )
                                    )
                                )
                            )
                        );

                        add(MobCrossing.BLUE_ROSE.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.BLUE_ROSE.get()))
                            )
                        );
                        add(MobCrossing.LILIE.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.LILIE.get()))
                            )
                        );
                        add(MobCrossing.PINK_WINDFLOWER.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.PINK_WINDFLOWER.get()))
                            )
                        );
                        add(MobCrossing.PURPLE_HYACINTH.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.PURPLE_HYACINTH.get()))
                            )
                        );
                        add(MobCrossing.RED_COSMOS.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.RED_COSMOS.get()))
                            )
                        );
                        add(MobCrossing.WHITE_MUM.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.WHITE_MUM.get()))
                            )
                        );
                        add(MobCrossing.YELLOW_PANSIE.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.YELLOW_PANSIE.get()))
                            )
                        );
                        add(MobCrossing.YELLOW_TULIP.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.YELLOW_TULIP.get()))
                            )
                        );

                        add(MobCrossing.PETRIFIED_DIRT.get(), LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MobCrossing.PETRIFIED_DIRT.get()))
                            )
                        );
                    }

                    @Override
                    protected Iterable<Block> getKnownBlocks() {
                        return Set.of(MobCrossing.WEED.get(), MobCrossing.BLUE_ROSE.get(), MobCrossing.LILIE.get(),
                            MobCrossing.PINK_WINDFLOWER.get(), MobCrossing.PURPLE_HYACINTH.get(), MobCrossing.RED_COSMOS.get(),
                            MobCrossing.WHITE_MUM.get(), MobCrossing.YELLOW_PANSIE.get(), MobCrossing.YELLOW_TULIP.get(),
                            MobCrossing.PETRIFIED_DIRT.get());
                    }
                },
                LootContextParamSets.BLOCK
            )),
            event.getLookupProvider()
        ));
    }
}
