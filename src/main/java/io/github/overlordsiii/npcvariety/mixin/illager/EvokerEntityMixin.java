package io.github.overlordsiii.npcvariety.mixin.illager;

import io.github.overlordsiii.npcvariety.api.IllagerClothingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin extends SpellcastingIllagerEntity implements IllagerClothingManager {

	@Unique
	private static final Identifier[] headFeatures = {
		Identifier.of("npcvariety:textures/entity/illager/head_features/empty.png"),
		Identifier.of("npcvariety:textures/entity/illager/head_features/eyepatch_l.png"),
		Identifier.of("npcvariety:textures/entity/illager/head_features/eyepatch_r.png"),
	};

	@Unique
	private static final Identifier[] shoes = {
		Identifier.of("npcvariety:textures/entity/illager/shoes/light.png"),
		Identifier.of("npcvariety:textures/entity/illager/shoes/dark.png"),
	};

	@Unique
	private static final Identifier overClothes = Identifier.of("npcvariety:textures/entity/illager/overclothes/robe.png");

	@Unique
	private static final Identifier shirt = Identifier.of("npcvariety:textures/entity/illager/shirt/undershirt.png");

	@Unique
	private static final Identifier trousers = Identifier.of("npcvariety:textures/entity/illager/trousers/evoker.png");

	@Unique
	private static final TrackedData<Integer> shoeIndex = DataTracker.registerData(EvokerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	@Unique
	private static final TrackedData<Integer> headIndex = DataTracker.registerData(EvokerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected EvokerEntityMixin(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker", at = @At("HEAD"))
	protected void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(shoeIndex, this.random.nextInt(2));
		builder.add(headIndex, getEyePatchIndex());
	}

	@Unique
	private int getEyePatchIndex() {
		if (this.random.nextFloat() <= 0.05F) {
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

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		tag.putInt("shoeIndex", this.dataTracker.get(shoeIndex));
		tag.putInt("headIndex", this.dataTracker.get(headIndex));
		super.writeCustomDataToNbt(tag);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		if (tag.contains("shoeIndex")) {
			this.dataTracker.set(shoeIndex, tag.getInt("shoeIndex"));
		}
		if (tag.contains("headIndex")) {
			this.dataTracker.set(headIndex, tag.getInt("headIndex"));
		}
		super.readCustomDataFromNbt(tag);
	}


	@Override
	public Identifier getHeadFeature() {
		return headFeatures[this.dataTracker.get(headIndex)];
	}

	@Override
	public Identifier getOverClothes() {
		return overClothes;
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
