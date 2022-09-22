package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.util.items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class LootContainerIndicator
{
    /*
     * Feldvariablen
     */
    private final Plugin _pl;
    private final ILootContainerService _lootContainerService;

    /**
     * Konstruktor der Klasse LootContainerIndicator
     *
     * @param pl Das Plugin.
     */
    public LootContainerIndicator(final Plugin pl, final ILootContainerService lootContainerService)
    {
        _pl = pl;
        _lootContainerService = lootContainerService;
    }

    /**
     * Starte den Indikator-Scheduler.
     */
    public void startIndicatorScheduler()
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(_pl, () ->
        {
            // Iteriere durch jeden Spieler auf dem Server.
            for (Player player : Bukkit.getOnlinePlayers())
            {
                // Bekomme das Item, das der Spieler in der Hand hält.
                ItemStack held = player.getInventory().getItemInMainHand();

                // Brich die Iteration ab, sobald das Item nicht dem LC-Tool entspricht.
                if (!held.equals(Items.lootcontainerWand()))
                {
                    continue;
                }

                // Iteriere nun durch jeden LC und erzeuge Partikel.
                for (LootContainer lootContainer : _lootContainerService.getDefinedLootContainers())
                {
                    player.spawnParticle(Particle.SMOKE_NORMAL, lootContainer.getLocation().clone().add(0.5, 1, 0.5), 25, 0.0, 0.0, 0.0, 0.05);
                    player.spawnParticle(Particle.VILLAGER_HAPPY, lootContainer.getLocation().clone().add(0.5, 1, 0.5), 5, 0.0, 0.0, 0.0, 0.05);
                }
            }
        }, 20, 20);
    }

}
