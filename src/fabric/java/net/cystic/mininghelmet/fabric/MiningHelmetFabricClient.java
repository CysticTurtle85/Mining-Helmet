package net.cystic.mininghelmet.fabric;

//#if MC < 1.21.4
import net.cystic.mininghelmet.MiningHelmet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

/** Tints the leather layer of the inventory icon. From 1.21.4 this is done by assets/mininghelmet/items/mining_helmet.json. */
public class MiningHelmetFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : MiningHelmet.dyeColor(stack), MiningHelmet.item.get());
    }
}
//#endif
