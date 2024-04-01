package net.cystic.mininghelmet;

import net.cystic.mininghelmet.custom.ModLootTables;
import net.cystic.mininghelmet.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiningHelmet implements ModInitializer {
	public static final String MOD_ID = "mininghelmet";
public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		//ModLootTables.registerLootTables();
	}
}