package com.natamus.realisticbees.mixin;

import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = BeeNestDestroyedTrigger.TriggerInstance.class, priority = 1001)
public class BeeNestDestroyedTriggerMixin {
    @Shadow private @Final MinMaxBounds.Ints beesInside;

    @Inject(method = "matches", at = @At(value = "HEAD"), cancellable = true)
    public void matches(BlockState blockState, ItemStack itemStack, int numBees, CallbackInfoReturnable<Boolean> cir) {
        Optional<Integer> minBeesInsideOptional = this.beesInside.min();
        if (minBeesInsideOptional.isPresent()) {
            if (minBeesInsideOptional.get() >= 3) {
                cir.setReturnValue(true);
            }
        }
    }
}
