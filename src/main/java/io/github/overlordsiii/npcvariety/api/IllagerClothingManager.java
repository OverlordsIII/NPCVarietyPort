package io.github.overlordsiii.npcvariety.api;

import net.minecraft.util.Identifier;

public interface IllagerClothingManager {
	Identifier getHeadFeature();
	Identifier getOverClothes();
	Identifier getShirt();
	Identifier getShoes();
	Identifier getTrousers();
	default void setEyePatch(boolean patch) {
	}
}
