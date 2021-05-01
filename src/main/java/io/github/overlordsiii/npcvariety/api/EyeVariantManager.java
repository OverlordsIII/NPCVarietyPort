package io.github.overlordsiii.npcvariety.api;

import net.minecraft.util.Identifier;

public interface EyeVariantManager {
	Identifier getEyeVariant();
	int getEyeIndex();
	void setEyeIndex(int index);
}
