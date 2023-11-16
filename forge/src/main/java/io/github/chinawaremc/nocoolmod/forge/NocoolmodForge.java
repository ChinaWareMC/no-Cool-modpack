package io.github.chinawaremc.nocoolmod.forge;


import com.mrcrayfish.backpacked.Backpacked;
import com.mrcrayfish.backpacked.core.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@Mod(NocoolmodForge.modid)
public class NocoolmodForge {
    public static final String modid = "nocoolmod";
    public static final DeferredRegister<Block> blocks;
    public static final DeferredRegister<Item> items;
    public static final RegistryObject<Block> bedrock_compressx1;
    public static final RegistryObject<Block> bedrock_compressx2;
    public static final RegistryObject<Block> bedrock_compressx3;
    public static final RegistryObject<Block> bedrock_compressx4;
    public static final RegistryObject<BlockItem> bedrock_compressx1_item;
    public static final RegistryObject<BlockItem> bedrock_compressx2_item;
    public static final RegistryObject<BlockItem> bedrock_compressx3_item;
    public static final RegistryObject<BlockItem> bedrock_compressx4_item;

    public static final RegistryObject<Item> quarterDirt;
    //玩梗计划 -> 42号混凝土 与意大利面
    //沃尔玛购物袋

    static {

        blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        items = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        quarterDirt = items.register("quarter_dirt", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        bedrock_compressx1 = blocks.register("bedrock_compressx1", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx2 = blocks.register("bedrock_compressx2", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx3 = blocks.register("bedrock_compressx3", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx4 = blocks.register("bedrock_compressx4", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx1_item = items.register(bedrock_compressx1.getId().getPath(), () -> new BlockItem(bedrock_compressx1.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        bedrock_compressx2_item = items.register(bedrock_compressx2.getId().getPath(), () -> new BlockItem(bedrock_compressx2.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        bedrock_compressx3_item = items.register(bedrock_compressx3.getId().getPath(), () -> new BlockItem(bedrock_compressx3.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        bedrock_compressx4_item = items.register(bedrock_compressx4.getId().getPath(), () -> new BlockItem(bedrock_compressx4.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    }

    public NocoolmodForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        blocks.register(bus);
        items.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean or(Block block, Block... eqBlocks) {
        boolean b = false;
        for (Block eqBlock : eqBlocks) b |= block.equals(eqBlock);
        return b;
    }



    @SubscribeEvent
    public void rightClientBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        if (player.level.isClientSide) {
            return;
        }
        if (player.isShiftKeyDown()) {
            Block block = level.getBlockState(pos).getBlock();
            if (
                    or(block,
                            Blocks.GRASS_BLOCK,
                            Blocks.DIRT,
                            Blocks.COARSE_DIRT,
                            Blocks.DIRT_PATH,
                            Blocks.ROOTED_DIRT
                    )
            ) {
                rightClientGetOutput(100, (ServerPlayer) player, pos, quarterDirt.get(), 100);// 60% get quarter Dirt
            }
        }
    }

    private static boolean canPlaceBlock(ServerPlayer player) {
        return player.getMainHandItem().getItem() instanceof BlockItem || player.getOffhandItem().getItem() instanceof BlockItem;
    }

    @SuppressWarnings("SameParameterValue")
    private static void rightClientGetOutput(int bound, ServerPlayer player, BlockPos pos, Item item, int mod) {
        if (canPlaceBlock(player)) {
            return;
        }
        BlockPos tPos = randomGenPos(bound, pos, mod);
        if (tPos != null) {

            if (player.getMainHandItem().is(item) && new Random().nextBoolean()) {
                dropItem(player, item, tPos);// If you have it yourself, then you may be doubly happy.
            }
            dropItem(player, item, tPos);
        }
    }

    private static void dropItem(ServerPlayer player, Item item, BlockPos tPos) {
        ItemEntity itemEntity = new ItemEntity(player.getLevel(), tPos.getX(), tPos.getY(), tPos.getZ(), new ItemStack(item));
        itemEntity.setDefaultPickUpDelay();
        player.level.addFreshEntity(itemEntity);
        PlayerAdvancements advancements = player.getAdvancements();
    }

    @Nullable
    private static BlockPos randomGenPos(int bound, BlockPos pos, int mod) {
        int i = new Random().nextInt(bound);
        return switch (i % mod) {
            case 0, 10, 20, 30, 40, 50, 60, 70, 80, 90 -> pos.above();
            case 1, 11, 21, 31, 41, 51, 61, 71, 81, 91 -> pos.below();
            case 2, 12, 22, 32, 42, 52, 62, 72, 82, 92 -> pos.east();
            case 3, 13, 23, 33, 43, 53, 63, 73, 83, 93 -> pos.west();
            case 4, 14, 24, 34, 44, 54, 64, 74, 84, 94 -> pos.north();
            case 5, 15, 25, 35, 45, 55, 65, 75, 85, 95 -> pos.south();
            default -> null;
        };
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = NocoolmodForge.modid)
    public static class ClientForge {
        public ClientForge() {
            MinecraftForge.EVENT_BUS.register(this);
        }
        @SubscribeEvent
        public void creativeMobTabRegistry(FMLClientSetupEvent event) {
            NonNullList<ItemStack> items1 = NonNullList.create();
            ItemStack defaultInstance = ModItems.BACKPACK.get().getDefaultInstance();
            CompoundTag tag = new CompoundTag();
            tag.putString("nocoolmod_key", "dirt_backpack");
            defaultInstance.setTag(tag);
            items1.add(defaultInstance);
            Backpacked.TAB.fillItemList(items1);
        }
    }

}