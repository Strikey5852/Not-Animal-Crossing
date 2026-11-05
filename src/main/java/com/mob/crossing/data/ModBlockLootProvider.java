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
                    }

                    @Override
                    protected Iterable<Block> getKnownBlocks() {
                        return Set.of(MobCrossing.WEED.get());
                    }
                },
                LootContextParamSets.BLOCK
            )),
            event.getLookupProvider()
        ));
    }
}
