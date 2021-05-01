package io.github.overlordsiii.npcvariety.mixin;

import java.util.List;
import java.util.Random;

import io.github.overlordsiii.npcvariety.api.BiomeSpawnRate;
import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.Rarity;
import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.api.TextureIdList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

@Mixin(MerchantEntity.class)
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public abstract class MerchantEntityMixin extends PassiveEntity implements SkinVariantManager, EyeVariantManager {

	public MerchantEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	private static final TextureIdList SKIN_TEXTURE_ID_LIST = new TextureIdList("textures/entity/villager/", 10, "skin");

	private static final TextureIdList EYE_TEXTURE_ID_LIST = new TextureIdList("textures/entity/villager/eyes", 4, "eye");


	private static final TrackedData<Integer> SKIN_INDEX = DataTracker.registerData(MerchantEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> EYE_INDEX = DataTracker.registerData(MerchantEntity.class, TrackedDataHandlerRegistry.INTEGER);

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void setIndexes(CallbackInfo ci) {
		this.dataTracker.startTracking(SKIN_INDEX, this.random.nextInt(8));
		this.dataTracker.startTracking(EYE_INDEX, getRandomEyeIndex());
	}

	private int getRandomEyeIndex() {
		if (this.random.nextBoolean()) {
			return 3;
		}
		return this.random.nextInt(5);
	}

	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void addSkinDataToTag(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("skinIndex", getSkinIndex());
		tag.putInt("eyeIndex", getEyeIndex());
	}

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void readSkinDataFromTag(CompoundTag tag, CallbackInfo ci) {
		setSkinIndex(tag.getInt("skinIndex"));
		setEyeIndex(tag.getInt("eyeIndex"));
	}

	@Override
	public Identifier getSkinVariant() {
		return SKIN_TEXTURE_ID_LIST.get(getSkinIndex());
	}

	@Override
	public void setSkinIndex(int index) {
		this.dataTracker.set(SKIN_INDEX, index);
	}

	@Override
	public int getSkinIndex() {
		return this.dataTracker.get(SKIN_INDEX);
	}

	@Override
	public Identifier getEyeVariant() {

		if (getEyeIndex() == 5) {
			return new Identifier("npcvariety:textures/entity/villager/eyes/eye5.png");
		}

		return EYE_TEXTURE_ID_LIST.get(getEyeIndex());
	}

	@Override
	public int getEyeIndex() {
		return this.dataTracker.get(EYE_INDEX);
	}

	@Override
	public void setEyeIndex(int index) {
		this.dataTracker.set(EYE_INDEX, index);
	}





}
