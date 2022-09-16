package com.gegenphase.battleroyale.util.items;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class Items
{
    /*
     * Klassenvariablen
     */
    public static final String ITEM_TYPE_DENSITY = "Dichtheit";
    public static final String ITEM_TYPE_SPAWNCHANCE = "Spawnwahrscheinlichkeit";

    private static ItemStack makeItem(Material m, int amount, short data, String name, List<String> lore)
    {
        ItemStack i = new ItemStack(m, amount, data);

        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);

        i.setItemMeta(im);
        return i;
    }

    /**
     * Bekomme einen LootContainer Wand, mit dem man LootContainer hinzufügen kann oder entfernen kann.
     *
     * @return Den Gegenstand als Itemstack.
     */
    public static ItemStack lootcontainerWand()
    {
        ItemStack i = new ItemStack(Material.STICK, 1);
        ItemMeta im = i.getItemMeta();

        im.setDisplayName("§3Lootcontainererstellen-/entfernen-Tool");

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§fRechtsklick, §bum ein LootContainer festzulegen.");
        lore.add("§fLinksklick, §bum ein LootContainer zu entfernen.");
        lore.add("§fFallen lassen, §balle LootContainer zu setzen.");
        im.setLore(lore);

        im.addEnchant(Enchantment.DURABILITY, 1, false);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        i.setItemMeta(im);
        return i;
    }

    /**
     * Bekomme einen Platzhalter.
     *
     * @param m Das Material des Platzhalters.
     * @return Der Platzhalter.
     */
    public static ItemStack placeholder(Material m)
    {
        return makeItem(m, 1, (short) 0, "§r", new ArrayList<String>());
    }

    /**
     * Ein Dichtheit-Icon.
     *
     * @param density Die Dichtheit.
     * @return Das Icon als ItemStack.
     */
    public static ItemStack densityIcon(double density)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktueller Wert: §e" + density);

        return makeItem(Material.SNOWBALL, 1, (short) 0, "§e§lDichtheit", lore);
    }

    /**
     * Ein Spawnchancen-Icon.
     *
     * @param pctSpawn Die Spawnchance.
     * @return Das Icon als ItemStack.
     */
    public static ItemStack pctSpawnIcon(double pctSpawn)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktueller Wert: §e" + pctSpawn);

        return makeItem(Material.SLIME_BALL, 1, (short) 0, "§e§lSpawnchance", lore);
    }

    /**
     * Ein Lootklassen-Icon.
     *
     * @param currentLootclass Die aktuell ausgewählte Lootklasse.
     * @return Das Icon als ItemStack.
     */
    public static ItemStack lootClassIcon(String currentLootclass)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktuelle Lootklasse: §e" + currentLootclass);

        return makeItem(Material.ENDER_EYE, 1, (short) 0, "§e§lLootklasse", lore);
    }

    /**
     * Knopf, der gebraucht wird, um einen LootContainer als Standardwert zu speichern.
     *
     * @param l Den LootContainer.
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack saveDefaultPresetButton(LootContainer l)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktuelle Lootklasse: §e" + l.getLootClass());
        lore.add("§6Aktuelle Dichtheit: §e" + l.getDensity());
        lore.add("§6Aktuelle Spawnwahrscheinlichkeit: §e" + l.getPctSpawn());
        lore.add("§6Leerspawn? §e" + l.getAllowSetBlockWhenEmpty());

        return makeItem(Material.CHEST, 1, (short) 0, "§e§lAls Standard speichern", lore);
    }

    /**
     * Knopf, der gebraucht wird, um die Standardwerte eines LC zu laden.
     *
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack loadDefaultPresetButton()
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Standard Lootklasse: §e" + MainConfig.DEFAULT_LOOTCLASS);
        lore.add("§6Aktuelle Dichtheit: §e" + MainConfig.DEFAULT_DENSITY);
        lore.add("§6Aktuelle Spawnwahrscheinlichkeit: §e" + MainConfig.DEFAULT_PCT_SPAWN);
        lore.add("§6Leerspawn? §e" + MainConfig.DEFAULT_SPAWN_WHEN_EMPTY);
        lore.add("");
        lore.add("§4Warnung §cIrreversibel!");

        return makeItem(Material.CRAFTING_TABLE, 1, (short) 0, "§e§lStandardwerte laden", lore);
    }

    /**
     * Knopf, der gebraucht wird, um einen LC als Voreinstellung 1 zu speichern.
     *
     * @param l Der LootContainer.
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack saveCustom1PresetButton(LootContainer l)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktuelle Lootklasse: §e" + l.getLootClass());
        lore.add("§6Aktuelle Dichtheit: §e" + l.getDensity());
        lore.add("§6Aktuelle Spawnwahrscheinlichkeit: §e" + l.getPctSpawn());
        lore.add("§6Leerspawn? §e" + l.getAllowSetBlockWhenEmpty());

        return makeItem(Material.BARREL, 1, (short) 0, "§e§lAls Voreinstellung 1 speichern", lore);
    }

    /**
     * Knopf, der gebraucht wird, um einen LC aus der Voreinstellung 1 zu laden.
     *
     * @return Der Knopf als ItemStack.
     */
    public static ItemStack loadCustom1PresetButton()
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Standard Lootklasse: §e" + MainConfig.CUSTOM1_LOOTCLASS);
        lore.add("§6Aktuelle Dichtheit: §e" + MainConfig.CUSTOM1_DENSITY);
        lore.add("§6Aktuelle Spawnwahrscheinlichkeit: §e" + MainConfig.CUSTOM1_PCT_SPAWN);
        lore.add("§6Leerspawn? §e" + MainConfig.CUSTOM1_SPAWN_WHEN_EMPTY);
        lore.add("");
        lore.add("§4Warnung §cIrreversibel!");

        return makeItem(Material.SMITHING_TABLE, 1, (short) 0, "§e§lVoreinstellung 1 laden", lore);
    }

    /**
     * Knopf, der gebraucht wird, um einen LC als Voreinstellung 2 zu speichern.
     *
     * @param l Der LootContainer.
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack saveCustom2PresetButton(LootContainer l)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktuelle Lootklasse: §e" + l.getLootClass());
        lore.add("§6Aktuelle Dichtheit: §e" + l.getDensity());
        lore.add("§6Aktuelle Spawnwahrscheinlichkeit: §e" + l.getPctSpawn());
        lore.add("§6Leerspawn? §e" + l.getAllowSetBlockWhenEmpty());

        return makeItem(Material.DISPENSER, 1, (short) 0, "§e§lAls Voreinstellung 2 speichern", lore);
    }

    /**
     * Knopf, der gebraucht wird, um einen LC aus der Voreinstellung 2 zu laden.
     *
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack loadCustom2PresetButton()
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Standard Lootklasse: §e" + MainConfig.CUSTOM2_LOOTCLASS);
        lore.add("§6Aktuelle Dichtheit: §e" + MainConfig.CUSTOM2_DENSITY);
        lore.add("§6Aktuelle Spawnwahrscheinlichkeit: §e" + MainConfig.CUSTOM2_PCT_SPAWN);
        lore.add("§6Leerspawn? §e" + MainConfig.CUSTOM2_SPAWN_WHEN_EMPTY);
        lore.add("");
        lore.add("§4Warnung §cIrreversibel!");

        return makeItem(Material.FLETCHING_TABLE, 1, (short) 0, "§e§lVoreinstellung 2 laden", lore);
    }

    /**
     * Ein Leerspawn-Knopf.
     *
     * @param emptySpawn Der aktuelle Leerspawnzustand.
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack emptySpawn(boolean emptySpawn)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktueller Wert: §e" + emptySpawn);

        return makeItem(Material.ENDER_PEARL, 1, (short) 0, "§e§lErzeuge Block, obwohl leerer Container?", lore);
    }

    /**
     * Ein +/- Schritt-Knopf.
     *
     * @param type    Der Typ.
     * @param step    Die Schrittweite.
     * @param current Der aktuelle Wert.
     * @return Den Knopf als ItemStack.
     */
    public static ItemStack valueButton(String type, String step, double current)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktueller Wert: §e" + current);
        lore.add(" ");
        lore.add("§6Linksklick §e= +" + step);
        lore.add("§6Rechtsklick §e= -" + step);

        return makeItem(Material.FIREWORK_STAR, 1, (short) 0, "§6" + type + " §e+/- " + step, lore);
    }

    /**
     * Ein Knopf, der in Kombination mit der Leerspawn Funktion steht.
     *
     * @param current Der aktuelle Zustand.
     * @return Der Knopf, der das obige Szenario repräsentiert.
     */
    public static ItemStack toggleEmptySpawnButton(boolean current)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktueller Wert: §e" + current);

        return makeItem(Material.FIREWORK_STAR, 1, (short) 0, "§6Leerspawn", lore);
    }

    /**
     * Ein Knopf, der durch die Lootklassen geht.
     *
     * @param currentClass Die aktuelle Klasse.
     * @return Der Knopf, der das obige Szenario repräsentiert.
     */
    public static ItemStack cycleThroughLootClassButton(String currentClass)
    {
        List<String> lore = new ArrayList<>();

        lore.add(" ");
        lore.add("§6Aktuelle Klasse: §e" + currentClass);

        return makeItem(Material.FIREWORK_STAR, 1, (short) 0, "§6Lootklasse", lore);
    }
}
