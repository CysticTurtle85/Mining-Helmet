package net.cystic.mininghelmet.client;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.custom.MiningHelmetItem;
import net.cystic.mininghelmet.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.item.DyeableItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.DyeableGeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MiningHelmetRenderer extends DyeableGeoArmorRenderer<MiningHelmetItem> {
    public MiningHelmetRenderer() {
        super(new MiningHelmetModel());
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    protected boolean isBoneDyeable(GeoBone bone) {
        return bone.getName().equals("Leather");
    }

    public static ItemColors create(BlockColors blockColors){
        ItemColors itemColors = new ItemColors();
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((MiningHelmetItem) stack.getItem()).getColor(stack), ModItems.MINING_HELMET);
        return itemColors;
    }

    @Override
    protected @NotNull Color getColorForBone(GeoBone bone) {
        // Get the RGB color value
        int rgbColor = ColorProviderRegistry.ITEM.get(ModItems.MINING_HELMET).getColor(getCurrentStack(), 0);

        // Prepend "ff" before the RGB color value to represent the alpha channel
        int argbColor = 0xFF000000 | (rgbColor & 0xFFFFFF);

        // Return the ARGB integer value
        return new Color(argbColor);
    }
}
