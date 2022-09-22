package com.gegenphase.battleroyale.loot.lootcontainer.scheduler;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.game.Game;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 16.09.2022
 **/
public class LootContainerHighlighter
{
    /*
     * Feldvariablen
     */
    private final Plugin _pl;
    private final ILootContainerService _lootContainerService;
    private final Game _game;

    /**
     * Konstruktor der Klasse LootContainerHighlighter.
     *
     * @param pl                   Das Plugin.
     * @param lootContainerService Das LootContainer Service.
     */
    public LootContainerHighlighter(Plugin pl, ILootContainerService lootContainerService, Game game)
    {
        _pl = pl;
        _lootContainerService = lootContainerService;
        _game = game;
    }

    /**
     * Starte den Highlighter.
     */
    public void startHighlighter()
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(_pl, () ->
        {
            if (_game.isRunning())
            {
                Set<LootContainer> lootContainerSet = _lootContainerService.getLootContainers();

                for (LootContainer l : lootContainerSet)
                {
                    if (!l.isSealed() || !isPlaced(l.getLocation()))
                    {
                        continue;
                    }

                    Location loc = l.getLocation();
                    if (MainConfig.LOOTCONTAINER_SEALED_PARTICLE)
                    {
                        loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc.clone().add(0.5, 0.5, 0.5), 15, 0.3, 0.3, 0.3, 0.05);
                    }

                    if (MainConfig.LOOTCONTAINER_SEALED_SOUND)
                    {
                        loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_HARP, 0.25f, 2.0f);
                    }
                }
            }
        }, 20, 20);
    }

    private boolean isPlaced(Location l)
    {
        return !l.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()).getType().equals(Material.AIR);
    }

}
