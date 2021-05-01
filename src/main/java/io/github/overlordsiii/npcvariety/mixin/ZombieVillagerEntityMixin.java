package io.github.overlordsiii.npcvariety.mixin;

import java.util.Random;

import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.api.TextureIdList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


@Mixin(ZombieVillagerEntity.class)
public class ZombieVillagerEntityMixin extends ZombieEntity implements SkinVariantManager {

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private static final TextureIdList SKIN_TEXTURE_ID_LIST = new TextureIdList("textures/entity/zombie_villager/", 10, "skin");

	private static final TrackedData<Integer> SKIN_INDEX = DataTracker.registerData(ZombieVillagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public ZombieVillagerEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void addSkinDataToTag(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("skinIndex", getSkinIndex());
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void initIndex(CallbackInfo ci) {
		this.dataTracker.startTracking(SKIN_INDEX, this.random.nextInt(8));
	}

	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void readSkinDataFromTag(CompoundTag tag, CallbackInfo ci) {
		setSkinIndex(tag.getInt("skinIndex"));
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


	@Inject(method = "finishConversion", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EquipmentSlot;values()[Lnet/minecraft/entity/EquipmentSlot;"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void resetSkins(ServerWorld arg0, CallbackInfo ci, VillagerEntity villagerEntity) {
		((SkinVariantManager)villagerEntity).setSkinIndex(getSkinIndex());
		((EyeVariantManager)villagerEntity).setEyeIndex(5);
	}
}
