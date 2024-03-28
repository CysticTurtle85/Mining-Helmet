package net.cystic.mininghelmet.client;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.custom.MiningHelmetItem;
import net.cystic.mininghelmet.item.ModItems;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MiningHelmetModel extends GeoModel<MiningHelmetItem> {
    @Override
    public Identifier getModelResource(MiningHelmetItem animatable) {
        return new Identifier(MiningHelmet.MOD_ID, "geo/mining_helmet.geo.json");
    }

    @Override
    public Identifier getTextureResource(MiningHelmetItem animatable) {
        return new Identifier(MiningHelmet.MOD_ID, "textures/armour/mining_helmet.png");
    }

    @Override
    public Identifier getAnimationResource(MiningHelmetItem animatable) {
        return new Identifier(MiningHelmet.MOD_ID, "animations/mining_helmet.animation.json");
    }
}
