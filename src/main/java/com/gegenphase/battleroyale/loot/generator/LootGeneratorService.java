package com.gegenphase.battleroyale.loot.generator;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootClass;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootGeneratorService
{
    /*
     * Feldvariablen
     */
    private final ILootContainerService _lootContainerService;
    private final ILootClassService _lootClassService;

    /**
     * Konstruktor der Klasse LootGeneratorService.
     *
     * @param lootContainerService Das LootContainerService.
     * @param lootClassService     Das LootClassService.
     */
    public LootGeneratorService(ILootContainerService lootContainerService, ILootClassService lootClassService)
    {
        _lootContainerService = lootContainerService;
        _lootClassService = lootClassService;
    }

    /**
     * Generiere den Loot.
     */
    public void generateLoot()
    {
        Set<LootContainer> lootContainers = _lootContainerService.getDefinedLootContainers();

        for (LootContainer l : lootContainers)
        {
            if (!_lootClassService.exists(l.getLootClass()))
            {
                Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "::LootGenerator:: Lootklasse '" + l.getLootClass() + "' existiert nicht. (" + l.toString() + ")");
                continue;
            }

            Block b = l.getLocation().getBlock();

            if (Math.random() < l.getPctSpawn() * MainConfig.GLOBAL_PCT_SPAWN)
            {
                b.setType(Material.valueOf(l.getType().toUpperCase()));
                fillContainer(b, _lootClassService.getLootClass(l.getLootClass()), l.getDensity());
                continue;
            }

            if (l.getAllowSetBlockWhenEmpty() || MainConfig.GLOBAL_SPAWN_WHEN_EMPTY)
            {
                b.setType(Material.valueOf(l.getType().toUpperCase()));
                clearContainer(b);
            }
            else
            {
                b.setType(Material.AIR);
            }
        }
    }

    private void clearContainer(Block b)
    {
        if (!b.getType().equals(Material.DISPENSER) && !b.getType().equals(Material.CHEST))
        {
            return;
        }

        Inventory inventory = null;

        if (b.getType().equals(Material.CHEST))
        {
            inventory = ((Chest) b.getState()).getBlockInventory();
        }
        else if (b.getType().equals(Material.DISPENSER))
        {
            inventory = ((Dispenser) b.getState()).getInventory();
        }

        inventory.clear();
    }

    private boolean isValidContainer(Block b)
    {
        return b.getType().equals(Material.CHEST) || b.getType().equals(Material.DISPENSER);
    }

    /**
     * Fülle einen Block mit Loot.
     *
     * @param b Der Block.
     * @param lootclass Die LootKlasse.
     * @param density Die Dichtheit
     */
    public void fillContainer(Block b, LootClass lootclass, double density)
    {
        Inventory inv = null;

        if (b.getType().equals(Material.CHEST))
        {
            inv = ((Chest) b.getState()).getBlockInventory();
        }
        else if (b.getType().equals(Material.DISPENSER))
        {
            inv = ((Dispenser) b.getState()).getInventory();
        }
        else
        {
            return;
        }

        clearContainer(b);

        while (inv.isEmpty() && density > 0)
        {
            for (int i = 0; i < inv.getSize(); i++)
            {
                inv.setItem(i, new ItemStack(Material.AIR, 1)); // Hier wird vorher geleert!

                if (skipItemSlot(density))
                {
                    inv.setItem(i, lootclass.getRandomReadyItem());
                }
            }
        }
    }

    public boolean skipItemSlot(double density)
    {
        return Math.random() < density * MainConfig.GLOBAL_DENSITY;
    }

}
