package net.cystic.mininghelmet.forge;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.MiningHelmetItem;
import net.cystic.mininghelmet.client.SodiumDynamicLightsFix;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(MiningHelmet.MOD_ID)
public class MiningHelmetForge {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MiningHelmet.MOD_ID);
    private static final RegistryObject<Item> MINING_HELMET = ITEMS.register("mining_helmet", () -> new MiningHelmetItem(new Item.Properties()));

    public MiningHelmetForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MiningHelmet.item = MINING_HELMET;
        ITEMS.register(modBus);
        modBus.addListener(MiningHelmetForge::addToCreativeTab);
        if (FMLEnvironment.dist == Dist.CLIENT)
            Client.register(modBus);
    }

    private static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
            event.getEntries().putAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(MINING_HELMET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static final class Client {
        static void register(IEventBus modBus) {
            modBus.addListener(Client::registerItemColors);
            modBus.addListener(Client::registerReloadListeners);
        }

        /** Tints the leather layer of the inventory icon. */
        static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : MiningHelmet.dyeColor(stack), MINING_HELMET.get());
        }

        static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
            if (SodiumDynamicLightsFix.isNeeded())
                event.registerReloadListener(new SodiumDynamicLightsFix());
        }
    }
}
