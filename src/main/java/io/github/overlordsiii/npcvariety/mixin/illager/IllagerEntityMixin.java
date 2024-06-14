package io.github.overlordsiii.npcvariety.mixin.illager;

import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import io.github.overlordsiii.npcvariety.api.TextureIdList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Mixin(IllagerEntity.class)
public abstract class IllagerEntityMixin extends RaiderEntity implements SkinVariantManager, EyeVariantManager {

	@Unique
	private static final TextureIdList SKIN_TEXTURE_ID_LIST = new TextureIdList("textures/entity/illager/", 7, "skin");;

	@Unique
	private static final TextureIdList EYE_TEXTURE_ID_LIST = new TextureIdList("textures/entity/illager/eyes", 3, "eye");

	@Unique
	private static final TrackedData<Integer> SKIN_INDEX = DataTracker.registerData(IllagerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	@Unique
	private static final TrackedData<Integer> EYE_INDEX = DataTracker.registerData(IllagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected IllagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(EYE_INDEX, this.getRandomEyeIndex());
		builder.add(SKIN_INDEX, this.random.nextInt(8));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (tag.contains("eyeIndex")) {
			setEyeIndex(tag.getInt("eyeIndex"));
		}
		if (tag.contains("skinIndex")) {
			setSkinIndex(tag.getInt("skinIndex"));
		}
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		tag.putInt("eyeIndex", getEyeIndex());
		tag.putInt("skinIndex", getSkinIndex());
		super.writeCustomDataToNbt(tag);
	}

	private int getRandomEyeIndex() {
		if (this.random.nextBoolean()) {
			return 4;
		}

		return this.random.nextInt(4);
	}

	@Override
	public Identifier getEyeVariant() {

		if (getEyeIndex() == 4) {
			return Identifier.of("npcvariety:textures/entity/illager/eyes/eye4.png");
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
}
