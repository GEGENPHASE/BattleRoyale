package com.gegenphase.battleroyale.loot.lootcontainer.scheduler;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.game.Game;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

import java.util.Random;
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
    private final Set<LootContainer> _containers;

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
        _containers = _lootContainerService.getDefinedLootContainers();
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
                _containers.addAll(_lootContainerService.getAllContainers());

                for (LootContainer l : _containers)
                {
                    if (!l.isSealed() || !l.isPlaced())
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
                        Bukkit.getScheduler().scheduleSyncDelayedTask(_pl, () ->
                        {
                            loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_HARP, 0.25f, 1.23f);
                        }, new Random().nextInt(10));
                    }
                    //FireWorkUtil.spawnFireWork(loc);
                }
            }
        }, 20, 35);
    }
}
