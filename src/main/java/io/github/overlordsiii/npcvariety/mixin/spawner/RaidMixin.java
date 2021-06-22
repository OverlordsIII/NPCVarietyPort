package io.github.overlordsiii.npcvariety.mixin.spawner;

import io.github.overlordsiii.npcvariety.NpcVariety;
import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.village.raid.Raid;

@Mixin(Raid.class)
public class RaidMixin {

	@Inject(method = "setWaveCaptain", at = @At("TAIL"))
	private void wave(int wave, RaiderEntity entity, CallbackInfo ci) {
		if (entity instanceof IllagerClothingManager manager && NpcVariety.CONFIG.patrolEyePatch) {
			manager.setEyePatch(true);
		}
	}
}
