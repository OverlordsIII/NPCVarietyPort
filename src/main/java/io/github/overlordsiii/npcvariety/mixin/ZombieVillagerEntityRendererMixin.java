package io.github.overlordsiii.npcvariety.mixin;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.ZombieVillagerEntityRenderer;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.util.Identifier;

@Mixin(ZombieVillagerEntityRenderer.class)
public class ZombieVillagerEntityRendererMixin {

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void setSkinTexture(ZombieVillagerEntity zombieVillagerEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(((SkinVariantManager)zombieVillagerEntity).getSkinVariant());
	}
}
