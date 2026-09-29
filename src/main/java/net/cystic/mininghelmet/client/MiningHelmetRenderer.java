package net.cystic.mininghelmet.client;

import net.cystic.mininghelmet.MiningHelmet;
import net.cystic.mininghelmet.MiningHelmetItem;
//#if MC >= 1.21.5
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
//#endif
//#if MC >= 1.21.11
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.ARGB;
import software.bernie.geckolib.cache.model.GeoBone;
import software.bernie.geckolib.cache.model.cuboid.CuboidGeoBone;
import software.bernie.geckolib.cache.model.cuboid.GeoCube;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
//#else
import software.bernie.geckolib.cache.object.GeoBone;
//#endif
//#if MC >= 1.20.5
import software.bernie.geckolib.renderer.specialty.DyeableGeoArmorRenderer;
//#else
import software.bernie.geckolib.renderer.DyeableGeoArmorRenderer;
//#endif
//#if MC >= 1.21.5
//#elif MC >= 1.21.2
import software.bernie.geckolib.object.Color;
//#elif MC >= 1.20.5
import software.bernie.geckolib.util.Color;
//#else
import software.bernie.geckolib.core.object.Color;
//#endif

/** Draws the 3D helmet model, tinting only the leather strap with the helmet's dye colour. */
//#if MC >= 1.21.5
public class MiningHelmetRenderer<R extends HumanoidRenderState & GeoRenderState> extends DyeableGeoArmorRenderer<MiningHelmetItem, R> {
    private static final DataTicket<Integer> DYE_COLOR = DataTicket.create(MiningHelmet.MOD_ID + "_dye_color", Integer.class);
//#else
public class MiningHelmetRenderer extends DyeableGeoArmorRenderer<MiningHelmetItem> {
//#endif
    private static final String DYEABLE_BONE = "Leather";

    public MiningHelmetRenderer() {
        super(new MiningHelmetModel());
    }

    @Override
    protected boolean isBoneDyeable(GeoBone bone) {
        //#if MC >= 1.21.11
        return DYEABLE_BONE.equals(bone.name());
        //#else
        return DYEABLE_BONE.equals(bone.getName());
        //#endif
    }

    // GeckoLib 5 renders from a snapshot of the item (a render state), so the dye colour is copied into it here.
    //#if MC >= 1.21.10
    @Override
    public void addRenderData(MiningHelmetItem animatable, RenderData data, R renderState, float partialTick) {
        renderState.addGeckolibData(DYE_COLOR, MiningHelmet.dyeColor(data.itemStack()));
    }
    //#elif MC >= 1.21.5
    @Override
    public void addRenderData(MiningHelmetItem animatable, RenderData data, R renderState) {
        renderState.addGeckolibData(DYE_COLOR, MiningHelmet.dyeColor(data.itemStack()));
    }
    //#endif

    //#if MC >= 1.21.11
    @Override
    protected int getColorForBone(R renderState, GeoBone bone, int baseColour) {
        return renderState.getOrDefaultGeckolibData(DYE_COLOR, MiningHelmet.DEFAULT_COLOR);
    }

    // GeckoLib 5.4+ hides the dyeable bone and redraws it tinted, but its redraw goes through GeoBone.render, which
    // skips hidden bones, so the leather band never appears. Draw the bone's cubes directly instead. The pose
    // arrives positioned at the bone's pivot, while cube positions are relative to the model, so step back first.
    @Override
    protected void renderDyedBone(R renderState, PoseStack.Pose pose, GeoBone bone, RenderPassInfo<R> renderPassInfo,
                                  VertexConsumer vertexConsumer, int renderColor) {
        if (!(bone instanceof CuboidGeoBone cuboidBone))
            return;
        PoseStack poseStack = new PoseStack();
        poseStack.last().set(pose);
        bone.translateAwayFromPivotPoint(poseStack);
        int color = ARGB.multiply(renderColor, getColorForBone(renderState, bone, -1));
        for (GeoCube cube : cuboidBone.cubes) {
            cube.render(poseStack, vertexConsumer, renderPassInfo.packedLight(), renderPassInfo.packedOverlay(), color);
        }
    }
    //#elif MC >= 1.21.5
    @Override
    protected int getColorForBone(R renderState, GeoBone bone, int packedLight, int packedOverlay, int baseColour) {
        return renderState.getOrDefaultGeckolibData(DYE_COLOR, MiningHelmet.DEFAULT_COLOR);
    }
    //#else
    @Override
    protected Color getColorForBone(GeoBone bone) {
        return new Color(MiningHelmet.dyeColor(getCurrentStack()));
    }
    //#endif

    //#if MC >= 1.20.5 && MC < 1.21.5
    // GeckoLib would otherwise tint the whole model for items in the #minecraft:dyeable tag.
    @Override
    public Color getRenderColor(MiningHelmetItem animatable, float partialTick, int packedLight) {
        return Color.WHITE;
    }
    //#endif
}
