package com.serilum.realisticbees.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.realisticbees.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0.01, max = 5.0) public static double beeSizeModifier = 0.25;
	@Entry(min = 0, max = 50) public static int extraBeeSpawnsPerBee = 9;
	@Entry(min = 0, max = 50) public static int beeHiveBeeSpace = 20;
	@Entry public static boolean preventBeeSuffocationDamage = true;
	@Entry public static boolean preventBeeCampfireDamage = false;
	@Entry(min = 1, max = 20) public static int nectarDeliveriesPerHoneyLevel = 1;
	@Entry public static boolean hivesRepopulateFromFullHoney = false;

	public static void initConfig() {
		configMetaData.put("beeSizeModifier", Arrays.asList(
			"This value determines the size of a bee. By default 0.25, which means they are 1/4th of their vanilla size. Set to 1.0 to disable the different bee size feature."
		));
		configMetaData.put("extraBeeSpawnsPerBee", Arrays.asList(
			"In order to make bees a little more common. Whenever a bee naturally spawns, the mod spawns an additional 'extraBeeSpawnsPerBee' bees."
		));
		configMetaData.put("beeHiveBeeSpace", Arrays.asList(
			"How many bees should be able to enter the hive in total. Minecraft's default is 3, but bees are a lot smaller so there should be more space."
		));
		configMetaData.put("preventBeeSuffocationDamage", Arrays.asList(
			"The smaller (baby) bees can sometimes do something unexpected with their AI and get suffocation damage. Preventing this damage fixes them disappearing."
		));
		configMetaData.put("preventBeeCampfireDamage", Arrays.asList(
			"Smaller bees fly closer to a campfire placed below their hive and can get burned by it. When enabled, bees take no damage from campfires."
		));
		configMetaData.put("nectarDeliveriesPerHoneyLevel", Arrays.asList(
			"How many nectar deliveries it takes on average to raise a hive's honey level by one. Minecraft's default is 1, but with more bees per hive the honey fills up a lot faster."
		));
		configMetaData.put("hivesRepopulateFromFullHoney", Arrays.asList(
			"When enabled, a hive that fills up with honey and still has space uses up the honey to release a new baby bee."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}