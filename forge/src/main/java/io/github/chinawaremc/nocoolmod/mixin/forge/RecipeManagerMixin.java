package io.github.chinawaremc.nocoolmod.mixin.forge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.chinawaremc.nocoolmod.forge.NocoolmodForge;
import io.github.chinawaremc.nocoolmod.mface.ShapedFace;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Shadow @Final private static Logger LOGGER;

    @Shadow private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("HEAD")
    )
    private void apply(Map<ResourceLocation, JsonElement> object,
                       ResourceManager resourceManager,
                       ProfilerFiller profiler,
                       CallbackInfo ci) {
        object.entrySet().removeIf(resourceLocationJsonElementEntry -> !resourceLocationJsonElementEntry.getKey().getNamespace().equals(NocoolmodForge.modid));
        object.forEach((resourceLocation, jsonElement) -> {
            LOGGER.warn("baka4n-test" + resourceLocation);
        });
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Inject(
            method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;Lnet/minecraftforge/common/crafting/conditions/ICondition$IContext;)Lnet/minecraft/world/item/crafting/Recipe;",
            at = @At(value = "RETURN"),
            cancellable = true)
    private static void fromJson(ResourceLocation arg, JsonObject jsonObject, ICondition.IContext context, CallbackInfoReturnable<Recipe<?>> cir) {
        Recipe<?> returnValue = cir.getReturnValue();
        if (arg.getNamespace().equals(NocoolmodForge.modid)) {
            switch (arg.getPath()) {
                case "dirt_backpack" -> {
                    if (returnValue instanceof ShapedRecipe shapedRecipe) {
                        ItemStack resultItem = shapedRecipe.getResultItem();
                        CompoundTag compoundTag = new CompoundTag();
                        compoundTag.putString("nocoolmod_key", "nocoolmod.dirt.backpack");
                        resultItem.setTag(compoundTag);
                        ((ShapedFace) shapedRecipe).no_Cool_modpack$setResult(resultItem);
                        cir.setReturnValue(shapedRecipe);
                    }
                }
            }

        }
    }

}
