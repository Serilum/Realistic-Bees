package com.serilum.realisticbees.mixin;

import com.serilum.realisticbees.config.ConfigHandler;
import com.serilum.realisticbees.util.Util;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityRenderer.class, priority = 1001)
public abstract class LivingEntityRendererMixin {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At(value = "TAIL"))
	public void extractRenderState(LivingEntity livingEntity, LivingEntityRenderState livingEntityRenderState, float partialTicks, CallbackInfo ci) {
		if (Util.shouldScaleBeeModelClientSide(livingEntity)) {
			livingEntityRenderState.scale *= (float)ConfigHandler.beeSizeModifier;
		}
	}
}
