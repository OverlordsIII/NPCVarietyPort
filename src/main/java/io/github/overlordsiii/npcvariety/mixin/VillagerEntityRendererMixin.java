package io.github.overlordsiii.npcvariety.mixin;

import static io.github.overlordsiii.npcvariety.NpcVariety.CONFIG;

import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.feature.LivingEntityEyeFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;

@Mixin(VillagerEntityRenderer.class)
public abstract class VillagerEntityRendererMixin extends MobEntityRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {

	public VillagerEntityRendererMixin(EntityRendererFactory.Context context, VillagerResemblingModel<VillagerEntity> entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	private void resetSkinTextures(VillagerEntity villagerEntity, CallbackInfoReturnable<Identifier> cir) {
		//cir.setReturnValue(new Identifier("npcvariety:textures/entity/villager/skin0.png"));
		if (CONFIG.villagerVariation) {
			cir.setReturnValue(((SkinVariantManager) villagerEntity).getSkinVariant());
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addEyeRenderer(EntityRendererFactory.Context context, CallbackInfo ci) {
		if (CONFIG.villagerVariation) {
			this.addFeature(new LivingEntityEyeFeatureRenderer<>(this));
		}
	}

}
