package com.serilum.realisticbees.mixin;

import com.serilum.realisticbees.config.ConfigHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Bee.class, priority = 1001)
public abstract class BeeMixin extends Animal {
	protected BeeMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	@Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "HEAD"), cancellable = true)
	private void hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
		Bee bee = (Bee)(Object)this;
		if (ConfigHandler.preventBeeSuffocationDamage && damageSource.equals(bee.level().damageSources().inWall())) {
			cir.setReturnValue(false);
			return;
		}

		if (ConfigHandler.preventBeeCampfireDamage && damageSource.is(DamageTypes.CAMPFIRE)) {
			cir.setReturnValue(false);
		}
	}
}
