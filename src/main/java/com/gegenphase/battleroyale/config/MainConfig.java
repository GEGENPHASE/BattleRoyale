package com.gegenphase.battleroyale.config;

import com.gegenphase.battleroyale.commands.loot.lcgen.LootSpreader;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 13.09.2022
 **/
public class MainConfig
{
    /*
     * Klassenkonstanten
     */
    public static double GLOBAL_DENSITY;
    public static double GLOBAL_PCT_SPAWN;
    public static boolean GLOBAL_SPAWN_WHEN_EMPTY;
    public static boolean ALLOW_RANDOM_CLASS_TO_BE_CHOSEN_IN_ANY;
    public static double DEFAULT_DENSITY;
    public static double DEFAULT_PCT_SPAWN;
    public static boolean DEFAULT_SPAWN_WHEN_EMPTY;
    public static String DEFAULT_LOOTCLASS;
    public static double CUSTOM1_DENSITY;
    public static double CUSTOM1_PCT_SPAWN;
    public static boolean CUSTOM1_SPAWN_WHEN_EMPTY;
    public static String CUSTOM1_LOOTCLASS;
    public static double CUSTOM2_DENSITY;
    public static double CUSTOM2_PCT_SPAWN;
    public static boolean CUSTOM2_SPAWN_WHEN_EMPTY;
    public static String CUSTOM2_LOOTCLASS;
    public static boolean LOOTCONTAINER_ALWAYS_BREAK_ON_EXIT;
    public static boolean LOOTCONTAINER_EMPTY_BREAK_ON_EXIT;
    public static boolean LOOTCONTAINER_DROP_ITEMS_ON_BREAK;
    public static boolean LOOTCONTAINER_SEALED_PARTICLE;
    public static boolean LOOTCONTAINER_SEALED_SOUND;

    static
    {
        GLOBAL_DENSITY = 0.5D;
        GLOBAL_PCT_SPAWN = 0.5D;
        GLOBAL_SPAWN_WHEN_EMPTY = false;

        ALLOW_RANDOM_CLASS_TO_BE_CHOSEN_IN_ANY = false;
        LOOTCONTAINER_ALWAYS_BREAK_ON_EXIT = false;
        LOOTCONTAINER_DROP_ITEMS_ON_BREAK = true;
        LOOTCONTAINER_EMPTY_BREAK_ON_EXIT = true;

        LOOTCONTAINER_SEALED_PARTICLE = true;
        LOOTCONTAINER_SEALED_SOUND = true;

        DEFAULT_DENSITY = 0.5D;
        DEFAULT_PCT_SPAWN = 0.5D;
        DEFAULT_SPAWN_WHEN_EMPTY = false;
        DEFAULT_LOOTCLASS = "any";

        CUSTOM1_DENSITY = 0.5D;
        CUSTOM1_PCT_SPAWN = 0.5D;
        CUSTOM1_SPAWN_WHEN_EMPTY = false;
        CUSTOM1_LOOTCLASS = "any";

        CUSTOM2_DENSITY = 0.5D;
        CUSTOM2_PCT_SPAWN = 0.5D;
        CUSTOM2_SPAWN_WHEN_EMPTY = false;
        CUSTOM2_LOOTCLASS = "any";
    }

    /*
     * Feldvariablen
     */
    private final FileConfiguration _config;
    private final Plugin _plugin;

    /**
     * Die Hauptkonfiguration.
     *
     * @param pl Das Plugin.
     */
    public MainConfig(Plugin pl)
    {
        _config = pl.getConfig();
        _plugin = pl;

        setup();
        reload();
    }

    private void setup()
    {
        _config.addDefault("Loot.Container.Appearance.DropItemsOnBreak", true);
        _config.addDefault("Loot.Container.Appearance.AlwaysBreakOnExit", false);
        _config.addDefault("Loot.Container.Appearance.SealedParticle", true);
        _config.addDefault("Loot.Container.Appearance.SealedSound", true);
        _config.addDefault("Loot.Container.Appearance.EmptyBreakOnExit", true);

        _config.addDefault("Loot.Container.Global.Density", 0.5D);
        _config.addDefault("Loot.Container.Global.SpawnChance", 0.5D);
        _config.addDefault("Loot.Container.Global.SpawnWhenEmpty", false);

        _config.addDefault("Loot.Class.Any.AllowRandomClassToBeChosen", true);

        _config.addDefault("Loot.Container.Default.Density", 0.5D);
        _config.addDefault("Loot.Container.Default.SpawnChance", 0.5D);
        _config.addDefault("Loot.Container.Default.SpawnWhenEmpty", false);
        _config.addDefault("Loot.Container.Default.LootClass", "any");

        _config.addDefault("Loot.Container.Custom1.Density", 0.5D);
        _config.addDefault("Loot.Container.Custom1.SpawnChance", 0.5D);
        _config.addDefault("Loot.Container.Custom1.SpawnWhenEmpty", false);
        _config.addDefault("Loot.Container.Custom1.LootClass", "any");

        _config.addDefault("Loot.Container.Custom2.Density", 0.5D);
        _config.addDefault("Loot.Container.Custom2.SpawnChance", 0.5D);
        _config.addDefault("Loot.Container.Custom2.SpawnWhenEmpty", false);
        _config.addDefault("Loot.Container.Custom2.LootClass", "any");

        _config.options().copyDefaults(true);
        _plugin.saveConfig();
    }

