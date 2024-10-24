package com.natamus.realisticbees.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityRenderer.class, priority = 1001)
public abstract class LivingEntityRendererMixin {
	@Inject(method = "scale", at = @At("HEAD"))
	private void applyScale(LivingEntityRenderState livingEntityRenderState, PoseStack poseStack, CallbackInfo ci) {
		if (((Object) this) instanceof BeeRenderer) {
			float scale = (float)ConfigHandler.beeSizeModifier;
			poseStack.scale(scale, scale, scale);
		}
	}
}