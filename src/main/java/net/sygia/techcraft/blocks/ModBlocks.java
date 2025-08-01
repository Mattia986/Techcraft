package net.sygia.techcraft.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sygia.techcraft.Techcraft;
import net.sygia.techcraft.blocks.custom.WindturbineBlock;
import net.sygia.techcraft.items.ModCreativeTabs;
import net.sygia.techcraft.items.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Techcraft.MOD_ID);
    public static final RegistryObject<Block> TESTBLOCK = registerBlock("testblock", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)), ModCreativeTabs.Techcraft);
    public static final RegistryObject<WindturbineBlock> WINDTURBINE = registerBlock("windturbine", () -> new WindturbineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()), ModCreativeTabs.Techcraft);

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> block, CreativeModeTab tab) {
        RegistryObject<B> bReturn = BLOCKS.register(name, block);
        registerBlockItem(name, bReturn, tab);
        return bReturn; // And bReturn just means blockReturn :>
    }

    private static <B extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<B> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
