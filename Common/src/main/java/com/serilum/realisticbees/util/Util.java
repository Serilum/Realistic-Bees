package com.serilum.realisticbees.util;

import com.natamus.collective.data.GlobalVariables;
import com.serilum.realisticbees.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Util {
	public static boolean isNectarDeliveryCounted() {
		int deliveriesPerHoneyLevel = ConfigHandler.nectarDeliveriesPerHoneyLevel;
		if (deliveriesPerHoneyLevel <= 1) {
			return true;
		}

		return GlobalVariables.random.nextInt(deliveriesPerHoneyLevel) == 0;
	}

	public static BlockState applyNectarDeliveryChance(BlockState honeyState) {
		if (isNectarDeliveryCounted()) {
			return honeyState;
		}

		int honeyLevel = honeyState.getValue(BeehiveBlock.HONEY_LEVEL);
		return honeyState.setValue(BeehiveBlock.HONEY_LEVEL, honeyLevel - 1);
	}

	public static void repopulateHive(Level level, BlockPos hivePos, BeehiveBlockEntity hive) {
		if (!ConfigHandler.hivesRepopulateFromFullHoney) {
			return;
		}

		BlockState hiveState = level.getBlockState(hivePos);
		if (!hiveState.hasProperty(BeehiveBlock.HONEY_LEVEL) || hiveState.getValue(BeehiveBlock.HONEY_LEVEL) < BeehiveBlock.MAX_HONEY_LEVELS) {
			return;
		}

		if (hive.isFull()) {
			return;
		}

		BlockPos frontPos = hivePos.relative(hiveState.getValue(BeehiveBlock.FACING));
		if (!level.getBlockState(frontPos).getCollisionShape(level, frontPos).isEmpty()) {
			return;
		}

		Bee babyBee = EntityType.BEE.create(level);
		if (babyBee == null) {
			return;
		}

		babyBee.setAge(AgeableMob.BABY_START_AGE);
		Vec3 frontCenter = Vec3.atBottomCenterOf(frontPos);
		babyBee.moveTo(frontCenter.x, frontCenter.y, frontCenter.z, babyBee.getYRot(), babyBee.getXRot());
		level.addFreshEntity(babyBee);
		level.setBlockAndUpdate(hivePos, hiveState.setValue(BeehiveBlock.HONEY_LEVEL, 0));
	}
}
