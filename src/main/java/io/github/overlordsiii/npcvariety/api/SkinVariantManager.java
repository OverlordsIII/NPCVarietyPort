package io.github.overlordsiii.npcvariety.api;

import net.minecraft.util.Identifier;

public interface SkinVariantManager {
	Identifier getSkinVariant();
	void setSkinIndex(int index);
	int getSkinIndex();
}
