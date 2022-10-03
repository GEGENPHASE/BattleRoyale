package com.gegenphase.battleroyale.commands.tipp;

import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 24.09.2022
 **/
public class TippJoinEvents implements Listener
{
    /*
     * Feldvariablen
     */
    private final Plugin _pl;

    /**
     * Konstruktor der Klasse {@link TippJoinEvents}.
     *
     * @param pl Das Plugin.
     */
    public TippJoinEvents(Plugin pl)
    {
        _pl = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(_pl, () ->
        {
            e.getPlayer().chat("/tipp");
            e.getPlayer().sendMessage(Messages.PREFIX + "Bekomme weitere Tipps mit " + Messages.F_HIGHLIGHT + "/tipp" + Messages.F_NORMAL + ".");
        }, 20 * 5);
    }

}
