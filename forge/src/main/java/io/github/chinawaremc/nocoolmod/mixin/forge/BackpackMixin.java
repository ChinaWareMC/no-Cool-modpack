package io.github.chinawaremc.nocoolmod.mixin.forge;

import com.mrcrayfish.backpacked.item.BackpackItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BackpackItem.class)
@Debug(export = true)
public abstract class BackpackMixin extends Item {

    public BackpackMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            if (tag.contains("nocoolmod_key")) {
                return Component.translatable(tag.getString("nocoolmod_key"));
            }
        }
        return super.getName(stack);
    }
}
