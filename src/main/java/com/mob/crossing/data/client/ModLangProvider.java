package com.mob.crossing.data.client;

import com.mob.crossing.MobCrossing;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ModLangProvider extends LanguageProvider {
    private static final Set<String> GENERATED_KEYS = new HashSet<>();

    public ModLangProvider(PackOutput output) {
        super(output, MobCrossing.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addBlockIfAbsent(MobCrossing.PETRIFIED_DIRT.get(), "Petrified Dirt");
        addBlockIfAbsent(MobCrossing.WEED.get(), "Weed");
        addBlockIfAbsent(MobCrossing.BLUE_ROSE.get(), "Blue Rose");
        addBlockIfAbsent(MobCrossing.LILIE.get(), "Lilie");
        addBlockIfAbsent(MobCrossing.PINK_WINDFLOWER.get(), "Pink Windflower");
        addBlockIfAbsent(MobCrossing.PURPLE_HYACINTH.get(), "Purple Hyacinth");
        addBlockIfAbsent(MobCrossing.RED_COSMOS.get(), "Red Cosmos");
        addBlockIfAbsent(MobCrossing.WHITE_MUM.get(), "White Mum");
        addBlockIfAbsent(MobCrossing.YELLOW_PANSIE.get(), "Yellow Pansie");
        addBlockIfAbsent(MobCrossing.YELLOW_TULIP.get(), "Yellow Tulip");
        addItemIfAbsent(MobCrossing.FOSSIL.get(), "Fossil");
        addItemIfAbsent(MobCrossing.BELL.get(), "Bell");
        addItemIfAbsent(MobCrossing.BELL_BAG.get(), "Small Bell Bag");
        addItemIfAbsent(MobCrossing.MEDIUM_BELL_BAG.get(), "Medium Bell Bag");
        addItemIfAbsent(MobCrossing.LARGE_BELL_BAG.get(), "Large Bell Bag");
        addItemIfAbsent(MobCrossing.WEED_ITEM.get(), "Weed");
        addItemIfAbsent(MobCrossing.BLUE_ROSE_ITEM.get(), "Blue Rose");
        addItemIfAbsent(MobCrossing.LILIE_ITEM.get(), "Lilie");
        addItemIfAbsent(MobCrossing.PINK_WINDFLOWER_ITEM.get(), "Pink Windflower");
        addItemIfAbsent(MobCrossing.PURPLE_HYACINTH_ITEM.get(), "Purple Hyacinth");
        addItemIfAbsent(MobCrossing.RED_COSMOS_ITEM.get(), "Red Cosmos");
        addItemIfAbsent(MobCrossing.WHITE_MUM_ITEM.get(), "White Mum");
        addItemIfAbsent(MobCrossing.YELLOW_PANSIE_ITEM.get(), "Yellow Pansie");
        addItemIfAbsent(MobCrossing.YELLOW_TULIP_ITEM.get(), "Yellow Tulip");
        addItemIfAbsent(MobCrossing.CHERRY.get(), "Cherry");
        addItemIfAbsent(MobCrossing.COCONUT.get(), "Coconut");
        addItemIfAbsent(MobCrossing.ORANGE.get(), "Orange");
        addItemIfAbsent(MobCrossing.PEACH.get(), "Peach");
        addItemIfAbsent(MobCrossing.PEAR.get(), "Pear");

        addIfAbsent("itemGroup.mobcrossing", "Mob Crossing");
    }

    private void addBlockIfAbsent(net.minecraft.world.level.block.Block block, String translation) {
        String key = "block." + MobCrossing.MODID + "." + getRegistryName(block);
        addIfAbsent(key, translation);
    }

    private void addItemIfAbsent(net.minecraft.world.item.Item item, String translation) {
        String key = "item." + MobCrossing.MODID + "." + getRegistryName(item);
        addIfAbsent(key, translation);
    }

    private String getRegistryName(net.minecraft.world.level.block.Block block) {
        Optional<net.minecraft.resources.ResourceKey<net.minecraft.world.level.block.Block>> key = block.builtInRegistryHolder().unwrapKey();
        return key.map(rk -> rk.location().getPath()).orElse("unknown");
    }

    private String getRegistryName(net.minecraft.world.item.Item item) {
        Optional<net.minecraft.resources.ResourceKey<net.minecraft.world.item.Item>> key = item.builtInRegistryHolder().unwrapKey();
        return key.map(rk -> rk.location().getPath()).orElse("unknown");
    }

    private void addIfAbsent(String key, String translation) {
        if (GENERATED_KEYS.add(key)) {
            add(key, translation);
        }
    }
}
