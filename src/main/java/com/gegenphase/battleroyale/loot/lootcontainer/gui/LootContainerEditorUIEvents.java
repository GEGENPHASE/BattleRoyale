package com.gegenphase.battleroyale.loot.lootcontainer.gui;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.util.items.Items;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootContainerEditorUIEvents implements Listener
{
    /*
     * Feldvariablen
     */
    private final ILootContainerService _lootContainerService;
    private final ILootClassService _lootClassService;

    /**
     * Konstruktor der Klasse {@link LootContainerEditorUIEvents}
     *
     * @param pl                   Das Plugin.
     * @param lootContainerService Das LootContainerService.
     */
    public LootContainerEditorUIEvents(Plugin pl, ILootContainerService lootContainerService, ILootClassService lootClassService)
    {
        _lootContainerService = lootContainerService;
        _lootClassService = lootClassService;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt)
    {
        if (!evt.getView().getTitle().startsWith(LootContainerEditorGUI.LOOT_CONTAINER_EDITOR_TITLE))
        {
            return;
        }

        evt.setCancelled(true);

        Player p = (Player) evt.getWhoClicked();
        ItemStack i = evt.getCurrentItem();

        if (i == null || evt.getClick().equals(ClickType.DOUBLE_CLICK))
        {
            return;
        }

        LootContainer l = getContainerFromTitle(evt.getView().getTitle());

        /*
         * Density +0.1
         */
        if (i.getItemMeta().getDisplayName().equals(Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.1", l.getDensity()).getItemMeta().getDisplayName()))
        {
            if (evt.getClick().isLeftClick())
            {
                l.setDensity(0.1 + l.getDensity());
            }
            else
            {
                l.setDensity(-0.1 + l.getDensity());
            }
        }

        /*
         * Density +0.01
         */
        if (i.getItemMeta().getDisplayName().equals(Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.01", l.getDensity()).getItemMeta().getDisplayName()))
        {
            if (evt.getClick().isLeftClick())
            {
                l.setDensity(0.01 + l.getDensity());
            }
            else
            {
                l.setDensity(-0.01 + l.getDensity());
            }
        }
        /*
         * Density +0.001
         */
        if (i.getItemMeta().getDisplayName().equals(Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.001", l.getDensity()).getItemMeta().getDisplayName()))
        {
            if (evt.getClick().isLeftClick())
            {
                l.setDensity(0.001 + l.getDensity());
            }
            else
            {
                l.setDensity(-0.001 + l.getDensity());
            }
        }

        /*
         * Spawnchance +0.1
         */
        if (i.getItemMeta().getDisplayName().equals(Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.1", l.getPctSpawn()).getItemMeta().getDisplayName()))
        {
            if (evt.getClick().isLeftClick())
            {
                l.setPctSpawn(0.1 + l.getPctSpawn());
            }
            else
            {
                l.setPctSpawn(-0.1 + l.getPctSpawn());
            }
        }

        /*
         * Spawnchance +0.01
         */
        if (i.getItemMeta().getDisplayName().equals(Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.01", l.getPctSpawn()).getItemMeta().getDisplayName()))
        {
            if (evt.getClick().isLeftClick())
            {
                l.setPctSpawn(0.01 + l.getPctSpawn());
            }
            else
            {
                l.setPctSpawn(-0.01 + l.getPctSpawn());
            }
        }
        /*
         * Spawnchance +0.001
         */
        if (i.getItemMeta().getDisplayName().equals(Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.001", l.getPctSpawn()).getItemMeta().getDisplayName()))
        {
            if (evt.getClick().isLeftClick())
            {
                l.setPctSpawn(0.001 + l.getPctSpawn());
            }
            else
            {
                l.setPctSpawn(-0.001 + l.getPctSpawn());
            }
        }

        /*
         * Toggle Spawn Even When Empty
         */
        if (i.getItemMeta().getDisplayName().equals(Items.toggleEmptySpawnButton(l.getAllowSetBlockWhenEmpty()).getItemMeta().getDisplayName()))
        {
            l.setAllowSetBlockWhenEmpty(!l.getAllowSetBlockWhenEmpty());
        }

        /*
         * Cycle Loot Class
         */
        if (i.getItemMeta().getDisplayName().equals(Items.cycleThroughLootClassButton(l.getLootClass()).getItemMeta().getDisplayName()))
        {
            String lootclass = l.getLootClass();
            if (!_lootClassService.exists(lootclass))
            {
                lootclass = "any";
                l.setLootClass(lootclass);
            }

            List<String> lootclasses = new ArrayList<>(_lootClassService.getLootClassKeys());
            lootclasses.add("any");

            int index = lootclasses.indexOf(lootclass);

            index = (index + 1) % lootclasses.size();

            lootclass = lootclasses.get(index);

            l.setLootClass(lootclass);
        }

        /*
         * Load Default
         */
        if (i.getItemMeta().getDisplayName().equals(Items.loadDefaultPresetButton().getItemMeta().getDisplayName()))
        {
           l.setLootClass(MainConfig.DEFAULT_LOOTCLASS);
           l.setDensity(MainConfig.DEFAULT_DENSITY);
           l.setPctSpawn(MainConfig.DEFAULT_PCT_SPAWN);
           l.setAllowSetBlockWhenEmpty(MainConfig.DEFAULT_SPAWN_WHEN_EMPTY);
            p.sendMessage(Messages.PREFIX + "Standardwerte wiederhergestellt.");
        }

        /*
         * Save Default
         */
        if (i.getItemMeta().getDisplayName().equals(Items.saveDefaultPresetButton(l).getItemMeta().getDisplayName()))
        {
            MainConfig.DEFAULT_LOOTCLASS = l.getLootClass();
            MainConfig.DEFAULT_DENSITY = l.getDensity();
            MainConfig.DEFAULT_PCT_SPAWN = l.getPctSpawn();
            MainConfig.DEFAULT_SPAWN_WHEN_EMPTY = l.getAllowSetBlockWhenEmpty();
            p.sendMessage(Messages.PREFIX + "Einstellungen als Standardwertevorgabe gespeichert.");
        }

        /*
         * Load Custom 1
         */
        if (i.getItemMeta().getDisplayName().equals(Items.loadCustom1PresetButton().getItemMeta().getDisplayName()))
        {
           l.setLootClass(MainConfig.CUSTOM1_LOOTCLASS);
           l.setDensity(MainConfig.CUSTOM1_DENSITY);
           l.setPctSpawn(MainConfig.CUSTOM1_PCT_SPAWN);
           l.setAllowSetBlockWhenEmpty(MainConfig.CUSTOM1_SPAWN_WHEN_EMPTY);
            p.sendMessage(Messages.PREFIX + "Einstellungen aus Voreinstellungsvorgabe 1 geladen.");
        }

        /*
         * Save Custom 1
         */
        if (i.getItemMeta().getDisplayName().equals(Items.saveCustom1PresetButton(l).getItemMeta().getDisplayName()))
        {
            MainConfig.CUSTOM1_LOOTCLASS = l.getLootClass();
            MainConfig.CUSTOM1_DENSITY = l.getDensity();
            MainConfig.CUSTOM1_PCT_SPAWN = l.getPctSpawn();
            MainConfig.CUSTOM1_SPAWN_WHEN_EMPTY = l.getAllowSetBlockWhenEmpty();
            p.sendMessage(Messages.PREFIX + "Einstellungen in Voreinstellungsvorgabe 1 gespeichert.");
        }

        /*
         * Load Custom 2
         */
        if (i.getItemMeta().getDisplayName().equals(Items.loadCustom2PresetButton().getItemMeta().getDisplayName()))
        {
           l.setLootClass(MainConfig.CUSTOM2_LOOTCLASS);
           l.setDensity(MainConfig.CUSTOM2_DENSITY);
           l.setPctSpawn(MainConfig.CUSTOM2_PCT_SPAWN);
           l.setAllowSetBlockWhenEmpty(MainConfig.CUSTOM2_SPAWN_WHEN_EMPTY);
            p.sendMessage(Messages.PREFIX + "Einstellungen aus Voreinstellungsvorgabe 2 geladen.");
        }

        /*
         * Save Custom 2
         */
        if (i.getItemMeta().getDisplayName().equals(Items.saveCustom2PresetButton(l).getItemMeta().getDisplayName()))
        {
            MainConfig.CUSTOM2_LOOTCLASS = l.getLootClass();
            MainConfig.CUSTOM2_DENSITY = l.getDensity();
            MainConfig.CUSTOM2_PCT_SPAWN = l.getPctSpawn();
            MainConfig.CUSTOM2_SPAWN_WHEN_EMPTY = l.getAllowSetBlockWhenEmpty();
            p.sendMessage(Messages.PREFIX + "Einstellungen in Voreinstellungsvorgabe 2 gespeichert.");
        }

        refreshAllItems(evt.getInventory(), l);
    }

    private LootContainer getContainerFromTitle(String title)
    {
        String toSplit = "";

        for (int i = title.length() - 1; i > 0; i--)
        {
            if (title.charAt(i) == '§')
            {
                toSplit = title.substring(i + 2);
            }
        }

        String[] split = toSplit.split(" ");

        World w = Bukkit.getWorld(split[0]);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);

        return _lootContainerService.getContainerAt(x, y, z, w);
    }

    private void refreshAllItems(Inventory i, LootContainer l)
    {
        i.setItem(10, Items.pctSpawnIcon(l.getPctSpawn()));
        i.setItem(19, Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.1", l.getPctSpawn()));
        i.setItem(28, Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.01", l.getPctSpawn()));
        i.setItem(37, Items.valueButton(Items.ITEM_TYPE_SPAWNCHANCE, "0.001", l.getPctSpawn()));

        i.setItem(12, Items.densityIcon(l.getDensity()));
        i.setItem(21, Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.1", l.getDensity()));
        i.setItem(30, Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.01", l.getDensity()));
        i.setItem(39, Items.valueButton(Items.ITEM_TYPE_DENSITY, "0.001", l.getDensity()));

        i.setItem(14, Items.emptySpawn(l.getAllowSetBlockWhenEmpty()));
        i.setItem(23, Items.toggleEmptySpawnButton(l.getAllowSetBlockWhenEmpty()));

        i.setItem(16, Items.lootClassIcon(l.getLootClass()));
        i.setItem(25, Items.cycleThroughLootClassButton(l.getLootClass()));

        i.setItem(41, Items.loadCustom2PresetButton());
        i.setItem(42, Items.loadCustom1PresetButton());
        i.setItem(43, Items.loadDefaultPresetButton());
        i.setItem(50, Items.saveCustom2PresetButton(l));
        i.setItem(51, Items.saveCustom1PresetButton(l));
        i.setItem(52, Items.saveDefaultPresetButton(l));
    }

}
