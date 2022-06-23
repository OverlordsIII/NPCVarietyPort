package io.github.overlordsiii.npcvariety.api;

import static io.github.overlordsiii.npcvariety.api.Rarity.RarityType.ABUNDANT;
import static io.github.overlordsiii.npcvariety.api.Rarity.RarityType.COMMON;
import static io.github.overlordsiii.npcvariety.api.Rarity.RarityType.RARE;
import static io.github.overlordsiii.npcvariety.api.Rarity.RarityType.REGULAR;
import static io.github.overlordsiii.npcvariety.api.Rarity.RarityType.UNCOMMON;

import java.util.Random;

public enum BiomeSpawnRate {
	DESERT(new Rarity<>(8), new Rarity<>(5, 6, 7), new Rarity<>(4, 10), new Rarity<>(0, 1, 2, 3), new Rarity<>(9)),
	PLAINS(new Rarity<>(4), new Rarity<>(5, 3), new Rarity<>(0, 1, 2, 6, 7), new Rarity<>(8), new Rarity<>(9, 10)),
	SAVANNA(new Rarity<>(7), new Rarity<>(8, 7, 6), new Rarity<>(5, 4), new Rarity<>(3, 2, 1, 0), new Rarity<>(9, 10)),
	TAIGA(new Rarity<>(9), new Rarity<>(0, 1, 2, 3), new Rarity<>(), new Rarity<>(4, 5, 6, 7, 8), new Rarity<>(10)),
	SNOWY(new Rarity<>(0), new Rarity<>(1, 2, 3), new Rarity<>(9), new Rarity<>(4, 10), new Rarity<>(8)),
	JUNGLE(new Rarity<>(10), new Rarity<>(), new Rarity<>(), new Rarity<>(), new Rarity<>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)),
	SWAMP(new Rarity<>(1), new Rarity<>(), new Rarity<>(), new Rarity<>(), new Rarity<>(0, 2, 3, 4, 5, 6, 7, 8, 9, 10));

	private final Rarity<Integer> abundant;
	private final Rarity<Integer> common;
	private final Rarity<Integer> regular;
	private final Rarity<Integer> uncommon;
	private final Rarity<Integer> rare;

	BiomeSpawnRate(Rarity<Integer> abundant, Rarity<Integer> common, Rarity<Integer> regular, Rarity<Integer> uncommon, Rarity<Integer> rare) {
		this.abundant = abundant.setType(ABUNDANT);
		this.common = common.setType(COMMON);
		this.regular = regular.setType(REGULAR);
		this.uncommon = uncommon.setType(UNCOMMON);
		this.rare = rare.setType(RARE);
	}

	public Rarity<Integer> getRandom(Random random) {

		if (abundant.getType().test(random)) {
		//	System.out.println("Giving villager (" + entity.getName().asString() + ") with rate type: " + this + " to abundant with values: " + abundant.getValues());
			return abundant;
		} else if (common.getType().test(random)) {
		//	System.out.println("Giving villager (" + entity.getName().asString() + ") with rate type: " + this + " to common with values: " + common.getValues());
			return common;
		} else if (regular.getType().test(random)) {
		//	System.out.println("Giving villager (" + entity.getName().asString() + ") with rate type: " + this + " to regular with values: " + regular.getValues());
			return regular;
		} else if (uncommon.getType().test(random)) {
		//	System.out.println("Giving villager (" + entity.getName().asString() + ") with rate type: " + this + " to uncommon with values: " + uncommon.getValues());
			return uncommon;
		} else if (rare.getType().test(random)){
		//	System.out.println("Giving villager (" + entity.getName().asString() + ") with rate type: " + this + " to rare with values: " + rare.getValues());
			return rare;
		}

		return null;
	}

	public Rarity<Integer> getUncommon() {
		return uncommon;
	}

	public Rarity<Integer> getRegular() {
		return regular;
	}

	public Rarity<Integer> getRare() {
		return rare;
	}

	public Rarity<Integer> getCommon() {
		return common;
	}

}
