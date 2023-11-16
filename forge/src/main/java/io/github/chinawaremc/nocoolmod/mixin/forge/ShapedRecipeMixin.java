package io.github.chinawaremc.nocoolmod.mixin.forge;

import io.github.chinawaremc.nocoolmod.mface.ShapedFace;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin implements ShapedFace {

    @Mutable
    @Shadow @Final
    ItemStack result;

    @Override
    public void no_Cool_modpack$setResult(ItemStack result) {
        this.result = result;
    }
}
