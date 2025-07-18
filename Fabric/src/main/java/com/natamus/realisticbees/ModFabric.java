package com.natamus.realisticbees;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectiveSpawnEvents;
import com.natamus.realisticbees.events.BeeEvent;
import com.natamus.realisticbees.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CollectiveSpawnEvents.MOB_SPECIAL_SPAWN.register((Mob entity, Level level, BlockPos blockPos, BaseSpawner spawner, EntitySpawnReason spawnReason) -> {
			BeeEvent.onBeeCheckSpawn(entity, level, blockPos, spawner, spawnReason);
			return true;
		});

		ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerLevel serverLevel) -> {
			BeeEvent.onBeeSpawn(entity, serverLevel);
		});
	}

	private static void setGlobalConstants() {

	}
}
