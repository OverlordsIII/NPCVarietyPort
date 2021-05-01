package io.github.overlordsiii.npcvariety.mixin.illager;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.IllagerEntityFeaturesRenderer;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.PillagerEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.util.Identifier;

@Mixin(PillagerEntityRenderer.class)
public abstract class PillagerEntityRendererMixin <T extends IllagerEntity> extends IllagerEntityRenderer<T> {
	protected PillagerEntityRendererMixin(EntityRenderDispatcher dispatcher, IllagerEntityModel<T> model, float f) {
		super(dispatcher, model, f);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetTexture(PillagerEntity pillagerEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(((SkinVariantManager)pillagerEntity).getSkinVariant());
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	protected void addFeatures(EntityRenderDispatcher entityRenderDispatcher, CallbackInfo ci) {
		this.addFeature(new IllagerEntityFeaturesRenderer<>(this));
		this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
	}
}
