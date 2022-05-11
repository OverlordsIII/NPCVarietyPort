package io.github.overlordsiii.npcvariety.mixin.spawner;

import io.github.overlordsiii.npcvariety.NpcVariety;
import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.world.spawner.PatrolSpawner;

@Mixin(PatrolSpawner.class)
public class PillagerSpawnerMixin {
/*
	@Inject(method = "spawnPillager", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PatrolEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;"))
	private void makePillagerCaptainWearEyePatch(ServerWorld world, BlockPos pos, Random random, boolean captain, CallbackInfoReturnable<Boolean> cir) {
		if (patrolEntity instanceof IllagerClothingManager manager && NpcVariety.CONFIG.patrolEyePatch && captain) {
			manager.setEyePatch(true);
		}
	}
 */

	@ModifyArg(method = "spawnPillager", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnEntityAndPassengers(Lnet/minecraft/entity/Entity;)V"), index = 0)
	private Entity makePillagerCaptainWearEyePatch(Entity pillager) {
		if (pillager instanceof PatrolEntity patrolEntity && NpcVariety.CONFIG.patrolEyePatch) {
			if (patrolEntity.isPatrolLeader() && patrolEntity instanceof IllagerClothingManager manager) {
				manager.setEyePatch(true);
				return patrolEntity;
			}
		}
		return pillager;
	}

}
