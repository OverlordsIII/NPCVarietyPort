package io.github.overlordsiii.npcvariety.mixin;

import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.api.TextureIdList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;

@Mixin(MerchantEntity.class)
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public abstract class MerchantEntityMixin extends PassiveEntity implements SkinVariantManager, EyeVariantManager {

	public MerchantEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	private static final TextureIdList SKIN_TEXTURE_ID_LIST = new TextureIdList("textures/entity/villager/", 10, "skin");

	private static final TextureIdList EYE_TEXTURE_ID_LIST = new TextureIdList("textures/entity/villager/eyes", 4, "eye");
	private static final TextureIdList HALF_EYE_TEXTURE_ID_LIST = new TextureIdList("textures/entity/villager/eyes/half", 4, "eye");


	private static final TrackedData<Integer> SKIN_INDEX = DataTracker.registerData(MerchantEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> EYE_INDEX = DataTracker.registerData(MerchantEntity.class, TrackedDataHandlerRegistry.INTEGER);

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void setIndexes(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(SKIN_INDEX, this.random.nextInt(8));
		builder.add(EYE_INDEX, getRandomEyeIndex());
	}

	@Unique
	private int getRandomEyeIndex() {
		if (this.random.nextBoolean()) {
			return 3;
		}
		return this.random.nextInt(5);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void addSkinDataToTag(NbtCompound tag, CallbackInfo ci) {
		tag.putInt("skinIndex", getSkinIndex());
		tag.putInt("eyeIndex", getEyeIndex());
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readSkinDataFromTag(NbtCompound tag, CallbackInfo ci) {
		if (tag.contains("skinIndex")) {
			setSkinIndex(tag.getInt("skinIndex"));
		}
		if (tag.contains("eyeIndex")) {
			setEyeIndex(tag.getInt("eyeIndex"));
		}
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
			if (((MerchantEntity) (Object) this) instanceof VillagerEntity entity) {
				if (entity.getVillagerData().getProfession().equals(VillagerProfession.WEAPONSMITH)) {
					return new Identifier("npcvariety:textures/entity/villager/eyes/half/eye5.png");
				}
			}

			return new Identifier("npcvariety:textures/entity/villager/eyes/eye5.png");
		}

		if (((MerchantEntity) (Object) this) instanceof VillagerEntity entity) {
			if (entity.getVillagerData().getProfession().equals(VillagerProfession.WEAPONSMITH)) {
				return HALF_EYE_TEXTURE_ID_LIST.get(getEyeIndex());
			}
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
