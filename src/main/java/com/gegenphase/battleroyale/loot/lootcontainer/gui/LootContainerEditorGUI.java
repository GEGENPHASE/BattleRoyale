package com.gegenphase.battleroyale.loot.lootcontainer.gui;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.util.items.Items;
import com.gegenphase.battleroyale.util.ui.UI;
import org.bukkit.Material;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootContainerEditorGUI extends UI
{
    /*
     * Klassenvariablen
     */
    public static final String LOOT_CONTAINER_EDITOR_TITLE = "§5LootContainer Editor §d";

    /**
     * Konstruktor der Klasse {@link LootContainerEditorGUI}
     *
     * @param coordinateTitle Der Titelteil mit den Koordinaten.
     */
    public LootContainerEditorGUI(final String coordinateTitle, LootContainer l)
    {
        super(6, LOOT_CONTAINER_EDITOR_TITLE + coordinateTitle);

        fillWithPlaceholders(Items.placeholder(Material.GRAY_STAINED_GLASS_PANE));

        setItem(Items.pctSpawnIcon(l.getPctSpawn()), 10);
        setItem(Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.1", l.getPctSpawn()), 19);
        setItem(Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.01", l.getPctSpawn()), 28);
        setItem(Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.001", l.getPctSpawn()), 37);

        setItem(Items.densityIcon(l.getDensity()), 12);
        setItem(Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.1", l.getDensity()), 21);
        setItem(Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.01", l.getDensity()), 30);
        setItem(Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.001", l.getDensity()), 39);

        setItem(Items.emptySpawn(l.getAllowSetBlockWhenEmpty()), 14);
        setItem(Items.toggleEmptySpawnButton(l.getAllowSetBlockWhenEmpty()), 23);

        setItem(Items.lootClassIcon(l.getLootClass()), 16);
        setItem(Items.cycleThroughLootClassButton(l.getLootClass()), 25);

        setItem(Items.loadCustom2PresetButton(), 41);
        setItem(Items.loadCustom1PresetButton(), 42);
        setItem(Items.loadDefaultPresetButton(), 43);
        setItem(Items.saveCustom2PresetButton(l), 50);
        setItem(Items.saveCustom1PresetButton(l), 51);
        setItem(Items.saveDefaultPresetButton(l), 52);

    }
}
