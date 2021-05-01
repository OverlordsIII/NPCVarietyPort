package io.github.overlordsiii.npcvariety.mixin.illager;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import io.github.overlordsiii.npcvariety.feature.RavagerEntityClothingFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.RavagerEntityRenderer;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.Identifier;

@Mixin(RavagerEntityRenderer.class)
public abstract class RavagerEntityRendererMixin extends MobEntityRenderer<RavagerEntity, RavagerEntityModel> {
	public RavagerEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, RavagerEntityModel entityModel, float f) {
		super(entityRenderDispatcher, entityModel, f);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetTexture(RavagerEntity ravagerEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(((SkinVariantManager)ravagerEntity).getSkinVariant());
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addFeatures(EntityRenderDispatcher entityRenderDispatcher, CallbackInfo ci) {
		this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
		this.addFeature(new RavagerEntityClothingFeatureRenderer<>(this));
	}
}
