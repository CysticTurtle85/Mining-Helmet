package net.cystic.mininghelmet.client;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.MiningHelmetItem;
//#if MC >= 1.21.11
import net.minecraft.resources.Identifier;
//#else
import net.minecraft.resources.ResourceLocation;
//#endif
import software.bernie.geckolib.model.GeoModel;
//#if MC >= 1.21.5
import software.bernie.geckolib.renderer.base.GeoRenderState;
//#elif MC >= 1.21.2
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoRenderer;
//#endif

public class MiningHelmetModel extends GeoModel<MiningHelmetItem> {
    //#if MC >= 1.21.11
    private static final Identifier MODEL = MiningHelmet.id("mining_helmet");
    private static final Identifier ANIMATION = MiningHelmet.id("mining_helmet");
    private static final Identifier TEXTURE = MiningHelmet.id("textures/models/armor/mining_helmet_layer_1.png");
    //#elif MC >= 1.21.5
    private static final ResourceLocation MODEL = MiningHelmet.id("mining_helmet");
    private static final ResourceLocation ANIMATION = MiningHelmet.id("mining_helmet");
    private static final ResourceLocation TEXTURE = MiningHelmet.id("textures/models/armor/mining_helmet_layer_1.png");
    //#else
    private static final ResourceLocation MODEL = MiningHelmet.id("geo/mining_helmet.geo.json");
    private static final ResourceLocation ANIMATION = MiningHelmet.id("animations/mining_helmet.animation.json");
    private static final ResourceLocation TEXTURE = MiningHelmet.id("textures/models/armor/mining_helmet_layer_1.png");
    //#endif

    //#if MC >= 1.21.11
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(MiningHelmetItem animatable) {
        return ANIMATION;
    }
    //#elif MC >= 1.21.5
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(MiningHelmetItem animatable) {
        return ANIMATION;
    }
    //#elif MC >= 1.21.2
    @Override
    public ResourceLocation getModelResource(MiningHelmetItem animatable, @Nullable GeoRenderer<MiningHelmetItem> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(MiningHelmetItem animatable, @Nullable GeoRenderer<MiningHelmetItem> renderer) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(MiningHelmetItem animatable) {
        return ANIMATION;
    }
    //#else
    @Override
    public ResourceLocation getModelResource(MiningHelmetItem animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(MiningHelmetItem animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(MiningHelmetItem animatable) {
        return ANIMATION;
    }
    //#endif
}
