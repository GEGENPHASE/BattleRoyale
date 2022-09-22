package com.gegenphase.battleroyale.loot.lootcontainer.scheduler;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 16.09.2022
 **/
public class LootContainerOpenEvents implements Listener
{
    /*
     * Feldvariablen
     */
    public final ILootContainerService _lootContainerService;

    /**
     * Konstruktor der Klasse LootContainerOpenEvents.
     *
     * @param pl                   Das Plugin.
     * @param lootContainerService Das LootContainerService.
     */
    public LootContainerOpenEvents(Plugin pl, ILootContainerService lootContainerService)
    {
        _lootContainerService = lootContainerService;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onLootContainerOpen(InventoryOpenEvent evt)
    {
        Location l = evt.getInventory().getLocation();

        if (l == null)
        {
            return;
        }

        if (_lootContainerService.isContainer(l.getBlockX(), l.getBlockY(), l.getBlockZ(), l.getWorld()))
        {
            LootContainer lc = _lootContainerService.getContainerAt(l.getBlockX(), l.getBlockY(), l.getBlockZ(), l.getWorld());

            if (lc.isSealed())
            {
                //((Player) evt.getPlayer()).playSound(l, Sound.ENTITY_ITEM_BREAK, 0.5f, 1.5f);
                _lootContainerService.getContainerAt(l.getBlockX(), l.getBlockY(), l.getBlockZ(), l.getWorld()).breakSeal();
            }
        }
    }


}
