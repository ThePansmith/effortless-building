package nl.requios.effortlessbuilding.utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

// Version of DirectionalPlaceContext with hitVec
public class MyPlaceContext extends BlockPlaceContext {
    private final Direction direction;

    public MyPlaceContext(Level level, BlockPos blockPos, Direction direction, ItemStack itemStack, Direction clickedFace, Vec3 relativeHitVec) {

        super(level, null, InteractionHand.MAIN_HAND, itemStack, new BlockHitResult(
                Vec3.atLowerCornerOf(blockPos).add(relativeHitVec), clickedFace, blockPos, false));
        this.direction = direction;
    }

    public BlockPos getClickedPos() {
        return this.getHitResult().getBlockPos();
    }

    public Direction getNearestLookingDirection() {
        return Direction.DOWN;
    }

    public Direction[] getNearestLookingDirections() {
        switch (this.direction) {
            case DOWN:
            default:
                return new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};
            case UP:
                return new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            case NORTH:
                return new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.SOUTH};
            case SOUTH:
                return new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.NORTH};
            case WEST:
                return new Direction[]{Direction.DOWN, Direction.WEST, Direction.SOUTH, Direction.UP, Direction.NORTH, Direction.EAST};
            case EAST:
                return new Direction[]{Direction.DOWN, Direction.EAST, Direction.SOUTH, Direction.UP, Direction.NORTH, Direction.WEST};
        }
    }

    public Direction getHorizontalDirection() {
        return this.direction.getAxis() == Direction.Axis.Y ? Direction.NORTH : this.direction;
    }

    public boolean isSecondaryUseActive() {
        return false;
    }

    public float getRotation() {
        return (float)(this.direction.get2DDataValue() * 90);
    }
}
