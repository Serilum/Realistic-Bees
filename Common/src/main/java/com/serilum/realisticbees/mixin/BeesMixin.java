package com.serilum.realisticbees.mixin;

import com.serilum.realisticbees.config.ConfigHandler;
import net.minecraft.world.item.component.Bees;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Bees.class, priority = 1001)
public class BeesMixin {
	@ModifyConstant(method = "addToTooltip", constant = @Constant(intValue = 3))
	public int addToTooltip_beeHiveBeeSpace(int size) {
		return ConfigHandler.beeHiveBeeSpace;
	}
}
