package com.serilum.realisticbees.mixin;

import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = BeeNestDestroyedTrigger.TriggerInstance.class, priority = 1001)
public class BeeNestDestroyedTriggerMixin {
	@Shadow private @Final MinMaxBounds.Ints numBees;

	@ModifyVariable(method = "matches", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
	public int matches_numBeesInside(int numBeesInside) {
		Integer minBeesInside = this.numBees.getMin();
		if (minBeesInside == null) {
			return numBeesInside;
		}

		if (minBeesInside < 3) {
			return numBeesInside;
		}

		return Math.min(numBeesInside, minBeesInside);
	}
}
