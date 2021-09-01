package paulevs.corelib.registry;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.halotroop.fabric.api.registry.Identifier;
import com.halotroop.fabric.api.registry.Registries;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import paulevs.corelib.model.Model;

public class ModelRegistry {
	private static final Map<Identifier, Map<Integer, Model>> BLOCK_REGISTRY = Maps.newHashMap();
	private static final Map<Identifier, Map<Integer, Model>> ITEM_REGISTRY = Maps.newHashMap();

	public static void addBlockModel(Block block, Model BlockModel) {
		Map<Integer, Model> models = Maps.newHashMap();
		models.put(0, BlockModel);
		BLOCK_REGISTRY.put(Registries.BLOCK.getId(block), models);
	}

	public static void addBlockModel(Block block, int meta, Model BlockModel) {
		Identifier id = Registries.BLOCK.getId(block);
		Map<Integer, Model> models = BLOCK_REGISTRY.computeIfAbsent(id, k -> Maps.newHashMap());
		models.put(meta, BlockModel);
	}

	public static Model getBlockModel(Block block, int meta) {
		Map<Integer, Model> models = BLOCK_REGISTRY.get(Registries.BLOCK.getId(block));
		return models == null ? null : models.get(meta);
	}

	public static void addItemModel(Item item, Model itemModel) {
		Map<Integer, Model> models = Maps.newHashMap();
		models.put(0, itemModel);
		ITEM_REGISTRY.put(Registries.ITEM.getId(item), models);
	}

	public static void addItemModel(Item item, int meta, Model itemModel) {
		Identifier id = Registries.ITEM.getId(item);
		Map<Integer, Model> models = ITEM_REGISTRY.computeIfAbsent(id, k -> Maps.newHashMap());
		models.put(meta, itemModel);
	}

	public static Model getItemModel(Item item, int meta) {
		Map<Integer, Model> models = ITEM_REGISTRY.get(Registries.ITEM.getId(item));
		return models == null ? null : models.get(meta);
	}

	public static List<Model> getBlockModels() {
		List<Model> list = Lists.newArrayList();
		BLOCK_REGISTRY.forEach((id, models) -> models.forEach((meta, model) -> list.add(model)));
		return list;
	}

	public static List<Model> getItemModels() {
		List<Model> list = Lists.newArrayList();
		ITEM_REGISTRY.forEach((id, models) -> models.forEach((meta, model) -> list.add(model)));
		return list;
	}
}