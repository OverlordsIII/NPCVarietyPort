package io.github.overlordsiii.npcvariety.feature;

import io.github.overlordsiii.npcvariety.api.RavagerClothingManager;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.RavagerEntity;

public class RavagerEntityClothingFeatureRenderer<T extends RavagerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	public RavagerEntityClothingFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof RavagerClothingManager) {
			renderModel(this.getContextModel(), ((RavagerClothingManager)entity).getOverClothes(), matrices, vertexConsumers, light, entity, 1.0F, 1.0F, 1.0F);
		}
	}
}
