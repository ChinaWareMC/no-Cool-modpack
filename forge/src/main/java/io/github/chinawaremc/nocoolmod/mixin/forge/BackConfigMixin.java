package io.github.chinawaremc.nocoolmod.mixin.forge;

import com.mrcrayfish.backpacked.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Config.Common.class)
public class BackConfigMixin {

    @Shadow(remap = false) @Final public ForgeConfigSpec.IntValue backpackInventorySizeColumns;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(ForgeConfigSpec.Builder builder, CallbackInfo ci) {
        if (backpackInventorySizeColumns.get().equals(backpackInventorySizeColumns.getDefault())) {
            backpackInventorySizeColumns.set(3);
            backpackInventorySizeColumns.save();
        }
    }
}
