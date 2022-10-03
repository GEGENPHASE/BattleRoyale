package com.gegenphase.battleroyale.loot.lootclasses.services;

import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootClass;
import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootItem;
import com.gegenphase.battleroyale.util.messages.Messages;
import com.gegenphase.moreitems.startup.Startup;
import com.shampaggon.crackshot.CSUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootClassFileService
{
    /*
     * Feldvariablen
     */
    private final File _sourcefile;
    private final FileConfiguration _config;

    public LootClassFileService(File mainpath)
    {
        mainpath.mkdirs();

        _sourcefile = new File(mainpath, "lootclasses.yml");

        if (!_sourcefile.exists())
        {
            try
            {
                _sourcefile.createNewFile();
                setup();
            }
            catch (IOException e)
            {
                Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "Konnte die Datei 'lootclasses.yml' nicht erstellen. Ist der Zugriff auf die Datei gewährleistet?");
            }
        }

        _config = YamlConfiguration.loadConfiguration(_sourcefile);
    }

    /**
     * Ersetze den bestehenden Bestand durch dem in der Datei gespeicherten.
     *
     * @return Die HashMap mit allen neuen Klassen.
     */
    public Map<String, LootClass> load()
    {
        Map<String, LootClass> lootClasses = new HashMap<>();

        /*
         * Lade die Datei neu.
         */
        try
        {
            _config.load(_sourcefile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> Die Datei 'lootclasses.yml' ist nicht zugreifbar, nicht vorhanden oder falsch konfiguriert.");
            return lootClasses;
        }

        /*
         * Bekomme alle Lootklassennamen
         */
        Set<String> lootclassnames = _config.getKeys(false);

        for (String name : lootclassnames)
        {
            LootClass l = getLootClass(name);

            if (l != null)
            {
                lootClasses.put(name, l);
                continue;
            }

            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> Lootklasse '" + name + "' ist leer. Überspringe.");
        }


        /*
         * Random Lootklasse
         */
        LootClass random = new LootClass();

        for (LootClass currentLc : lootClasses.values())
        {
            for (LootItem currentLi : currentLc.getContents())
            {
                random.addLootItem(currentLi);
            }
        }
        lootClasses.put("random", random);

        return lootClasses;
    }

    private LootClass getLootClass(String name)
    {
        List<LootItem> lootItems = new ArrayList<>();
        List<String> rawLootItems = (List<String>) _config.getList(name);

        for (String rawLootItem : rawLootItems)
        {
            LootItem li = getLootItem(rawLootItem);
            if (li != null)
            {
                for (int i = 0; i < li.getWeight(); i++)
                {
                    lootItems.add(li);
                }
            }
        }

        if (lootItems.isEmpty())
        {
            return null;
        }

        return new LootClass(lootItems);
    }

    private LootItem getLootItem(final String rawLootItem)
    {
        String[] tags = rawLootItem.split(" ");
        // id:stone min:10 max:100 name:&eabc_def lore:&6abc#&7def#&5hij enchant:durability#3

        Material material = null;
        int min = 1;
        int max = 1;
        String name = "";
        List<String> lore = new ArrayList<>();
        List<String> enchantments = new ArrayList<>();
        short data = 0;
        int weight = 1;

        for (int i = 0; i < tags.length; i++)
        {
            String[] tagsplit = tags[i].split(":");

            if (tagsplit.length != 2)
            {
                Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! ItemTags dürfen maximal ein Doppelpunkt (:) enthalten. Überspringe fehlerhaften Datenblock. (" + rawLootItem + ")");
                continue;
            }

            /*
             * ID
             */
            if (tagsplit[0].equalsIgnoreCase("id"))
            {
                try
                {
                    material = Material.valueOf(tagsplit[1].toUpperCase());
                }
                catch (IllegalArgumentException e)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Eine ID konnte nicht gefunden werden. Rettung unmöglich. (" + rawLootItem + ")");
                    return null;
                }
            }

            /*
             * Min
             */
            if (tagsplit[0].equalsIgnoreCase("min"))
            {
                try
                {
                    min = Integer.parseInt(tagsplit[1]);
                }
                catch (NumberFormatException e)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Der Mindestwert ist ungültig, weil der Wert keine natürliche Zahl ist. Wert wurde auf 1 korrigiert. (" + rawLootItem + ")");
                }
            }

            /*
             * Max
             */
            if (tagsplit[0].equalsIgnoreCase("max"))
            {
                try
                {
                    max = Integer.parseInt(tagsplit[1]);
                }
                catch (NumberFormatException e)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Der Maximalwert ist ungültig, weil der Wert keine natürliche Zahl ist. (" + rawLootItem + ")");
                }
            }

            /*
             * Name
             */
            if (tagsplit[0].equalsIgnoreCase("name"))
            {
                name = convert(tagsplit[1]);
            }

            /*
             * Lore
             */
            if (tagsplit[0].equalsIgnoreCase("lore"))
            {
                String[] lines = tagsplit[1].split("#");

                for (int j = 0; j < lines.length; j++)
                {
                    lore.add(convert(lines[j]));
                }
            }

            /*
             * Enchant
             */
            if (tagsplit[0].equalsIgnoreCase("enchant"))
            {
                enchantments.add(tagsplit[1]);
            }

            /*
             * Data
             */
            if (tagsplit[0].equalsIgnoreCase("data"))
            {
                data = Short.parseShort(tagsplit[1]);
            }

            /*
             * Gewichtung
             */
            if (tagsplit[0].equalsIgnoreCase("weight"))
            {
                weight = Integer.parseInt(tagsplit[1]);
            }

            /*
             * CrackShot
             */
            if (tagsplit[0].equalsIgnoreCase("crackshot"))
            {
                if (Bukkit.getServer().getPluginManager().getPlugin("CrackShot") == null)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Wenn der Tag 'crackshot' benutzt wird, sollte auch das Plugin CrackShot installiert sein. Rettung unmöglich. (" + rawLootItem + ")");
                    return null;
                }

                ItemStack weapon = new CSUtility().generateWeapon(tagsplit[1]);

                if (weapon == null)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Das CrackShot-Item '" + tagsplit[1] + "' konnte nicht gefunden werden. Rettung unmöglich. (" + rawLootItem + ")");
                    return null;
                }

                if (material != null)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Wenn der Tag 'crackshot' benutzt wird, sollte keine id angegeben werden. Überschreibe durch CrackShot-Iteminformationen. (" + rawLootItem + ")");
                }

                material = weapon.getType();
                name = weapon.getItemMeta().getDisplayName();
                lore = weapon.getItemMeta().getLore();
            }

            /*
             * MoreItems
             */
            if (tagsplit[0].equalsIgnoreCase("moreitems"))
            {
                if (Bukkit.getServer().getPluginManager().getPlugin("MoreItems") == null)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Wenn der Tag 'moreitems' benutzt wird, sollte auch das Plugin MoreItems installiert sein. Rettung unmöglich. (" + rawLootItem + ")");
                    return null;
                }

                ItemStack special = Startup.getItem(tagsplit[1].toLowerCase());

                if (special == null)
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Das MoreItems-Item '" + tagsplit[1] + "' konnte nicht gefunden werden. Rettung unmöglich. (" + rawLootItem + ")");
                    return null;
                }

                if (rawLootItem.contains("crackshot") || rawLootItem.contains("id"))
                {
                    Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Wenn der Tag 'moreitems' benutzt wird, sollte keine id oder auch kein CrackShot-Item angegeben werden. Überschreibe durch MoreItems-Iteminformationen. (" + rawLootItem + ")");
                }

                material = special.getType();
                name = special.getItemMeta().getDisplayName();
                lore = special.getItemMeta().getLore();

                for (Enchantment e : special.getItemMeta().getEnchants().keySet())
                {
                    enchantments.add(e.getName().toLowerCase() + "#" + special.getItemMeta().getEnchants().get(e));
                }
            }
        }

        /*
         * Fehlerkorrektur
         */
        if (material == null)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Eine ID ist nicht gefunden worden. (" + rawLootItem + ")");
            return null;
        }

        if (min <= 0)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Der Mindestwert muss positiv sein! Wert zu 1 korrigiert. (" + rawLootItem + ")");
            min = 1;
        }

        if (max < min)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Der Mindestwert muss kleiner gleich dem Maximalwert sein! Werte wurden korrigiert, indem sie getauscht worden sind. (" + rawLootItem + ")");
            int h = max;
            max = min;
            min = h;
        }

        /*
         * Item zusammenbauen und als LootItem zurückgeben.
         */
        ItemStack item = new ItemStack(material, 1, data);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);

        for (String rawEnchantment : enchantments)
        {
            enchantItem(meta, rawEnchantment);
        }

        item.setItemMeta(meta);

        return new LootItem(item, min, max, weight);
    }

    private void setup()
    {
        List<String> medicineClass = new ArrayList<>();
        medicineClass.add("id:apple min:1 max:10 name:&cGesunder_Apfel");
        medicineClass.add("id:bread min:1 max:5 name:&6Eiweißbrot lore:§eSieht_lecker_aus!");

        List<String> archerClass = new ArrayList<>();
        archerClass.add("id:bow min:1 max:1 name:&cLangbogen enchant:arrow_damage#2");
        archerClass.add("id:arrow min:1 max:28 name:&6Eiweißbrot lore:§eSieht_lecker_aus!");

        _config.addDefault("Nahrung", medicineClass);
        _config.addDefault("Bogenschuetze", archerClass);

        _config.options().copyDefaults(true);

        save();
    }

    private void save()
    {
        try
        {
            _config.save(_sourcefile);
        }
        catch (IOException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "Konnte die Datei 'lootclasses.yml' nicht speichern! Existiert sie? Ist der Zugriff verhindet worden?");
        }
    }

    private String convert(String s)
    {
        return ChatColor.translateAlternateColorCodes('&', s.replaceAll("_", " "));
    }

    private void enchantItem(ItemMeta im, String enchantmentRaw)
    {
        String[] enchantmentTags = enchantmentRaw.split("#");

        Enchantment e = Enchantment.getByName(enchantmentTags[0].toUpperCase());

        /*
         * Fehlerüberprüfung
         */
        if (e == null)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Der Verzauberungsdatenblock '" + enchantmentRaw + "' ist ungültig, weil die Verzauberung nicht gefunden worden ist.");
            return;
        }

        int lvl = 1;
        try
        {
            lvl = Integer.parseInt(enchantmentTags[1]);
        }
        catch (NumberFormatException ex)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "<<LootClassLoader>> In der Datei 'lootclasses.yml' ist ein Fehlerhafter Datensatz! Der Verzauberungsdatenblock '" + enchantmentRaw + "' ist ungültig, das Level kein ganzzahliger Wert ist! Level wurde auf 1 korrigiert.");
        }

        im.addEnchant(e, lvl, true);
    }

}
