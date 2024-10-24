package com.natamus.realisticbees.mixin;

import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeeRenderer.class, priority = 1001)
public abstract class BeeRendererMixin extends EntityRenderer<Bee, BeeRenderState> {
	protected BeeRendererMixin(EntityRendererProvider.Context $$0) {
		super($$0);
	}

	@Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;)V", at = @At(value = "TAIL"))
	public void BeeRenderer(EntityRendererProvider.Context p_173931_, CallbackInfo ci) {
		shadowRadius *= (float)ConfigHandler.beeSizeModifier;
	}
}
