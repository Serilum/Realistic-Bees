package com.natamus.realisticbees.fabric.mixin;

import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BeehiveBlockEntity.class, priority = 1001)
public class BeehiveBlockEntityMixin {
    @Inject(method = "isFull()Z", at = @At(value = "HEAD"), cancellable = true)
    private void hurt(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(((BeehiveBlockEntity)(Object)this).getOccupantCount() == ConfigHandler.beeHiveBeeSpace);
    }

    @ModifyConstant(method = "addOccupantWithPresetTicks(Lnet/minecraft/world/entity/Entity;ZI)V", constant = @Constant(intValue = 3))
    public int addOccupantWithPresetTicks_increaseSize(int size) {
        return ConfigHandler.beeHiveBeeSpace;
    }
}
