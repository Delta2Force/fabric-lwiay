package me.delta2force.lwiay.items;

import java.util.HashMap;

import me.delta2force.lwiay.LwiayMod;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LwiayItems {
	private static HashMap<Item, String> itemRegistry = new HashMap<>();
	
	public static final Item LETTER_L = genericItem();
	public static final Item LETTER_W = genericItem();
	public static final Item LETTER_I = genericItem();
	public static final Item LETTER_A = genericItem();
	public static final Item LETTER_Y = genericItem();
	public static final Item LWIAY = new LwiayItem(lwiayItemSettings());
	public static final Item LWIAY_REMOTE = new LwiayRemote(lwiayItemSettings());
	
	private static Item genericItem() {
		return new Item(lwiayItemSettings());
	}
	
	private static Item.Settings lwiayItemSettings(){
		return new Item.Settings().group(LwiayMod.LWIAY_GROUP);
	}
	
	public static void initialize() {
		addItemToRegistry(LETTER_L, "letter_l");
		addItemToRegistry(LETTER_W, "letter_w");
		addItemToRegistry(LETTER_I, "letter_i");
		addItemToRegistry(LETTER_A, "letter_a");
		addItemToRegistry(LETTER_Y, "letter_y");
		addItemToRegistry(LWIAY, "lwiay");
		addItemToRegistry(LWIAY_REMOTE, "lwiay_remote");
	}
	
	protected static void addItemToRegistry(Item item, String name) {
		itemRegistry.put(item, name);
	}
	
	public static void registerItemsInMinecraft() {
		for(Item i : itemRegistry.keySet()) {
			Registry.register(Registry.ITEM, new Identifier("lwiay", itemRegistry.get(i)), i);
		}
	}
}
