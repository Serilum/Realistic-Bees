package com.natamus.realisticbees.mixin;

import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1001)
public class LivingEntityMixin {
    @Inject(method = "getScale()F", at = @At(value = "HEAD"), cancellable = true)
    public void getScale(CallbackInfoReturnable<Float> cir) {
        if ((LivingEntity)(Object)this instanceof Bee bee) {
            AttributeMap $$0 = bee.getAttributes();
            cir.setReturnValue($$0 == null ? 1.0F : (float) ConfigHandler.beeSizeModifier);
        }
    }
}