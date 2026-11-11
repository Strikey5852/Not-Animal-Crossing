package com.mob.crossing;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.mob.crossing.block.PetrifiedDirtBlock;
import com.mob.crossing.block.PetrifiedDirtBlockEntity;
import com.mob.crossing.block.WeedBlock;

import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.effect.MobEffects;

import com.mob.crossing.data.client.ModItemModelProvider;
import com.mob.crossing.data.client.ModLangProvider;
import com.mob.crossing.data.ModBlockLootProvider;
import com.mob.crossing.data.ModBlockTagsProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(MobCrossing.MODID)
public class MobCrossing {
    public static final String MODID = "mobcrossing";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<PetrifiedDirtBlock> PETRIFIED_DIRT = BLOCKS.register("petrified_dirt", PetrifiedDirtBlock::new);
    public static final DeferredItem<BlockItem> PETRIFIED_DIRT_ITEM = ITEMS.registerSimpleBlockItem("petrified_dirt", PETRIFIED_DIRT);
    public static final DeferredItem<Item> FOSSIL = ITEMS.register("fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BELL = ITEMS.register("bell", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BELL_BAG = ITEMS.register("small_bell_bag", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MEDIUM_BELL_BAG = ITEMS.register("medium_bell_bag", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LARGE_BELL_BAG = ITEMS.register("large_bell_bag", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CHERRY = ITEMS.register("cherry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.4F).build())));
    public static final DeferredItem<Item> COCONUT = ITEMS.register("coconut", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.6F).build())));
    public static final DeferredItem<Item> ORANGE = ITEMS.register("orange", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5F).build())));
    public static final DeferredItem<Item> PEACH = ITEMS.register("peach", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5F).build())));
    public static final DeferredItem<Item> PEAR = ITEMS.register("pear", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.5F).build())));

    public static final DeferredBlock<Block> WEED = BLOCKS.register("weed", WeedBlock::new);
    public static final DeferredItem<BlockItem> WEED_ITEM = ITEMS.registerSimpleBlockItem("weed", WEED);

    public static final DeferredBlock<Block> BLUE_ROSE = BLOCKS.register("blue_rose", () -> new FlowerBlock(MobEffects.NIGHT_VISION, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> BLUE_ROSE_ITEM = ITEMS.registerSimpleBlockItem("blue_rose", BLUE_ROSE);
    public static final DeferredBlock<Block> LILIE = BLOCKS.register("lilie", () -> new FlowerBlock(MobEffects.ABSORPTION, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> LILIE_ITEM = ITEMS.registerSimpleBlockItem("lilie", LILIE);
    public static final DeferredBlock<Block> PINK_WINDFLOWER = BLOCKS.register("pink_windflower", () -> new FlowerBlock(MobEffects.JUMP, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> PINK_WINDFLOWER_ITEM = ITEMS.registerSimpleBlockItem("pink_windflower", PINK_WINDFLOWER);
    public static final DeferredBlock<Block> PURPLE_HYACINTH = BLOCKS.register("purple_hyacinth", () -> new FlowerBlock(MobEffects.SLOW_FALLING, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> PURPLE_HYACINTH_ITEM = ITEMS.registerSimpleBlockItem("purple_hyacinth", PURPLE_HYACINTH);
    public static final DeferredBlock<Block> RED_COSMOS = BLOCKS.register("red_cosmos", () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> RED_COSMOS_ITEM = ITEMS.registerSimpleBlockItem("red_cosmos", RED_COSMOS);
    public static final DeferredBlock<Block> WHITE_MUM = BLOCKS.register("white_mum", () -> new FlowerBlock(MobEffects.HEALTH_BOOST, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> WHITE_MUM_ITEM = ITEMS.registerSimpleBlockItem("white_mum", WHITE_MUM);
    public static final DeferredBlock<Block> YELLOW_PANSIE = BLOCKS.register("yellow_pansie", () -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> YELLOW_PANSIE_ITEM = ITEMS.registerSimpleBlockItem("yellow_pansie", YELLOW_PANSIE);
    public static final DeferredBlock<Block> YELLOW_TULIP = BLOCKS.register("yellow_tulip", () -> new FlowerBlock(MobEffects.REGENERATION, 0.4F, BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .sound(SoundType.GRASS)
            .noCollission()));
    public static final DeferredItem<BlockItem> YELLOW_TULIP_ITEM = ITEMS.registerSimpleBlockItem("yellow_tulip", YELLOW_TULIP);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> PETRIFIED_DIRT_BLOCK_ENTITY = BLOCK_ENTITIES.register("petrified_dirt", ()
            -> net.minecraft.world.level.block.entity.BlockEntityType.Builder.of(PetrifiedDirtBlockEntity::new, PETRIFIED_DIRT.get())
            .build(null));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("mobcrossing_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mobcrossing"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> WEED_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(PETRIFIED_DIRT_ITEM.get());
                output.accept(FOSSIL.get());
                output.accept(BELL.get());
                output.accept(BELL_BAG.get());
                output.accept(MEDIUM_BELL_BAG.get());
                output.accept(LARGE_BELL_BAG.get());
                output.accept(WEED_ITEM.get());
                output.accept(BLUE_ROSE_ITEM.get());
                output.accept(LILIE_ITEM.get());
                output.accept(PINK_WINDFLOWER_ITEM.get());
                output.accept(PURPLE_HYACINTH_ITEM.get());
                output.accept(RED_COSMOS_ITEM.get());
                output.accept(WHITE_MUM_ITEM.get());
                output.accept(YELLOW_PANSIE_ITEM.get());
                output.accept(YELLOW_TULIP_ITEM.get());
                output.accept(CHERRY.get());
                output.accept(COCONUT.get());
                output.accept(ORANGE.get());
                output.accept(PEACH.get());
                output.accept(PEAR.get());
                output.accept(Items.APPLE);
            }).build());

    public MobCrossing(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        // Data providers
        modEventBus.addListener(ModBlockLootProvider::onGatherData);
        modEventBus.addListener((GatherDataEvent event) -> event.getGenerator().addProvider(true,
            new ModBlockTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper())));
        modEventBus.addListener((GatherDataEvent event) -> event.getGenerator().addProvider(true,
            new ModItemModelProvider(event.getGenerator().getPackOutput(), event.getExistingFileHelper())));
        modEventBus.addListener((GatherDataEvent event) -> event.getGenerator().addProvider(true,
            new ModLangProvider(event.getGenerator().getPackOutput())));

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting");
    }
}
