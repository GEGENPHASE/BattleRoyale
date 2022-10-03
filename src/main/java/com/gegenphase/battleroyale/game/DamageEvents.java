package com.gegenphase.battleroyale.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 23.09.2022
 * <p>
 * Diese Klasse verhindert das bekommen von Schaden während der Vorbereitungsphase in einem Game.
 **/
public class DamageEvents implements Listener
{
    /*
     * Feldvariablen
     */
    private final Game _game;

    /**
     * Konstruktor der Klasse {@link DamageEvents}.
     *
     * @param plugin Das Plugin.
     * @param game   Das Spiel.
     */
    public DamageEvents(final Plugin plugin, final Game game)
    {
        _game = game;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamageDuringInvincibility(final EntityDamageEvent evt)
    {
        if (!(evt.getEntity() instanceof Player) || !_game.enabledInvincibility())
        {
            return;
        }

        evt.setCancelled(true);
    }

}
