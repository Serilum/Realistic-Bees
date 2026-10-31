package com.serilum.realisticbees.mixin;

import com.serilum.realisticbees.config.ConfigHandler;
import com.serilum.realisticbees.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BeehiveBlockEntity.class, priority = 1001)
public class BeehiveBlockEntityMixin {
	@Inject(method = "isFull()Z", at = @At(value = "HEAD"), cancellable = true)
	private void isFull(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(((BeehiveBlockEntity)(Object)this).getOccupantCount() >= ConfigHandler.beeHiveBeeSpace);
	}

	@ModifyConstant(method = "addOccupant", constant = @Constant(intValue = 3))
	public int addOccupantWithPresetTicks_increaseSize(int size) {
		return ConfigHandler.beeHiveBeeSpace;
	}

	@ModifyArg(method = "releaseOccupant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), index = 1)
	private static BlockState releaseOccupant_nectarDeliveryChance(BlockState honeyState) {
		return Util.applyNectarDeliveryChance(honeyState);
	}

	@Inject(method = "serverTick", at = @At(value = "TAIL"))
	private static void serverTick(Level level, BlockPos hivePos, BlockState state, BeehiveBlockEntity hive, CallbackInfo ci) {
		Util.repopulateHive(level, hivePos, hive);
	}
}
