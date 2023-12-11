package com.natamus.realisticbees.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.realisticbees.events.BeeEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeBeeEvent {
	@SubscribeEvent
	public void onBeeCheckSpawn(LivingSpawnEvent.SpecialSpawn e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
		if (level == null) {
			return;
		}

		Entity entity = e.getEntity();
		if (!(entity instanceof Mob)) {
			return;
		}

		BeeEvent.onBeeCheckSpawn((Mob)entity, level, null, null, e.getSpawnReason());
	}

	@SubscribeEvent
	public void onBeeSpawn(EntityJoinWorldEvent e) {
		BeeEvent.onBeeSpawn(e.getEntity(), e.getWorld());
	}
}