    /**
     * Lade die Konfiguration und somit die Werte neu.
     */
    public void reload()
    {
        _plugin.reloadConfig();
        GLOBAL_DENSITY = _config.getDouble("Loot.Container.Global.Density");
        GLOBAL_PCT_SPAWN = _config.getDouble("Loot.Container.Global.SpawnChance");
        GLOBAL_SPAWN_WHEN_EMPTY = _config.getBoolean("Loot.Container.Global.SpawnWhenEmpty");

        ALLOW_RANDOM_CLASS_TO_BE_CHOSEN_IN_ANY = _config.getBoolean("Loot.Class.Any.AllowRandomClassToBeChosen");

        LOOTCONTAINER_ALWAYS_BREAK_ON_EXIT = _config.getBoolean("Loot.Container.Appearance.AlwaysBreakOnExit");
        LOOTCONTAINER_DROP_ITEMS_ON_BREAK = _config.getBoolean("Loot.Container.Appearance.DropItemsOnBreak");
        LOOTCONTAINER_SEALED_PARTICLE = _config.getBoolean("Loot.Container.Appearance.SealedParticle");
        LOOTCONTAINER_SEALED_SOUND = _config.getBoolean("Loot.Container.Appearance.SealedSound");
        LOOTCONTAINER_EMPTY_BREAK_ON_EXIT = _config.getBoolean("Loot.Container.Appearance.EmptyBreakOnExit");

        DEFAULT_DENSITY = _config.getDouble("Loot.Container.Default.Density");
        DEFAULT_PCT_SPAWN = _config.getDouble("Loot.Container.Default.SpawnChance");
        DEFAULT_SPAWN_WHEN_EMPTY = _config.getBoolean("Loot.Container.Default.SpawnWhenEmpty");
        DEFAULT_LOOTCLASS = _config.getString("Loot.Container.Default.LootClass");

        CUSTOM1_DENSITY = _config.getDouble("Loot.Container.Custom1.Density");
        CUSTOM1_PCT_SPAWN = _config.getDouble("Loot.Container.Custom1.SpawnChance");
        CUSTOM1_SPAWN_WHEN_EMPTY = _config.getBoolean("Loot.Container.Custom1.SpawnWhenEmpty");
        CUSTOM1_LOOTCLASS = _config.getString("Loot.Container.Custom1.LootClass");

        CUSTOM2_DENSITY = _config.getDouble("Loot.Container.Custom2.Density");
        CUSTOM2_PCT_SPAWN = _config.getDouble("Loot.Container.Custom2.SpawnChance");
        CUSTOM2_SPAWN_WHEN_EMPTY = _config.getBoolean("Loot.Container.Custom2.SpawnWhenEmpty");
        CUSTOM2_LOOTCLASS = _config.getString("Loot.Container.Custom2.LootClass");
    }

    /**
     * Speichere die Konfiguration.
     */
    public void saveConfig()
    {
        // Nein es geht nicht mit der _config... Keine Ahnung warum, es ist einfach so, Informatik halt. #ProgramersDailyLife.
         _plugin.reloadConfig();

        _plugin.getConfig().set("Loot.Container.Default.Density", DEFAULT_DENSITY);
        _plugin.getConfig().set("Loot.Container.Default.SpawnChance", DEFAULT_PCT_SPAWN);
        _plugin.getConfig().set("Loot.Container.Default.SpawnWhenEmpty", DEFAULT_SPAWN_WHEN_EMPTY);
        _plugin.getConfig().set("Loot.Container.Default.LootClass", DEFAULT_LOOTCLASS);

        _plugin.getConfig().set("Loot.Container.Custom1.Density", CUSTOM1_DENSITY);
        _plugin.getConfig().set("Loot.Container.Custom1.SpawnChance", CUSTOM1_PCT_SPAWN);
        _plugin.getConfig().set("Loot.Container.Custom1.SpawnWhenEmpty", CUSTOM1_SPAWN_WHEN_EMPTY);
        _plugin.getConfig().set("Loot.Container.Custom1.LootClass", CUSTOM1_LOOTCLASS);

        _plugin.getConfig().set("Loot.Container.Custom2.Density", CUSTOM2_DENSITY);
        _plugin.getConfig().set("Loot.Container.Custom2.SpawnChance", CUSTOM2_PCT_SPAWN);
        _plugin.getConfig().set("Loot.Container.Custom2.SpawnWhenEmpty", CUSTOM2_SPAWN_WHEN_EMPTY);
        _plugin.getConfig().set("Loot.Container.Custom2.LootClass", CUSTOM2_LOOTCLASS);

        _plugin.saveConfig();
    }
}