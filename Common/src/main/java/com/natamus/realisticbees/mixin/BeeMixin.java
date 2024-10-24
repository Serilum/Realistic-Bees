package com.natamus.realisticbees.mixin;

import com.natamus.collective.functions.EntityFunctions;
import com.natamus.realisticbees.config.ConfigHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Bee.class, priority = 1001)
public abstract class BeeMixin extends Animal {
    protected BeeMixin(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", at = @At(value = "TAIL"))
    public void Bee(EntityType<? extends Bee> beeEntityType, Level level, CallbackInfo ci) {
        Bee bee = (Bee)(Object)this;
        EntityFunctions.setEntitySize(bee, (EntityDimensions.scalable(0.7F, 0.6F)).scale((float)ConfigHandler.beeSizeModifier), bee.getEyeHeight(Pose.STANDING));
    }

    @Inject(method = "hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "HEAD"), cancellable = true)
    private void hurtServer(ServerLevel serverLevel, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        Bee bee = (Bee)(Object)this;
        if (ConfigHandler.preventBeeSuffocationDamage && damageSource.equals(bee.level().damageSources().inWall())) {
            cir.setReturnValue(false);
        }
    }
}
