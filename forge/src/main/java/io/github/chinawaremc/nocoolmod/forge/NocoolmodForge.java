package io.github.chinawaremc.nocoolmod.forge;


import com.mrcrayfish.backpacked.Backpacked;
import com.mrcrayfish.backpacked.core.ModItems;
import io.github.chinawaremc.nocoolmod.enums.RightClientRandom;
import io.github.chinawaremc.nocoolmod.group.ModGroups;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
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
    public static final RegistryObject<Item> dirtStick;

    public static final RegistryObject<Item> quarterDirt;

    public static ResourceLocation id(String name) {
        return new ResourceLocation(modid, name);
    }
    //玩梗计划 -> 42号混凝土 与意大利面， 沃尔玛购物袋
    //沃尔玛购物袋 被攻击时，攻击者会弹出一条消息，这个消息有三个选项，男，女，沃尔玛购物袋，你怎么能定义我的性别。在说出正确选项前你不可被攻击

    static {

        blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        items = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        new ModGroups(id("basic"));
        new ModGroups(id("finale"));
        ModGroups basic = ModGroups.groups.get("basic");
        ModGroups finale = ModGroups.groups.get("finale");
        dirtStick = items.register("dirt_stick", () -> new Item(new Item.Properties().tab(basic)));
        quarterDirt = items.register("quarter_dirt", () -> new Item(new Item.Properties().tab(basic)));
        bedrock_compressx1 = blocks.register("bedrock_compressx1", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx2 = blocks.register("bedrock_compressx2", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx3 = blocks.register("bedrock_compressx3", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx4 = blocks.register("bedrock_compressx4", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noLootTable().isValidSpawn((arg, arg2, arg3, object) -> false)));
        bedrock_compressx1_item = items.register(bedrock_compressx1.getId().getPath(), () -> new BlockItem(bedrock_compressx1.get(), new Item.Properties().tab(finale)));
        bedrock_compressx2_item = items.register(bedrock_compressx2.getId().getPath(), () -> new BlockItem(bedrock_compressx2.get(), new Item.Properties().tab(finale)));
        bedrock_compressx3_item = items.register(bedrock_compressx3.getId().getPath(), () -> new BlockItem(bedrock_compressx3.get(), new Item.Properties().tab(finale)));
        bedrock_compressx4_item = items.register(bedrock_compressx4.getId().getPath(), () -> new BlockItem(bedrock_compressx4.get(), new Item.Properties().tab(finale)));
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
        if (level.isClientSide) {
            return;
        }
        RightClientRandom.init((ServerPlayer) player, pos, level);
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