package io.github.overlordsiii.npcvariety.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "npcvariety")
@Config.Gui.Background("minecraft:textures/block/barrel_side.png")
@Config.Gui.CategoryBackground(category = "rarityPercentage", background = "minecraft:textures/block/emerald_block.png")
@Config.Gui.CategoryBackground(category = "generalRules", background = "minecraft:textures/block/jukebox_side.png")
@Config.Gui.CategoryBackground(category = "mobVariation", background = "minecraft:textures/block/bookshelf.png")

public class NPCVarietyConfig implements ConfigData {

	@ConfigEntry.Category("rarityPercentage")
	@ConfigEntry.Gui.RequiresRestart
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	@ConfigEntry.Gui.Tooltip
	@Comment("Indicates what the rarity is for abundant type of villager skins during world generation")
	public int abundantRarityPercentage = 50;

	@ConfigEntry.Category("rarityPercentage")
	@ConfigEntry.Gui.RequiresRestart
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	@ConfigEntry.Gui.Tooltip
	@Comment("Indicates what the rarity is for common type of villager skins during world generation")
	public int commonRarityPercentage = 30;

	@ConfigEntry.Category("rarityPercentage")
	@ConfigEntry.Gui.RequiresRestart
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	@ConfigEntry.Gui.Tooltip
	@Comment("Indicates what the rarity is for regular type of villager skins during world generation")
	public int regularRarityPercentage = 10;

	@ConfigEntry.Category("rarityPercentage")
	@ConfigEntry.Gui.RequiresRestart
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	@ConfigEntry.Gui.Tooltip
	@Comment("Indicates what the rarity is for uncommon type of villager skins during world generation")
	public int uncommonRarityPercentage = 7;

	@ConfigEntry.Category("rarityPercentage")
	@ConfigEntry.Gui.RequiresRestart
	@ConfigEntry.BoundedDiscrete(min = 0, max = 100)
	@ConfigEntry.Gui.Tooltip
	@Comment("Indicates what the rarity is for rare type of villager skins during world generation")
	public int rareRarityPercentage = 3;

	@ConfigEntry.Category("generalRules")
	@ConfigEntry.Gui.Tooltip
	@Comment("Indicates that during a raid or patrol, the leader will always have eyepatch enabled")
	public boolean patrolEyePatch = true;

	@ConfigEntry.Category("generalRules")
	@ConfigEntry.Gui.Tooltip
	@Comment("Allows natural variation be assigned when a Village is generated naturally")
	public boolean naturalVariation = true;

	@ConfigEntry.Category("generalRules")
	@ConfigEntry.Gui.Tooltip
	@Comment("Allows villager children to inherit skin and eyes from parents")
	public boolean inheritTraits = true;

	@ConfigEntry.Category("generalRules")
	@ConfigEntry.Gui.Tooltip
	@Comment("Allows zombies to convert villagers skins into zombie villager version when killing them")
	public boolean convertVillagerToZombieVillagerSkin = true;

	@ConfigEntry.Category("generalRules")
	@ConfigEntry.Gui.Tooltip
	@Comment("Allows zombie villager skins to carry over to villagers after being cured")
	public boolean convertZombieVillagerSkinsToVillager = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for evokers")
	public boolean evokerVariation = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for pillagers")
	public boolean pillagerVariation = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for ravagers")
	public boolean ravagerVariation = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for vindicators")
	public boolean vindicatorVariation = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for villagers")
	public boolean villagerVariation = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for wandering traders")
	public boolean wanderingTraderVariation = true;

	@ConfigEntry.Category("mobVariation")
	@ConfigEntry.Gui.Tooltip
	@Comment("Gives skin variation for zombie villagers")
	public boolean zombieVillagerVariation = true;

}
