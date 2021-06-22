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
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends IllagerEntity implements IllagerClothingManager {

	private static final Identifier[] headFeatures = {
		new Identifier("npcvariety:textures/entity/illager/head_features/empty.png"),
		new Identifier("npcvariety:textures/entity/illager/head_features/eyepatch_l.png"),
		new Identifier("npcvariety:textures/entity/illager/head_features/eyepatch_r.png"),
	};

	private static final Identifier trousers = new Identifier("npcvariety:textures/entity/illager/trousers/vindicator.png");
	private static final Identifier shirt = new Identifier("npcvariety:textures/entity/illager/shirt/undershirt.png");
	private static final Identifier overclothes = new Identifier("npcvariety:textures/entity/illager/overclothes/jacket.png");

	private static final Identifier[] shoes = {
		new Identifier("npcvariety:textures/entity/illager/shoes/light.png"),
		new Identifier("npcvariety:textures/entity/illager/shoes/dark.png"),
	};

	private static final TrackedData<Integer> headIndex = DataTracker.registerData(VindicatorEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> shoeIndex = DataTracker.registerData(VindicatorEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected VindicatorEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(headIndex, getEyePatchIndex());
		this.dataTracker.startTracking(shoeIndex, this.random.nextInt(2));
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	private void readIndexes(NbtCompound tag, CallbackInfo ci) {
		this.dataTracker.set(headIndex, tag.getInt("headIndex"));
		this.dataTracker.set(shoeIndex, tag.getInt("shoeIndex"));
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	private void writeIndexes(NbtCompound tag, CallbackInfo ci) {
		if (tag.contains("headIndex")) {
			tag.putInt("headIndex", this.dataTracker.get(headIndex));
		}
		if (tag.contains("shoeIndex")) {
			tag.putInt("shoeIndex", this.dataTracker.get(shoeIndex));
		}
	}

	@Override
	public void setEyePatch(boolean patch) {
		if (patch) {
			this.dataTracker.set(headIndex, this.random.nextBoolean() ? 1 : 2);
		} else {
			this.dataTracker.set(headIndex, 0);
		}
	}

	private int getEyePatchIndex() {
		if (this.random.nextFloat() <= 0.05F) {
			return this.random.nextBoolean() ? 1 : 2;
		}

		return 0;
	}

	@Override
	public Identifier getHeadFeature() {
		return headFeatures[this.dataTracker.get(headIndex)];
	}

	@Override
	public Identifier getOverClothes() {
		return overclothes;
	}

	@Override
	public Identifier getShirt() {
		return shirt;
	}

	@Override
	public Identifier getShoes() {
		return shoes[this.dataTracker.get(shoeIndex)];
	}

	@Override
	public Identifier getTrousers() {
		return trousers;
	}
}
