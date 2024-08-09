package com.natamus.realisticbees.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.realisticbees.events.BeeEvent;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

@EventBusSubscriber
public class NeoForgeBeeEvent {
	@SubscribeEvent
	public static void onBeeCheckSpawn(MobSpawnEvent.PositionCheck e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		BeeEvent.onBeeCheckSpawn(e.getEntity(), level, null, null, e.getSpawnType());
	}

	@SubscribeEvent
	public static void onBeeSpawn(EntityJoinLevelEvent e) {
		BeeEvent.onBeeSpawn(e.getEntity(), e.getLevel());
	}
}
