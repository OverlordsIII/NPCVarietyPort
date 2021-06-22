package io.github.overlordsiii.npcvariety;

import io.github.overlordsiii.npcvariety.config.NPCVarietyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

import net.fabricmc.api.ModInitializer;

public class NpcVariety implements ModInitializer {

	static {
		AutoConfig.register(NPCVarietyConfig.class, JanksonConfigSerializer::new);

		CONFIG = AutoConfig.getConfigHolder(NPCVarietyConfig.class).getConfig();
	}

	public static final NPCVarietyConfig CONFIG;

	@Override
	public void onInitialize() {

	}
}
