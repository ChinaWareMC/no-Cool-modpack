package io.github.chinawaremc.nocoolmod.group;

import io.github.chinawaremc.nocoolmod.forge.NocoolmodForge;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ModGroups extends CreativeModeTab {
    public static final Map<String, ModGroups> groups = new HashMap<>();

    private final String label;

    public ModGroups(ResourceLocation label) {
        super(label.toString());
        this.label = label.getPath();
        groups.put(label.getPath(), this);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return switch (label) {
            case "basic" -> new ItemStack(NocoolmodForge.quarterDirt.get());
            case "finale" -> new ItemStack(NocoolmodForge.bedrock_compressx1_item.get());
            default -> ItemStack.EMPTY;
        };
    }
}
