package io.github.overlordsiii.npcvariety.api;

import java.util.ArrayList;

import net.minecraft.util.Identifier;

public class TextureIdList extends ArrayList<Identifier> {
	public TextureIdList(String path, int idNums, String keyword) {
		for (int i = 0; i < idNums + 1; i++) {
			add(new Identifier("npcvariety:" + path + "/" + keyword + i + ".png"));
		}
	}
}
