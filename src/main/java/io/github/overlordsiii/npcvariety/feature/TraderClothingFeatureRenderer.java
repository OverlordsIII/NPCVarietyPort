package io.github.overlordsiii.npcvariety.feature;

import io.github.overlordsiii.npcvariety.api.TraderClothingManager;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.MerchantEntity;

public class TraderClothingFeatureRenderer<T extends MerchantEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

	public TraderClothingFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity instanceof TraderClothingManager manager && !entity.isInvisible()) {

			renderModel(this.getContextModel(), manager.getTraderClothes(), matrices, vertexConsumers, light, entity, -1);
		}
	}
}
