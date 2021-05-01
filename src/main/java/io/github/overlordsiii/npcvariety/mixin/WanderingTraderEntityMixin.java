package io.github.overlordsiii.npcvariety.mixin;

import io.github.overlordsiii.npcvariety.api.TraderClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity implements TraderClothingManager {

	private static final Identifier[] robeTextures = {
		new Identifier("npcvariety:textures/entity/trader/blue_robe.png"),
		new Identifier("npcvariety:textures/entity/trader/red_gem_head.png")
	};

	private static final TrackedData<Integer> robeIndex = DataTracker.registerData(WanderingTraderEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(robeIndex, this.random.nextInt(2));
	}

	@Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
	private void writeRobeIndex(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("robeIndex", this.dataTracker.get(robeIndex));
	}

	@Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
	private void readRobeIndex(CompoundTag tag, CallbackInfo ci) {
		this.dataTracker.set(robeIndex, tag.getInt("robeIndex"));
	}



	@Override
	public Identifier getTraderClothes() {
		return robeTextures[this.dataTracker.get(robeIndex)];
		//return robeTextures[this.dataTracker.get(robeIndex)];
	}
}
