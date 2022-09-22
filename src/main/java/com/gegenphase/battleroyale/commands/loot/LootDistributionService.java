package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.commands.loot.lcgen.LootContainerSpawner;
import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootClass;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GEGENPHASE
 * @version 15.09.2022
 **/
@Deprecated
public class LootDistributionService
{
    /*
     * Feldvariablen
     */
    public static final List<LootContainer> _lootContainers = new ArrayList<>();
    private final World _w;
    private final ILootClassService _lootClassService;

    /**
     * Konstruktor der Klasse {@link LootDistributionService}.
     */
    public LootDistributionService(ILootClassService lootClassService)
    {
        _lootClassService = lootClassService;
        _w = Bukkit.getWorld("world");
    }

    /**
     * Verteile Kisten um eine Bestimmte {@link Location}.
     *
     * @param center Das Zentrum der {@link Location}.
     * @param radius Der maximale Radius.
     * @param amount Die Anzahl der Kisten.
     */
    public void distributeLoot(Location center, int radius, int amount)
    {
        /*
         * Wiederhole amount-oft.
         */
        for (int i = 0; i < amount; i++)
        {
            /*
             * Generiere zufällige gültige x und z Koordinaten.
             */
            int centerX = center.getBlockX();
            int centerZ = center.getBlockZ();

            int x = getRandomCoordinate(centerX, radius);
            int z = getRandomCoordinate(centerZ, radius);

            while (Math.sqrt((x - centerX) * (x - centerX) + (z - centerZ) * (z - centerZ)) >= radius)
            {
                x = getRandomCoordinate(centerX, radius);
                z = getRandomCoordinate(centerZ, radius);
            }
            /*
             * Erstelle hier einen LootContainer.
             */
            generateRandomContainer(x, z);
        }
    }

    private int getRandomCoordinate(int center, int radius)
    {
        return (int) ((double) center + (2.0 * Math.random() - 1.0) * (double) radius);
    }

    private void generateRandomContainer(final int x, final int z)
    {
        /*
         * Bekomme die höchste y-Koordinate, den Block und erstelle dort einen LootContainer.
         */
        int y = _w.getHighestBlockYAt(x, z);
        Block b = _w.getBlockAt(x, y, z);

        LootContainer l = new LootContainer(MainConfig.DEFAULT_LOOTCLASS, MainConfig.DEFAULT_PCT_SPAWN, new Location(_w, x, y + 1, z), MainConfig.DEFAULT_DENSITY, MainConfig.DEFAULT_SPAWN_WHEN_EMPTY, LootContainer.TYPE_CHEST);

        /*
         * Prüfe, ob die LootKlasse existiert.
         */
        if (!_lootClassService.exists(l.getLootClass()))
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "++LootVerteiler++ Lootklasse '" + l.getLootClass() + "' existiert nicht. (" + l.toString() + ")");
            b.setType(Material.AIR);
            return;
        }

        /*
         * Generiere (also setzen und fülle den LootContainer).
         */
        generate(l);
        _lootContainers.add(l);
        Bukkit.getLogger().info("generated lootcontainer " + l.toString());
    }

    private int bekommeNiedrigsteKoordinate(int x, int y, int z)
    {
        while (_w.getBlockAt(x, y, z).getType().equals(Material.AIR))
        {
            y--;
            if(y < - 64) {
                // Errorwert -512
                return -512;
            }
        }

        return y + 1;
    }


    private void generate(LootContainer l)
    {
        /*
         * Block bekommen und setzen.
         */
        Block b = l.getLocation().getBlock();
        b.setType(Material.valueOf(l.getType()));

        /*
         * Inventar bekommen.
         */
        Inventory i = null;
        if (b.getType().equals(Material.CHEST))
        {
            i = ((Chest) b.getState()).getInventory();
        }
        else if (b.getType().equals(Material.DISPENSER))
        {
            i = ((Dispenser) b.getState()).getInventory();
        }

        /*
         * Inventar füllen
         */
        LootClass lootClass = _lootClassService.getLootClass(l.getLootClass());

        for (int index = 0; index < i.getSize(); index++)
        {
            if (Math.random() < l.getDensity() * MainConfig.GLOBAL_DENSITY)
            {
                i.setItem(index, lootClass.getRandomReadyItem());
            }
        }
    }

    /**
     * Entferne alle zufällig generierten LootContainer.
     */
    public static void removeAll()
    {
        for (LootContainer l : LootDistributionService._lootContainers)
        {
            l.getLocation().getBlock().setType(Material.AIR);
        }
    }

}
