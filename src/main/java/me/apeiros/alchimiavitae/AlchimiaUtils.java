package me.apeiros.alchimiavitae;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.groups.MultiGroup;
import io.github.mooy1.infinitylib.groups.SubGroup;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import lombok.experimental.UtilityClass;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Holds utility classes and methods of {@link AlchimiaVitae}
 */
@UtilityClass
public class AlchimiaUtils {

    // {{{ Recipe types
    /**
     * Holds {@link AlchimiaVitae}'s {@link RecipeType}s
     */
    @UtilityClass
    public class RecipeTypes {

        public static final RecipeType SOUL_COLLECTOR = new RecipeType(
                AbstractAddon.createKey("soul_collector_type"), AlchimiaItems.SOUL_COLLECTOR,
                "", "&b&o使用灵魂收割者收集");

        public static final RecipeType PLANT_INFUSION_CHAMBER = new RecipeType(
                AbstractAddon.createKey("plant_infusion_chamber_type"), AlchimiaItems.PLANT_INFUSION_CHAMBER,
                "", "&b&o使用植物注入仓注入");

        public static final RecipeType EXP_CRYSTALLIZER = new RecipeType(
                AbstractAddon.createKey("exp_crystallizer_type"), AlchimiaItems.EXP_CRYSTALLIZER,
                "", "&b&o使用经验结晶器纯化");

        public static final RecipeType DIVINE_ALTAR = new RecipeType(
                AbstractAddon.createKey("divine_altar_type"), AlchimiaItems.DIVINE_ALTAR,
                "", "&b&o使用神圣祭坛进行合成");

        public static final RecipeType COSMIC_CAULDRON = new RecipeType(
                AbstractAddon.createKey("cosmic_cauldron_type"), AlchimiaItems.COSMIC_CAULDRON,
                "", "&b&o使用华丽炼药锅进行酿造");

        public static final RecipeType INFUSION_ALTAR = new RecipeType(
                AbstractAddon.createKey("infusion_altar_type"), AlchimiaItems.ALTAR_OF_INFUSION,
                "", "&b&o使用注入祭坛注入魔法");

    }
    // }}}

    // {{{ Item groups
    /**
     * Holds {@link AlchimiaVitae}'s {@link ItemGroup}s
     */
    @UtilityClass
    public class ItemGroups {

        public static final ItemGroup GENERAL = new SubGroup(
                "av_general",
                new CustomItemStack(Material.ENCHANTED_BOOK, "&6炼金术自传 &7- &2起源"));

        public static final ItemGroup ALTAR_RECIPES = new SubGroup(
                "av_altar_recipes",
                new CustomItemStack(Material.ENCHANTING_TABLE, "&6炼金术自传 &7- &5转化"));

        public static final ItemGroup INFUSIONS = new SubGroup(
                "av_infusions",
                new CustomItemStack(Material.NETHER_STAR, "&6炼金术自传 &7- &d注入"));

        public static final ItemGroup MAIN = new MultiGroup(
                "alchimia_vitae",
                new CustomItemStack(Material.TOTEM_OF_UNDYING, "&6炼金术自传"),
                GENERAL, ALTAR_RECIPES, INFUSIONS);

    }
    // }}}

    // {{{ MiniMessage
    private static final MiniMessage MM = MiniMessage.builder()
            .tags(TagResolver.builder()
                    .resolver(StandardTags.color())
                    .resolver(StandardTags.decorations())
                    .resolver(StandardTags.gradient())
                    .resolver(StandardTags.reset())
                    .build())
            .build();

    // Serializer
    private static final LegacyComponentSerializer LCS = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    public static String format(String s) {
        return LCS.serialize(MM.deserialize(s));
    }

    public static String itemType(String type) {
        return LCS.serialize(MM.deserialize("<blue>" + type + "<blue> (<italic>AlchimiaVitae<blue>)"));
    }
    // }}}

    // {{{ Methods for making potions
    public static SlimefunItemStack makePotion(
            String id,
            String name,
            Color color,
            Collection<PotionEffect> effects,
            boolean splash,
            String... lore) {

        ItemStack potion = new ItemStack(splash ? Material.SPLASH_POTION : Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.setDisplayName(name);
        potionMeta.setColor(color);

        for (PotionEffect e : effects) {
            potionMeta.addCustomEffect(e, true);
        }

        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion, name, lore);
    }

    public static SlimefunItemStack makePotion(
            String id,
            String name,
            Color color,
            Map<PotionEffectType, int[]> effects,
            boolean splash,
            String... lore) {

        List<PotionEffect> new_effects = new LinkedList<>();

        for (Map.Entry<PotionEffectType, int[]> e : effects.entrySet()) {
            new_effects.add(new PotionEffect(e.getKey(), e.getValue()[0], e.getValue()[1], true, true, true));
        }

        return makePotion(id, name, color, new_effects, splash, lore);
    }
    // }}}

    // {{{ Other utility methods
    public static boolean equalsAny(Object base, Object... comparisons) {
        for (Object comparison : comparisons) {
            if (base.equals(comparison)) return true;
        }
        return false;
    }
    // }}}
}
