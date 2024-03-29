package net.cystic.mininghelmet.custom;


import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.MineshaftStructure;
import net.minecraft.world.gen.structure.Structure;

public class ModLootTables {
    private static final Identifier CHEST_LOOT_TABLE_ID = Blocks.CHEST.getLootTableId();
    private static final Identifier MINECART_CHEST_LOOT_TABLE_ID = EntityType.CHEST_MINECART.getLootTableId();

    public static void registerLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && (CHEST_LOOT_TABLE_ID.equals(id) || MINECART_CHEST_LOOT_TABLE_ID.equals(id))) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.MINING_HELMET));
                MiningHelmet.LOGGER.info("id is: " + id);
            }
        });
    }
}