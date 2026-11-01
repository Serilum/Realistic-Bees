package com.serilum.realisticbees.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.serilum.realisticbees.config.ConfigHandler;
import com.serilum.realisticbees.util.Util;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityRenderer.class, priority = 1001)
public abstract class LivingEntityRendererMixin {
	@Inject(method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", at = @At(value = "HEAD"))
	public void scale(LivingEntity livingEntity, PoseStack poseStack, float partialTickTime, CallbackInfo ci) {
		if (Util.shouldScaleBeeModelClientSide(livingEntity)) {
			float beeSize = (float)ConfigHandler.beeSizeModifier;
			poseStack.scale(beeSize, beeSize, beeSize);
		}
	}
}
