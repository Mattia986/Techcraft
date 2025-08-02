package net.sygia.techcraft.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sygia.techcraft.block_entities.ModBlockEntities;
import net.sygia.techcraft.block_entities.WindturbineBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class WindturbineBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<TurbinePart> PART = EnumProperty.create("part", TurbinePart.class);
    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Optional<VoxelShape> SHAPE = Stream.of(
            Block.box(7, 45, 5, 9, 47, 7),
            Block.box(5, 0, 5, 11, 3, 11),
            Block.box(6, 2, 6, 10, 5, 10),
            Block.box(6, 44, 6, 10, 48, 10),
            Block.box(7, 0, 7, 9, 48, 9.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

    public WindturbineBlock(Properties properties) {
        super(properties.noOcclusion());
        runCalculation(SHAPE.orElse(Shapes.block()));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction dir = state.getValue(FACING);
        VoxelShape baseShape = SHAPES.get(dir);
        TurbinePart part = state.getValue(PART);
        return switch (part) {
            case LOWER -> baseShape;
            case MIDDLE -> offsetShape(baseShape, 0, -1, 0);
            case UPPER -> offsetShape(baseShape, 0, -2, 0);
        };
    }

    private void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values())
            SHAPES.put(direction, calculateShapes(direction, shape));
    }

    public static VoxelShape calculateShapes(Direction to, VoxelShape shape) {
        final VoxelShape[] buffer = { shape, Shapes.empty() };
        final int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
                    Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }
        return buffer[0];
    }

    private static VoxelShape offsetShape(VoxelShape shape, double x, double y, double z) {
        VoxelShape[] shifted = { Shapes.empty() };
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            shifted[0] = Shapes.or(shifted[0], Shapes.box(
                    minX + x, minY + y, minZ + z,
                    maxX + x, maxY + y, maxZ + z
            ));
        });
        return shifted[0];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        if (!level.getBlockState(pos.above()).canBeReplaced(ctx) ||
                !level.getBlockState(pos.above(2)).canBeReplaced(ctx)) {
            return null;
        }
        return defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(PART, TurbinePart.LOWER);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(PART) == TurbinePart.LOWER) {
            Direction facing = state.getValue(FACING);
            level.setBlock(pos.above(), state.setValue(PART, TurbinePart.MIDDLE), 3);
            level.setBlock(pos.above(2), state.setValue(PART, TurbinePart.UPPER), 3);
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TurbinePart part = state.getValue(PART);
            BlockPos basePos = switch (part) {
                case LOWER -> pos;
                case MIDDLE -> pos.below();
                case UPPER -> pos.below(2);
            };
            for (int i = 0; i < 3; i++) {
                BlockPos removePos = basePos.above(i);
                if (level.getBlockState(removePos).getBlock() == this)
                    level.removeBlock(removePos, false);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == TurbinePart.LOWER ?
                ModBlockEntities.WINDTURBINE_BLOCK_ENTITY.get().create(pos, state) : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.tick(state, level, pos, random);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : ($0, $1, $2, blockEntity) -> ((WindturbineBlockEntity) blockEntity).tick();
    }
}
