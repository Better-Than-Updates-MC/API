package io.github.minecraftcursedlegacy.impl.attacheddata;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

public interface DataStorage {
	CompoundTag getModdedTag();
	void putAttachedData(Id id, AttachedData data);
	AttachedData getAttachedData(Id id, Supplier<AttachedData> supplier);
	Set<Entry<Id,AttachedData>> getAllAttachedData();
}
