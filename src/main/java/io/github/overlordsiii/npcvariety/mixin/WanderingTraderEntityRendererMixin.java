package io.github.overlordsiii.npcvariety.mixin;

import static io.github.overlordsiii.npcvariety.NpcVariety.CONFIG;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import io.github.overlordsiii.npcvariety.feature.TraderClothingFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WanderingTraderEntityRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.util.Identifier;

@Mixin(WanderingTraderEntityRenderer.class)
public abstract class WanderingTraderEntityRendererMixin extends MobEntityRenderer<WanderingTraderEntity, VillagerResemblingModel<WanderingTraderEntity>> {

	public WanderingTraderEntityRendererMixin(EntityRendererFactory.Context context, VillagerResemblingModel<WanderingTraderEntity> entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetTexture(WanderingTraderEntity wanderingTraderEntity, CallbackInfoReturnable<Identifier> cir) {
		if (CONFIG.wanderingTraderVariation) {
			cir.setReturnValue(((SkinVariantManager) wanderingTraderEntity).getSkinVariant());
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addEyeRenderer(EntityRendererFactory.Context context, CallbackInfo ci) {
		if (CONFIG.wanderingTraderVariation) {
			this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
			this.addFeature(new TraderClothingFeatureRenderer<>(this));
		}
	}
}
