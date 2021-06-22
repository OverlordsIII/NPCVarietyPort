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
import net.minecraft.client.render.entity.EvokerEntityRenderer;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

@Mixin(EvokerEntityRenderer.class)
public abstract class EvokerEntityRendererMixin <T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T> {


	protected EvokerEntityRendererMixin(EntityRendererFactory.Context ctx, IllagerEntityModel<T> model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetTexture(SpellcastingIllagerEntity spellcastingIllagerEntity, CallbackInfoReturnable<Identifier> cir) {
		if (CONFIG.evokerVariation) {
			cir.setReturnValue(((SkinVariantManager) spellcastingIllagerEntity).getSkinVariant());
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addEyeTexture(EntityRendererFactory.Context context, CallbackInfo ci) {
		if (CONFIG.evokerVariation) {
			this.addFeature(new IllagerEntityFeaturesRenderer<>(this));
			this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
		}
	}

}
