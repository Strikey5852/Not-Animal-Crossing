package com.mob.crossing.data.client;

import com.mob.crossing.MobCrossing;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MobCrossing.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // fossil item model (uses block texture)
        basicItem(MobCrossing.FOSSIL.get());

        // petrified_dirt item model (simple block item)
        simpleBlockItem(MobCrossing.PETRIFIED_DIRT.get());

        // weed item model
        basicItem(MobCrossing.WEED_ITEM.get());

        // bell item model
        basicItem(MobCrossing.BELL.get());

        // bell bag item models
        basicItem(MobCrossing.BELL_BAG.get());
        basicItem(MobCrossing.MEDIUM_BELL_BAG.get());
        basicItem(MobCrossing.LARGE_BELL_BAG.get());

        // fruit item models
        basicItem(MobCrossing.CHERRY.get());
        basicItem(MobCrossing.COCONUT.get());
        basicItem(MobCrossing.ORANGE.get());
        basicItem(MobCrossing.PEACH.get());
        basicItem(MobCrossing.PEAR.get());
    }
}
