package io.github.chinawaremc.nocoolmod.enums;

import io.github.chinawaremc.nocoolmod.forge.NocoolmodForge;
import net.minecraft.core.BlockPos;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public enum RightClientRandom {
    rightDirt(0.6, NocoolmodForge.quarterDirt.get(), true, true,
            Blocks.DIRT, Blocks.DIRT_PATH, Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT),
    ;
    private final double percentage;
    private final Item item;
    private final boolean isShift, isAnd;
    private final Block[] checkBlocks;

    RightClientRandom(double percentage, Item item, boolean isShift, boolean isAnd, Block... items) {
        this.percentage = percentage;
        this.item = item;
        this.isShift = isShift;
        checkBlocks = items;
        this.isAnd = isAnd;
    }

    public static void init(ServerPlayer player, BlockPos pos, Level level) {
        boolean sneak = player.isShiftKeyDown();
        Block block = level.getBlockState(pos).getBlock();
        RightClientRandom random = null;
        for (RightClientRandom value : values()) {
            if (or(block, value.checkBlocks)) {
                random = value;
                break;
            }
        }
        if (random != null) {
            if ((random.isShift && !sneak) || (!random.isShift && sneak)) {
                return;
            }
            rightClientGetOutput(random.percentage, player, pos, random.item, random.isAnd);
        }

    }

    public static Random random(long seed) {
        return new Random(System.nanoTime() ^ seed);
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
        double v = random(256).nextDouble();
        return isAnd ? v <= percentage : v < percentage;
    }

    public static boolean isDouble() {
        return random(1024).nextBoolean();
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
