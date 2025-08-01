package net.sygia.techcraft.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WindturbineBlockEntity extends BlockEntity {

    private float angle = 0;

    public WindturbineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WINDTURBINE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
    }

    public void tick() {
        if (level != null && !level.isClientSide) return;
        angle += (float) (Math.pow(2, getBlockPos().getY()) *  Math.pow(-5, 10));

    }

    public float getSpinAngle() {
        return angle;
    }
}
