package com.serilum.realisticbees.util;

import com.natamus.collective.data.GlobalVariables;
import com.serilum.realisticbees.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Util {
	private static final Identifier beeSizeModifierId = Identifier.fromNamespaceAndPath(Reference.MOD_ID, "bee_size");

	public static void setBeeSize(Bee bee) {
		AttributeModifier beeSize = new AttributeModifier(beeSizeModifierId, ConfigHandler.beeSizeModifier - 1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		bee.getAttribute(Attributes.SCALE).addOrUpdateTransientModifier(beeSize);
	}

	public static boolean shouldScaleBeeModelClientSide(LivingEntity livingEntity) {
		if (!ConfigHandler.clientSideBeeSizeFallback) {
			return false;
		}

		if (!(livingEntity instanceof Bee bee)) {
			return false;
		}

		boolean serverAlreadyChangedBeeSize = bee.getAttribute(Attributes.SCALE).hasModifier(beeSizeModifierId);
		return !serverAlreadyChangedBeeSize;
	}

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

		Bee babyBee = EntityType.BEE.create(level, EntitySpawnReason.BREEDING);
		if (babyBee == null) {
			return;
		}

		babyBee.setAge(AgeableMob.BABY_START_AGE);
		babyBee.snapTo(Vec3.atBottomCenterOf(frontPos), babyBee.getYRot(), babyBee.getXRot());
		level.addFreshEntity(babyBee);
		level.setBlockAndUpdate(hivePos, hiveState.setValue(BeehiveBlock.HONEY_LEVEL, 0));
	}
}
