package io.github.overlordsiii.npcvariety.mixin.spawner;

import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.world.SpawnHelper;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

	@ModifyArg(
		method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SpawnHelper;isValidSpawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/MobEntity;D)Z"),
		index = 1
	)
	private static MobEntity addPatchToNaturallySpawningLeaders(MobEntity mobEntity) {
		if (mobEntity instanceof PillagerEntity pillagerEntity) {
			if (pillagerEntity instanceof IllagerClothingManager manager && pillagerEntity.isPatrolLeader()) {
				manager.setEyePatch(true);
			}
		} else if (mobEntity instanceof VindicatorEntity vindicatorEntity) {
			if (vindicatorEntity instanceof IllagerClothingManager manager && vindicatorEntity.isPatrolLeader()) {
				manager.setEyePatch(true);
			}
		}
		return mobEntity;
	}

}
