package net.cystic.mininghelmet;

import net.cystic.mininghelmet.client.MiningHelmetRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.util.GeckoLibUtil;
import java.util.function.Consumer;
//#if MC >= 1.21.5
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
//#elif MC >= 1.21.2
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorType;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
//#elif MC >= 1.20.5
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
//#else
import net.minecraft.world.item.DyeableArmorItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
//#endif
//#if FABRIC && MC < 1.20.5
import software.bernie.geckolib.animatable.client.RenderProvider;
import java.util.function.Supplier;
//#endif
//#if FORGE
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
//#endif

public class MiningHelmetItem extends
        //#if MC >= 1.21.5
        Item
        //#elif MC >= 1.20.5
        ArmorItem
        //#else
        DyeableArmorItem
        //#endif
        implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    //#if FABRIC && MC < 1.20.5
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    //#endif

    //#if MC >= 1.21.5
    public MiningHelmetItem(Properties properties) {
        super(properties.humanoidArmor(MiningHelmet.MATERIAL, ArmorType.HELMET));
    }
    //#elif MC >= 1.21.2
    public MiningHelmetItem(Properties properties) {
        super(MiningHelmet.MATERIAL, ArmorType.HELMET, properties);
    }
    //#elif MC >= 1.20.5
    public MiningHelmetItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, Type.HELMET, properties.durability(Type.HELMET.getDurability(15)));
    }
    //#else
    public MiningHelmetItem(Properties properties) {
        super(MiningHelmet.MATERIAL, Type.HELMET, properties);
    }
    //#endif

    // The model is static, so there are no animation controllers to register.
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // Hands GeckoLib the renderer that draws the 3D helmet in place of the vanilla armour model.
    // The renderer is only created on the client, the first time the helmet is drawn.
    //#if MC >= 1.20.5
    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            //#if MC >= 1.21.5
            private MiningHelmetRenderer<?> renderer;
            //#else
            private MiningHelmetRenderer renderer;
            //#endif

            @Override
            //#if MC >= 1.21.10
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack stack, EquipmentSlot slot) {
            //#elif MC >= 1.21.5
            public <S extends HumanoidRenderState> GeoArmorRenderer<?, ?> getGeoArmorRenderer(@Nullable S renderState, ItemStack stack, EquipmentSlot slot, EquipmentClientInfo.LayerType type, @Nullable HumanoidModel<S> original) {
            //#elif MC >= 1.21.2
            public <E extends LivingEntity, S extends HumanoidRenderState> HumanoidModel<?> getGeoArmorRenderer(@Nullable E entity, ItemStack stack, EquipmentSlot slot, EquipmentClientInfo.LayerType type, HumanoidModel<S> original) {
            //#else
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T entity, ItemStack stack, @Nullable EquipmentSlot slot, @Nullable HumanoidModel<T> original) {
            //#endif
                if (this.renderer == null)
                    //#if MC >= 1.21.5
                    this.renderer = new MiningHelmetRenderer<>();
                    //#else
                    this.renderer = new MiningHelmetRenderer();
                    //#endif
                return this.renderer;
            }
        });
    }
    //#elif FABRIC
    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private MiningHelmetRenderer renderer;

            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<LivingEntity> original) {
                if (this.renderer == null)
                    this.renderer = new MiningHelmetRenderer();
                this.renderer.prepForRender(entity, stack, slot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
    //#elif FORGE
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private MiningHelmetRenderer renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new MiningHelmetRenderer();
                this.renderer.prepForRender(entity, stack, slot, original);
                return this.renderer;
            }
        });
    }
    //#endif
}
