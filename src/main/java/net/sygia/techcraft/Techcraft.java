package net.sygia.techcraft;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sygia.techcraft.block_entities.ModBlockEntities;
import net.sygia.techcraft.blocks.ModBlocks;
import net.sygia.techcraft.items.ModItems;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Techcraft.MOD_ID)
public class Techcraft
{
    public static final String MOD_ID = "techcraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Techcraft()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);


        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(eventBus);

        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WINDTURBINE.get(), RenderType.translucent());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("SUCK MY COCK");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
