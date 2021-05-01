package io.github.overlordsiii.npcvariety.feature;

import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;

public class IllagerEntityFeaturesRenderer<T extends IllagerEntity, M extends CompositeEntityModel<T>> extends FeatureRenderer<T, M> {
	public IllagerEntityFeaturesRenderer(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof IllagerClothingManager) {
			IllagerClothingManager clothingManager = (IllagerClothingManager) entity;

			renderModel(this.getContextModel(), clothingManager.getHeadFeature(), matrices, vertexConsumers, light, entity, 1.0F, 1.0F, 1.0F);
			renderModel(this.getContextModel(), clothingManager.getOverClothes(), matrices, vertexConsumers, light, entity, 1.0F, 1.0F, 1.0F);
			renderModel(this.getContextModel(), clothingManager.getShirt(), matrices, vertexConsumers, light, entity, 1.0F, 1.0F, 1.0F);
			renderModel(this.getContextModel(), clothingManager.getTrousers(), matrices, vertexConsumers, light, entity, 1.0F, 1.0F, 1.0F);
			renderModel(this.getContextModel(), clothingManager.getShoes(), matrices, vertexConsumers, light, entity, 1.0F, 1.0F, 1.0F);
		}
	}
}
