package io.github.chinawaremc.nocoolmod.mixin.forge;

import io.github.chinawaremc.nocoolmod.mface.ShapedFace;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.*;

@Mixin(ShapedRecipe.class)
@Debug(export = true)
public class ShapedRecipeMixin implements ShapedFace {

    @Mutable
    @Shadow @Final
    ItemStack result;

    @Override
    public void no_Cool_modpack$setResult(ItemStack result) {
        this.result = result;
    }
}
