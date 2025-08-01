package net.sygia.techcraft.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sygia.techcraft.Techcraft;
import net.sygia.techcraft.block_entities.ModBlockEntities;
import net.sygia.techcraft.block_entities.WindturbineBlockEntity;
import net.sygia.techcraft.client.renderer.block_entities.WindturbineBlockEntityRenderer;
import net.sygia.techcraft.models.ModelWindTurbine;

@Mod.EventBusSubscriber(modid = Techcraft.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.WINDTURBINE_BLOCK_ENTITY.get(), WindturbineBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelWindTurbine.LAYER_LOCATION, ModelWindTurbine::createBodyLayer);
    }
}