package net.cystic.mininghelmet.neoforge;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.MiningHelmetItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
//#if MC < 1.21.2
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
//#endif
//#if MC < 1.21.4 || SODIUM_DYNAMIC_LIGHTS
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
//#endif
//#if MC < 1.21.4
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
//#endif
//#if SODIUM_DYNAMIC_LIGHTS
import net.cystic.mininghelmet.client.SodiumDynamicLightsFix;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
//#endif

@Mod(MiningHelmet.MOD_ID)
public class MiningHelmetNeoForge {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MiningHelmet.MOD_ID);
    //#if MC >= 1.21.2
    private static final DeferredItem<MiningHelmetItem> MINING_HELMET = ITEMS.registerItem("mining_helmet", MiningHelmetItem::new);
    //#else
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MiningHelmet.MOD_ID);
    private static final DeferredHolder<ArmorMaterial, ArmorMaterial> MATERIAL = ARMOR_MATERIALS.register("mining_helmet", MiningHelmet::createMaterial);
    private static final DeferredItem<MiningHelmetItem> MINING_HELMET = ITEMS.register("mining_helmet", () -> new MiningHelmetItem(MATERIAL, new Item.Properties()));
    //#endif

    public MiningHelmetNeoForge(IEventBus modBus) {
        MiningHelmet.item = MINING_HELMET::get;
        //#if MC < 1.21.2
        ARMOR_MATERIALS.register(modBus);
        //#endif
        ITEMS.register(modBus);
        modBus.addListener(MiningHelmetNeoForge::addToCreativeTab);
        //#if MC < 1.21.4 || SODIUM_DYNAMIC_LIGHTS
        if (FMLEnvironment.dist == Dist.CLIENT)
            Client.register(modBus);
        //#endif
    }

    private static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
            event.insertAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(MINING_HELMET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    //#if MC < 1.21.4 || SODIUM_DYNAMIC_LIGHTS
    private static final class Client {
        static void register(IEventBus modBus) {
            //#if MC < 1.21.4
            modBus.addListener(Client::registerItemColors);
            //#endif
            //#if SODIUM_DYNAMIC_LIGHTS
            modBus.addListener(Client::addReloadListeners);
            //#endif
        }

        //#if MC < 1.21.4
        /** Tints the leather layer of the inventory icon. From 1.21.4 this is done by assets/mininghelmet/items/mining_helmet.json. */
        static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : MiningHelmet.dyeColor(stack), MINING_HELMET.get());
        }
        //#endif

        //#if SODIUM_DYNAMIC_LIGHTS
        static void addReloadListeners(AddClientReloadListenersEvent event) {
            if (SodiumDynamicLightsFix.isNeeded())
                event.addListener(MiningHelmet.id("sodium_dynamic_lights_fix"), new SodiumDynamicLightsFix());
        }
        //#endif
    }
    //#endif
}
