package io.github.overlordsiii.npcvariety.mixin;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.util.Identifier;

@Mixin(VillagerEntityRenderer.class)
public abstract class VillagerEntityRendererMixin extends MobEntityRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {
	public VillagerEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, VillagerResemblingModel<VillagerEntity> entityModel, float f) {
		super(entityRenderDispatcher, entityModel, f);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetSkinTextures(VillagerEntity villagerEntity, CallbackInfoReturnable<Identifier> cir) {
		//cir.setReturnValue(new Identifier("npcvariety:textures/entity/villager/skin0.png"));
		cir.setReturnValue(((SkinVariantManager)villagerEntity).getSkinVariant());
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addEyeRenderer(EntityRenderDispatcher dispatcher, ReloadableResourceManager reloadableResourceManager, CallbackInfo ci) {
		this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
	}

}
