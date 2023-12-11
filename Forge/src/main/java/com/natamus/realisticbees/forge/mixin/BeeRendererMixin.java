package com.natamus.realisticbees.forge.mixin;

import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeeRenderer.class, priority = 1001)
public abstract class BeeRendererMixin extends LivingEntityRenderer<Bee, BeeModel<Bee>> {
	public BeeRendererMixin(EntityRendererProvider.Context p_174289_, BeeModel<Bee> p_174290_, float p_174291_) {
		super(p_174289_, p_174290_, p_174291_);
	}

	@Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", at = @At(value = "TAIL"))
	public void BeeRenderer(EntityRendererProvider.Context p_173931_, CallbackInfo ci) {
		shadowRadius *= (float)ConfigHandler.beeSizeModifier;
	}
}
