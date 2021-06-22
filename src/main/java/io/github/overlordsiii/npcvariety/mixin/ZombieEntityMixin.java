package io.github.overlordsiii.npcvariety.mixin;

import static io.github.overlordsiii.npcvariety.NpcVariety.CONFIG;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {


	@Inject(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/ZombieVillagerEntity;setXp(I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void setZombieVillagerSkinIndex(ServerWorld serverWorld, LivingEntity livingEntity, CallbackInfo ci, VillagerEntity villagerEntity, ZombieVillagerEntity zombieVillagerEntity) {
		if (CONFIG.convertVillagerToZombieVillagerSkin) {
			((SkinVariantManager) zombieVillagerEntity).setSkinIndex(((SkinVariantManager) villagerEntity).getSkinIndex());
		}
	}

}
