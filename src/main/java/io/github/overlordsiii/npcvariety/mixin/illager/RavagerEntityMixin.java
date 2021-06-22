package io.github.overlordsiii.npcvariety.mixin.illager;

import io.github.overlordsiii.npcvariety.api.EyeVariantManager;
import io.github.overlordsiii.npcvariety.api.RavagerClothingManager;
import io.github.overlordsiii.npcvariety.api.SkinVariantManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends RaiderEntity implements SkinVariantManager, EyeVariantManager, RavagerClothingManager {

	private static final Identifier[] skinTextures = {
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager1.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager2.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager3.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager4.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager5.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager6.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager7.png"),
		new Identifier("npcvariety:textures/entity/illager/ravager/ravager8.png"),
	};

	private static final Identifier[] eyeTextures = {
		new Identifier("npcvariety:textures/entity/illager/eyes/eye0.png"),
		new Identifier("npcvariety:textures/entity/illager/eyes/eye1.png"),
		new Identifier("npcvariety:textures/entity/illager/eyes/eye2.png"),
		new Identifier("npcvariety:textures/entity/illager/eyes/eye3.png"),
		new Identifier("npcvariety:textures/entity/illager/eyes/eye4.png"),
	};

	private static final Identifier clothing = new Identifier("npcvariety:textures/entity/illager/ravager/chains.png");

	private static final TrackedData<Integer> skinIndex = DataTracker.registerData(RavagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> eyeIndex = DataTracker.registerData(RavagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected RavagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(skinIndex, this.random.nextInt(8));
		this.dataTracker.startTracking(eyeIndex, this.random.nextInt(5));
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	private void readIndex(NbtCompound tag, CallbackInfo ci) {
		this.dataTracker.set(skinIndex, tag.getInt("skinIndex"));
		this.dataTracker.set(eyeIndex, tag.getInt("eyeIndex"));
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	private void writeIndex(NbtCompound tag, CallbackInfo ci) {
		if (tag.contains("skinIndex")) {
			tag.putInt("skinIndex", this.dataTracker.get(skinIndex));
		}
		if (tag.contains("eyeIndex")) {
			tag.putInt("eyeIndex", this.dataTracker.get(eyeIndex));
		}
	}

	@Override
	public Identifier getEyeVariant() {
		return eyeTextures[getEyeIndex()];
	}

	@Override
	public int getEyeIndex() {
		return this.dataTracker.get(eyeIndex);
	}

	@Override
	public void setEyeIndex(int index) {
		this.dataTracker.set(eyeIndex, index);
	}

	@Override
	public Identifier getOverClothes() {
		return clothing;
	}

	@Override
	public Identifier getSkinVariant() {
		return skinTextures[getSkinIndex()];
	}

	@Override
	public void setSkinIndex(int index) {
		this.dataTracker.set(skinIndex, index);
	}

	@Override
	public int getSkinIndex() {
		return this.dataTracker.get(skinIndex);
	}
}
