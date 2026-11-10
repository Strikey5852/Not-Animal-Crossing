package com.mob.crossing;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import com.mob.crossing.block.PetrifiedDirtBlock;
import com.mob.crossing.block.PetrifiedDirtBlockEntity;
import com.mob.crossing.block.WeedBlock;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

    public static final DeferredBlock<Block> WEED = BLOCKS.register("weed", WeedBlock::new);
    public static final DeferredItem<BlockItem> WEED_ITEM = ITEMS.registerSimpleBlockItem("weed", WEED);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> PETRIFIED_DIRT_BLOCK_ENTITY = BLOCK_ENTITIES.register("petrified_dirt", ()
            -> net.minecraft.world.level.block.entity.BlockEntityType.Builder.of(PetrifiedDirtBlockEntity::new, PETRIFIED_DIRT.get())
            .build(null));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
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
