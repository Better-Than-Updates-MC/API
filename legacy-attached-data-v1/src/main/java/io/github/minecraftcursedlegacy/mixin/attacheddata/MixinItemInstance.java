package io.github.minecraftcursedlegacy.mixin.attacheddata;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData;
import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager;
import io.github.minecraftcursedlegacy.api.attacheddata.v1.SimpleDataStorage;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

@Mixin(ItemInstance.class)
public abstract class MixinItemInstance implements SimpleDataStorage {
	private Map<Id, AttachedData> api_attachedDataMap = new HashMap<>();

	@Inject(at = @At("RETURN"), method = "copy")
	private void api_copyData(CallbackInfoReturnable<ItemInstance> info) {
		DataManager.ITEM_INSTANCE.copyData((ItemInstance) (Object) this, info.getReturnValue());
	}

	@Inject(at = @At("RETURN"), method = "toTag")
	private void api_addData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
		tag.put("moddedData", this.getModdedTag());
	}

	@Inject(at = @At("RETURN"), method = "split")
	private void api_copySplitData(int countToTake, CallbackInfoReturnable<ItemInstance> info) {
		DataManager.ITEM_INSTANCE.copyData((ItemInstance) (Object) this, info.getReturnValue());
	}

	@Inject(at = @At("RETURN"), method = "fromTag")
	private void api_readData(CompoundTag tag, CallbackInfo info) {
		if (tag.containsKey("moddedData")) {
			CompoundTag data = tag.getCompoundTag("moddedData");

			// Load Data
			DataManager.ITEM_INSTANCE.loadData((ItemInstance) (Object) this, data);
		}
	}

	@Override
	public Map<Id, AttachedData> getRawAttachedDataMap() {
		return this.api_attachedDataMap;
	}	
}
