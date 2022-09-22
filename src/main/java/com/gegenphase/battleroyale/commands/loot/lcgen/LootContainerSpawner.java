package com.gegenphase.battleroyale.commands.loot.lcgen;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootClass;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;

/**
 * @author GEGENPHASE
 * @version 18.09.2022
 * <p>
 * Diese Klasse kümmert sich darum, dass Lootcontainer in der Welt an einer bestimmten Koordinate erscheinen
 * und mit Loot gefüllt werden.
 **/
public class LootContainerSpawner
{
    /*
     * Feldvariablen
     */
    private final ILootClassService _lootClassService;

    /**
     * Konstruktor der Klasse {@link LootContainerSpawner}.
     *
     * @param lootClassService Das LootClass-Service.
     */
    public LootContainerSpawner(ILootClassService lootClassService)
    {
        _lootClassService = lootClassService;
    }

    /**
     * Erzeuge einen LootContainer an einer bestimmten Stelle mit Standardeinstellungen.
     *
     * @param x Die x-Koordinate.
     * @param y Die y-Koordinate.
     * @param z Die z-Koordinate.
     * @param w Die Welt.
     * @return Der LootContainer der gespawnt wird.
     */
    public LootContainer spawnLootContainer(int x, int y, int z, World w)
    {
        /*
         * LootContainer bekommen
         */
        LootContainer l = new LootContainer(MainConfig.DEFAULT_LOOTCLASS, MainConfig.DEFAULT_PCT_SPAWN, x, y, z, w, MainConfig.DEFAULT_DENSITY, false, Material.CHEST.toString());
        Block b = w.getBlockAt(x, y, z);
        b.setType(Material.CHEST);
        /*
         * LootContainer bekommen
         */

        /*
         * Entscheidung Spawn oder nicht?
         */
        //if (Math.random() > MainConfig.GLOBAL_PCT_SPAWN * l.getPctSpawn())
        //{
        //    b.setType(Material.AIR);
        //    return;
        //}
        /*
         * Entscheidung Spawn oder nicht?
         */

        /*
         * LootKlasse bekommen
         */
        if (!_lootClassService.exists(l.getLootClass()))
        {
            return l;
        }

        LootClass lootClass = _lootClassService.getLootClass(l.getLootClass());
        /*
         * LootKlasse bekommen
         */

        /*
         * Inventar bekommen
         */
        Inventory inv = null;
        if (b.getType().equals(Material.CHEST))
        {
            inv = ((Chest) b.getState()).getInventory();
        }
        else if (b.getType().equals(Material.DISPENSER))
        {
            inv = ((Dispenser) b.getState()).getInventory();
        }

        if (inv == null)
        {
            return l;
        }
        /*
         * Inventar bekommen
         */

        /*
         * Inventar füllen
         */
        while (inv.isEmpty() && l.getDensity() * MainConfig.GLOBAL_DENSITY > 0)
        {
            for (int i = 0; i < inv.getSize(); i++)
            {
                // Dichtheit
                if (Math.random() < l.getDensity() * MainConfig.GLOBAL_DENSITY)
                {
                    inv.setItem(i, lootClass.getRandomReadyItem());
                }
            }
        }
        /*
         * Inventar füllen
         */

        return l;
    }
}
