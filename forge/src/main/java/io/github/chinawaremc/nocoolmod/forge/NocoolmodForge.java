package io.github.chinawaremc.nocoolmod.forge;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

@Mod(NocoolmodForge.modid)
public class NocoolmodForge {
    public static final String modid = "nocoolmod";
    public static final DeferredRegister<Block> blocks;
    public static final DeferredRegister<Item> items;
    public static final RegistryObject<Block> bedrock_compressx1;
    public static final RegistryObject<BlockItem> bedrock_compressx1_item;

    public static final RegistryObject<Item> quarterDirt;

    static {
        blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        items = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        quarterDirt = items.register("quarter_dirt", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        bedrock_compressx1 = blocks.register("bedrock_compressx1", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx1_item = items.register(bedrock_compressx1.getId().getPath(), () -> new BlockItem(bedrock_compressx1.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    }
    public NocoolmodForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        blocks.register(modEventBus);
        items.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void rightClientBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        Level level = event.getLevel();
        if (player.isShiftKeyDown()) {
            player.sendSystemMessage(Component.literal("test"));
            Block block = level.getBlockState(pos).getBlock();
            if (block.equals(Blocks.GRASS_BLOCK) || block.equals(Blocks.DIRT)) {
                int i = new Random().nextInt(10);
                BlockPos tPos = switch (i) {
                    case 0 -> pos.above();
                    case 1 -> pos.below();
                    case 2 -> pos.east();
                    case 3 -> pos.west();
                    case 4 -> pos.north();
                    case 5 -> pos.south();
                    default -> null;
                };
                if (tPos != null) {
                    ItemEntity itemEntity = new ItemEntity(player.getLevel(), tPos.getX(), tPos.getY(), tPos.getZ(), new ItemStack(quarterDirt.get()));
                    itemEntity.setDefaultPickUpDelay();
                    player.level.addFreshEntity(itemEntity);
                }
            }
        }

    }



}