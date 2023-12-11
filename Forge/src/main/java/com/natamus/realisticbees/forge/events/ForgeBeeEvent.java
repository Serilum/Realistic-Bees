package com.natamus.realisticbees.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.realisticbees.events.BeeEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeBeeEvent {
	@SubscribeEvent
	public void onBeeCheckSpawn(MobSpawnEvent.FinalizeSpawn e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		BeeEvent.onBeeCheckSpawn(e.getEntity(), level, null, null, e.getSpawnType());
	}

	@SubscribeEvent
	public void onBeeSpawn(EntityJoinLevelEvent e) {
		BeeEvent.onBeeSpawn(e.getEntity(), e.getLevel());
	}
}
