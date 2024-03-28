package net.cystic.mininghelmet.client;

import net.cystic.mininghelmet.custom.MiningHelmetItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MiningHelmetRenderer extends GeoArmorRenderer<MiningHelmetItem> {
    public MiningHelmetRenderer() {
        super(new MiningHelmetModel());
    }
}
