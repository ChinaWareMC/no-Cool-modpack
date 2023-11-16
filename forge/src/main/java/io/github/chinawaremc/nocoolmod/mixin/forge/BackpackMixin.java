package io.github.chinawaremc.nocoolmod.mixin.forge;

import com.mrcrayfish.backpacked.item.BackpackItem;
import io.github.chinawaremc.nocoolmod.mface.BackpackFace;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackpackItem.class)
@Debug(export = true)
public abstract class BackpackMixin extends Item implements BackpackFace {

    @Shadow(remap = false) public abstract int getColumnCount();

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



    @Redirect(method = "openBackpack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
    private static Item openBackpack(ItemStack instance) {
        BackpackItem item = (BackpackItem) instance.getItem();
        if (instance.hasTag()) {
            CompoundTag tag = instance.getTag();
            if (tag != null && tag.contains("nocoolmod_key")) {
                switch (tag.getString("nocoolmod_key")) {
                    case "nocoolmod.dirt.backpack" -> {
                        ((BackpackFace) item).no_Cool_modpack$setColumnCount(4);
                    }
                }
            }
        }
        return item;
    }

    @Unique
    private int no_Cool_modpack$column;

    @Inject(method = "getColumnCount", at = @At("RETURN"), cancellable = true, remap = false)
    private void getColumnCount(CallbackInfoReturnable<Integer> cir) {
        no_Cool_modpack$column = cir.getReturnValue();
        cir.setReturnValue(no_Cool_modpack$column);
    }

    @Override
    public void no_Cool_modpack$setColumnCount(int columnCount) {
        no_Cool_modpack$column = columnCount;
    }
}
