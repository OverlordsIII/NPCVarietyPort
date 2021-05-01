package io.github.overlordsiii.npcvariety.mixin;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import io.github.overlordsiii.npcvariety.feature.TraderClothingFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WanderingTraderEntityRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.util.Identifier;

@Mixin(WanderingTraderEntityRenderer.class)
public abstract class WanderingTraderEntityRendererMixin extends MobEntityRenderer<WanderingTraderEntity, VillagerResemblingModel<WanderingTraderEntity>> {
	public WanderingTraderEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, VillagerResemblingModel<WanderingTraderEntity> entityModel, float f) {
		super(entityRenderDispatcher, entityModel, f);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetTexture(WanderingTraderEntity wanderingTraderEntity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(((SkinVariantManager)wanderingTraderEntity).getSkinVariant());
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addEyeRenderer(EntityRenderDispatcher entityRenderDispatcher, CallbackInfo ci) {
		this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
		this.addFeature(new TraderClothingFeatureRenderer<>(this));
	}
}
