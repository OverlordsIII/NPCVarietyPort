package io.github.overlordsiii.npcvariety.mixin.spawner;

import java.util.Random;

import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.PillagerSpawner;

@Mixin(PillagerSpawner.class)
public class PillagerSpawnerMixin {

	@Inject(method = "spawnPillager", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PatrolEntity;setRandomPatrolTarget()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void makePillagerWearEyePatch(ServerWorld world, BlockPos pos, Random random, boolean captain, CallbackInfoReturnable<Boolean> cir, PatrolEntity patrolEntity) {
		if (patrolEntity instanceof IllagerClothingManager) {
			IllagerClothingManager manager = (IllagerClothingManager) patrolEntity;

			manager.setEyePatch(true);
		}
	}

}
