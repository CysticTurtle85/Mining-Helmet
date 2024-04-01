package net.cystic.mininghelmet;

import net.cystic.mininghelmet.custom.MiningHelmetItem;
import net.cystic.mininghelmet.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;
import software.bernie.geckolib.core.object.Color;

public class MiningHelmetClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.ITEM.register(
                (stack, layer) -> layer == 1 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack),
                ModItems.MINING_HELMET
        );

    }
}
