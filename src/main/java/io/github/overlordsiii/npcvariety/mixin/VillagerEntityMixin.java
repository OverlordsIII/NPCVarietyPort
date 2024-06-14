package io.github.overlordsiii.npcvariety.mixin;

import java.util.Random;

import io.github.overlordsiii.npcvariety.NpcVariety;
import io.github.overlordsiii.npcvariety.api.BiomeSpawnRate;
import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.Rarity;
import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
	@Shadow public abstract VillagerData getVillagerData();

	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "createChild", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;)Lnet/minecraft/entity/EntityData;"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void inheritTraits(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<VillagerEntity> cir, VillagerType villagerType3, VillagerEntity villagerEntity) {
		if (passiveEntity instanceof VillagerEntity dad && NpcVariety.CONFIG.inheritTraits) {
			boolean dadInheritSkin = this.random.nextBoolean();
			if (dadInheritSkin) {
				((SkinVariantManager) villagerEntity).setSkinIndex(((SkinVariantManager)dad).getSkinIndex());
			} else {
				((SkinVariantManager) villagerEntity).setSkinIndex(((SkinVariantManager)this).getSkinIndex());
			}
			boolean dadInheritEyes = this.random.nextBoolean();
			if (dadInheritEyes) {
				((EyeVariantManager) villagerEntity).setEyeIndex(((EyeVariantManager)dad).getEyeIndex());
			} else {
				((EyeVariantManager) villagerEntity).setEyeIndex(((EyeVariantManager)this).getEyeIndex());
			}
		}
	}


	@Inject(method = "initialize", at = @At("TAIL"))
	private void resetSkinIndexBasedOnBiome(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
		if (spawnReason == SpawnReason.STRUCTURE && NpcVariety.CONFIG.naturalVariation) {
			BiomeSpawnRate rate = getRateFromType(this.getVillagerData());

			Rarity<Integer> numbers = getRandomRarity(rate);

			int index = this.random.nextInt(numbers.getValues().size());

			int val = numbers.getValues().get(index);

			((SkinVariantManager)this).setSkinIndex(val);
		}
	}

	private static Rarity<Integer> getRandomRarity(BiomeSpawnRate rate) {
		Rarity<Integer> rarity = rate.getRandom(new Random());

		if (rarity == null) {
			return getRandomRarity(rate);
		}

		if (rarity.getValues().isEmpty()) {
			return getRandomRarity(rate);
		}

		return rarity;
	}

	//TODO make this datadriven
	private static BiomeSpawnRate getRateFromType(VillagerData data) {
		VillagerType type = data.getType();

		if (type == VillagerType.DESERT) {
			return BiomeSpawnRate.DESERT;
		} else if (type == VillagerType.SNOW) {
			return BiomeSpawnRate.SNOWY;
		} else if (type == VillagerType.PLAINS) {
			return BiomeSpawnRate.PLAINS;
		} else if (type == VillagerType.JUNGLE) {
			return BiomeSpawnRate.JUNGLE;
		} else if (type == VillagerType.SAVANNA) {
			return BiomeSpawnRate.SAVANNA;
		} else if (type == VillagerType.SWAMP) {
			return BiomeSpawnRate.SWAMP;
		} else {
			return BiomeSpawnRate.TAIGA;
		}
	}
}
