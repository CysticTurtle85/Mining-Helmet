package net.cystic.mininghelmet.item;

import net.cystic.mininghelmet.MiningHelmet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MINING_HELMET_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MiningHelmet.MOD_ID, "mining_helmet"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mining_helmet"))
                    .icon(() -> new ItemStack(ModItems.MINING_HELMET))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.MINING_HELMET);
                    }).build());

    public static void registerItemGroups() {

    }
}
