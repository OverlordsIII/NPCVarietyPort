package io.github.overlordsiii.npcvariety.mixin.illager;

import static io.github.overlordsiii.npcvariety.NpcVariety.CONFIG;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.IllagerEntityFeaturesRenderer;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.VindicatorEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.util.Identifier;

@Mixin(VindicatorEntityRenderer.class)
public abstract class VindicatorEntityRendererMixin <T extends IllagerEntity> extends IllagerEntityRenderer<T> {


	protected VindicatorEntityRendererMixin(EntityRendererFactory.Context ctx, IllagerEntityModel<T> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void textureReset(VindicatorEntity vindicatorEntity, CallbackInfoReturnable<Identifier> cir) {
		if (CONFIG.vindicatorVariation) {
			cir.setReturnValue(((SkinVariantManager)vindicatorEntity).getSkinVariant());
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addIllagerFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
		this.addFeature(new IllagerEntityFeaturesRenderer<>(this));

	}
}
