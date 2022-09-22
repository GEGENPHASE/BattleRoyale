package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 15.09.2022
 **/
public class LootContainerBreakEvents implements Listener
{
    /*
     * Feldvariablen
     */
    private final Plugin _pl;
    private final ILootContainerService _lootContainerService;

    /**
     * Konstruktor der Klasse LootContainerBreakEvents.
     *
     * @param pl                   Das Plugin.
     * @param lootContainerService Das LootContainerService.
     */
    public LootContainerBreakEvents(Plugin pl, ILootContainerService lootContainerService)
    {
        _pl = pl;
        _lootContainerService = lootContainerService;
        _pl.getServer().getPluginManager().registerEvents(this, _pl);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt)
    {
        Location location = evt.getInventory().getLocation();
        Inventory i = evt.getInventory();

        /*
         * Wenn Inventar die Nullreferenz darstellt.
         */
        if (location == null || location.getBlock().getType().equals(Material.AIR))
        {
            return;
        }

        /*
         * Ist das ein LootContainer?
         */
        if (_lootContainerService.isContainer(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld()))
        {

            if (i.isEmpty() && MainConfig.LOOTCONTAINER_EMPTY_BREAK_ON_EXIT || MainConfig.LOOTCONTAINER_ALWAYS_BREAK_ON_EXIT)
            {
                /*
                 * ItemSpawn
                 */
                if (MainConfig.LOOTCONTAINER_DROP_ITEMS_ON_BREAK)
                {
                    location.getBlock().breakNaturally();
                }
                else
                {
                    location.getBlock().setType(Material.AIR);
                }

                /*
                 * Audio Visuell
                 */
                location.getWorld().playSound(location, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.2f, 1.5f);
                location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location.clone().add(0.5, 0.5, 0.5), 50, Material.CHEST.createBlockData());
                location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location.clone().add(0.5, 0.5, 0.5), 50, Material.DISPENSER.createBlockData());
                location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.clone().add(0.5, 0.5, 0.5), 25, 0, 0, 0, 0.05);
            }
        }
    }

}
