package net.sygia.techcraft.block_entities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sygia.techcraft.Techcraft;
import net.sygia.techcraft.blocks.ModBlocks;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Techcraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<WindturbineBlockEntity>> WINDTURBINE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("windturbine_block_entity", () -> BlockEntityType.Builder.of(WindturbineBlockEntity::new, ModBlocks.WINDTURBINE.get()).build(null));

    private ModBlockEntities() { }
}
