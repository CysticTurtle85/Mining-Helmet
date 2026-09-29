package net.cystic.mininghelmet.fabric;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.MiningHelmetItem;
import net.fabricmc.api.ModInitializer;
//#if MC >= 26.1
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
//#else
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//#endif
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
//#if MC >= 1.21.2
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
//#elif MC >= 1.20.5
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
//#endif

public class MiningHelmetFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        //#if MC >= 1.21.2
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, MiningHelmet.id("mining_helmet"));
        Item helmet = Registry.register(BuiltInRegistries.ITEM, key, new MiningHelmetItem(new Item.Properties().setId(key)));
        //#elif MC >= 1.20.5
        Holder<ArmorMaterial> material = Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, MiningHelmet.id("mining_helmet"), MiningHelmet.createMaterial());
        Item helmet = Registry.register(BuiltInRegistries.ITEM, MiningHelmet.id("mining_helmet"), new MiningHelmetItem(material, new Item.Properties()));
        //#else
        Item helmet = Registry.register(BuiltInRegistries.ITEM, MiningHelmet.id("mining_helmet"), new MiningHelmetItem(new Item.Properties()));
        //#endif
        MiningHelmet.item = () -> helmet;

        //#if MC >= 26.1
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> output.insertAfter(Items.TURTLE_HELMET, helmet));
        //#else
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.addAfter(Items.TURTLE_HELMET, helmet));
        //#endif
    }
}
