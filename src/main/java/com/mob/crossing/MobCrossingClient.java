package com.mob.crossing;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MobCrossing.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MobCrossing.MODID, value = Dist.CLIENT)
public class MobCrossingClient {
    public MobCrossingClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.WEED.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.BLUE_ROSE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.LILIE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.PINK_WINDFLOWER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.PURPLE_HYACINTH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.RED_COSMOS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.WHITE_MUM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.YELLOW_PANSIE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MobCrossing.YELLOW_TULIP.get(), RenderType.cutout());
        });
    }
}
