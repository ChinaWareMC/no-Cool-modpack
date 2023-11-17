package io.github.chinawaremc.nocoolmod.enums;

import net.minecraft.core.BlockPos;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Random;
@Deprecated
public enum RightClientRandom {
    ;
    private final double percentage;
    private final Item item;
    private final boolean isShift;

    RightClientRandom(double percentage, Item item, boolean isShift) {
        this.percentage = percentage;
        this.item = item;
        this.isShift = isShift;
    }

    public static void init() {

    }

    private static void rightClientGetOutput(double percentage, ServerPlayer player, BlockPos pos, Item item, boolean isAnd) {
        if (canPlaceBlock(player)) {
            return;
        }
        BlockPos tPos = randomGetPos(getRandom(percentage, isAnd), pos);
        if (tPos != null) {
            if (isDouble()) {
                dropItem(player, item, tPos);// If you have it yourself, then you may be doubly happy.
            }
            dropItem(player, item, tPos);
        }
    }

    public static boolean getRandom(double percentage, boolean isAnd) {
        double v = new Random(256).nextDouble();
        return isAnd ? v <= percentage : v < percentage;
    }

    public static boolean isDouble() {
        return new Random(1024).nextBoolean();
    }

    public static boolean or(Block block, Block... eqBlocks) {
        boolean b = false;
        for (Block eqBlock : eqBlocks) b |= block.equals(eqBlock);
        return b;
    }

    public static BlockPos randomGetPos(boolean on, BlockPos pos) {
        int i = new Random().nextInt(6);
        return on ? switch (i) {
            case 0 -> pos.above();
            case 1-> pos.below();
            case 2-> pos.east();
            case 3-> pos.west();
            case 4-> pos.north();
            case 5-> pos.south();
            default-> null;
        } : null;
    }

    private static void dropItem(ServerPlayer player, Item item, BlockPos tPos) {
        ItemEntity itemEntity = new ItemEntity(player.getLevel(), tPos.getX(), tPos.getY(), tPos.getZ(), new ItemStack(item));
        itemEntity.setDefaultPickUpDelay();
        player.level.addFreshEntity(itemEntity);
        PlayerAdvancements advancements = player.getAdvancements();
    }

    private static boolean canPlaceBlock(ServerPlayer player) {
        return player.getMainHandItem().getItem() instanceof BlockItem || player.getOffhandItem().getItem() instanceof BlockItem;
    }



}
