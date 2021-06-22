package io.github.overlordsiii.npcvariety.mixin.illager;

import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends IllagerEntity implements IllagerClothingManager {

	private static final Identifier trousers = new Identifier("npcvariety:textures/entity/illager/trousers/pillager.png");
	private static final Identifier shirt = new Identifier("npcvariety:textures/entity/illager/shirt/pillager.png");
	private static final Identifier shoes = new Identifier("npcvariety:textures/entity/illager/shoes/pillager.png");

	private static final Identifier[] headFeatures = {
		new Identifier("npcvariety:textures/entity/illager/head_features/empty.png"),
		new Identifier("npcvariety:textures/entity/illager/head_features/eyepatch_l.png"),
		new Identifier("npcvariety:textures/entity/illager/head_features/eyepatch_r.png"),
	};

	private static final TrackedData<Integer> headIndex = DataTracker.registerData(PillagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected PillagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker", at = @At("HEAD"))
	private void addHeadIndex(CallbackInfo ci) {
		this.dataTracker.startTracking(headIndex, getEyePatchIndex());
	}

	private int getEyePatchIndex() {
		if (this.random.nextFloat() <= 0.15F) {
			return this.random.nextBoolean() ? 1 : 2;
		}

		return 0;
	}

	@Override
	public void setEyePatch(boolean patch) {
		if (patch) {
			this.dataTracker.set(headIndex, this.random.nextBoolean() ? 1 : 2);
		} else {
			this.dataTracker.set(headIndex, 0);
		}
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	private void readHeadIndex(NbtCompound tag, CallbackInfo ci) {
		this.dataTracker.set(headIndex, tag.getInt("headIndex"));
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	private void writeHeadIndex(NbtCompound tag, CallbackInfo ci) {
		if (tag.contains("headIndex")) {
			tag.putInt("headIndex", this.dataTracker.get(headIndex));
		}
	}


	@Override
	public Identifier getHeadFeature() {
		return headFeatures[this.dataTracker.get(headIndex)];
	}

	@Override
	public Identifier getOverClothes() {
		return shirt;
	}

	@Override
	public Identifier getShirt() {
		return shirt;
	}

	@Override
	public Identifier getShoes() {
		return shoes;
	}

	@Override
	public Identifier getTrousers() {
		return trousers;
	}
}
