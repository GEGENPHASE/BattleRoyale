package com.gegenphase.battleroyale.loot.lootcontainer.scheduler;

import com.gegenphase.battleroyale.game.Game;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.loot.lootcontainer.services.LootContainerService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
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
                    if (!l.isSealed())
                    {
                        continue;
                    }

                    Location loc = l.getLocation();
                    loc.getWorld().spawnParticle(Particle.ASH, loc.clone().add(0.5, 1.5, 0.5), 50, 0.0, 0.0, 0.0, 0.05);
                }
            }
        }, 20, 20);
    }
}
