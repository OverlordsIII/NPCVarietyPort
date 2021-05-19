package io.github.overlordsiii.npcvariety.mixin.spawner;

import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.LocalDifficulty;

@Mixin(Raid.class)
public class RaidMixin {

	@Inject(method = "spawnNextWave", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/raid/RaiderEntity;setPatrolLeader(Z)V"), locals = LocalCapture.PRINT)
	private void setEyePatch(BlockPos pos, CallbackInfo ci, boolean bl, int i, LocalDifficulty localDifficulty, boolean bl2, @Coerce Object[] var6, int var7, int var8, @Coerce Object member, int j, int k, int l, RaiderEntity raiderEntity) {
		if (raiderEntity instanceof IllagerClothingManager) {
			IllagerClothingManager manager = (IllagerClothingManager) raiderEntity;

			manager.setEyePatch(true);
		}
	}

}
