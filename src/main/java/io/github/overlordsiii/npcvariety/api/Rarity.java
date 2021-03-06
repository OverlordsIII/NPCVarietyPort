package io.github.overlordsiii.npcvariety.api;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.github.overlordsiii.npcvariety.NpcVariety;

/**
 * Represents a single rarity for example Common rarity
 */
public class Rarity<T> {

	private final List<T> values;

	private RarityType type;

	/**
	 * Represents the values that are in this rarity
	 * @param rarieties the var args that are in this rarity
	 */
	@SafeVarargs
	public Rarity(T... rarieties) {
		values = Arrays.asList(rarieties);
	}

	Rarity<T> setType(RarityType type) {
		this.type = type;
		return this;
	}

	public RarityType getType() {
		return type;
	}

	public List<T> getValues() {
		return values;
	}

	public enum RarityType {
		ABUNDANT(NpcVariety.CONFIG.abundantRarityPercentage),
		COMMON(NpcVariety.CONFIG.commonRarityPercentage),
		REGULAR(NpcVariety.CONFIG.regularRarityPercentage),
		UNCOMMON(NpcVariety.CONFIG.uncommonRarityPercentage),
		RARE(NpcVariety.CONFIG.rareRarityPercentage);

		private final int probability;

		RarityType(int probability) {
			this.probability = probability;
		}

		public boolean test(Random random) {
			return probability >= random.nextInt(100) + 1;
		}

	}
}
