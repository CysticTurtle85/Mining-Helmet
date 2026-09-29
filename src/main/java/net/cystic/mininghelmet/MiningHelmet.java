package net.cystic.mininghelmet;

//#if MC >= 1.21.11
import net.minecraft.resources.Identifier;
//#else
import net.minecraft.resources.ResourceLocation;
//#endif
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//#if MC >= 1.21.2
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import java.util.Map;
//#elif MC >= 1.20.5
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.List;
import java.util.Map;
//#else
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
//#endif
import java.util.function.Supplier;

public final class MiningHelmet {
    public static final String MOD_ID = "mininghelmet";

    /** Colour of an undyed helmet: the same leather brown as vanilla leather armour. */
    public static final int DEFAULT_COLOR = 0xFFA06540;

    /** Set by the loader entrypoint once the item is registered. */
    public static Supplier<Item> item;

    // Same protection, durability and enchantability as an iron helmet.
    //#if MC >= 1.21.2
    public static final ArmorMaterial MATERIAL = new ArmorMaterial(
            15,
            Map.of(ArmorType.HELMET, 2, ArmorType.CHESTPLATE, 6, ArmorType.LEGGINGS, 5, ArmorType.BOOTS, 2, ArmorType.BODY, 5),
            9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F,
            TagKey.create(Registries.ITEM, id("repairs_mining_helmet")),
            ResourceKey.create(EquipmentAssets.ROOT_ID, id("mining_helmet")));
    //#elif MC >= 1.20.5
    public static ArmorMaterial createMaterial() {
        return new ArmorMaterial(
                Map.of(ArmorItem.Type.HELMET, 2, ArmorItem.Type.CHESTPLATE, 6, ArmorItem.Type.LEGGINGS, 5, ArmorItem.Type.BOOTS, 2, ArmorItem.Type.BODY, 5),
                9, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.IRON_INGOT, Items.TORCH),
                List.of(new ArmorMaterial.Layer(id("mining_helmet"))), 0.0F, 0.0F);
    }
    //#else
    public static final ArmorMaterial MATERIAL = new ArmorMaterial() {
        // Indexed by ArmorItem.Type: helmet, chestplate, leggings, boots
        private static final int[] BASE_DURABILITY = {11, 16, 15, 13};
        private static final int[] DEFENSE = {2, 6, 5, 2};

        @Override public int getDurabilityForType(ArmorItem.Type type) { return BASE_DURABILITY[type.ordinal()] * 15; }
        @Override public int getDefenseForType(ArmorItem.Type type) { return DEFENSE[type.ordinal()]; }
        @Override public int getEnchantmentValue() { return 9; }
        @Override public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_IRON; }
        @Override public Ingredient getRepairIngredient() { return Ingredient.of(Items.IRON_INGOT, Items.TORCH); }
        @Override public String getName() { return MOD_ID + ":mining_helmet"; }
        @Override public float getToughness() { return 0.0F; }
        @Override public float getKnockbackResistance() { return 0.0F; }
    };
    //#endif

    private MiningHelmet() {}

    /** The helmet's dye colour as opaque ARGB, or {@link #DEFAULT_COLOR} if it hasn't been dyed. */
    public static int dyeColor(ItemStack stack) {
        //#if MC >= 1.20.5
        return DyedItemColor.getOrDefault(stack, DEFAULT_COLOR);
        //#else
        return 0xFF000000 | ((DyeableLeatherItem) stack.getItem()).getColor(stack);
        //#endif
    }

    //#if MC >= 1.21.11
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
    //#elif MC >= 1.21
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
    //#else
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
    //#endif
}
