package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * @author GEGENPHASE
 * @version 13.09.2022
 **/
public class CmdLootClassSubWerkzeug
{
    /*
     * Feldvariablen
     */
    private final ILootClassService _lootClassService;

    /**
     * Konstruktor der Klasse CmdLootClassSubWerkzeug.
     *
     * @param lootClassService     Das Loot-Klassen-Service.
     */
    public CmdLootClassSubWerkzeug(final ILootClassService lootClassService)
    {
        _lootClassService = lootClassService;
    }

    /**
     * Behandele den SubCommand 'class' für '/loot class ...'.
     *
     * @param p Der Spieler, der den Befehl ausführt.
     * @param args Die Argumente, die der Spieler für den Befehl eintippt.
     */
    public void classSubCommand(Player p, String[] args)
    {
        /*
         * Prüfe, ob Anzahl der Argumente okay ist.
         */
        if (args.length < 2)
        {
            Messages.showCommandUsage(p, "/loot class <load | help>");
            return;
        }

        switch (args[1])
        {
            case "load" ->
            {
                _lootClassService.load();
                p.sendMessage(Messages.PREFIX + "Lootklassen geladen und überschrieben!");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK,10.0f,0.625f);
            }
            case "help" -> {
                Messages.showCommandUsage(p,"/loot class <load |help>");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "load " + Messages.F_NORMAL + "Lade und überschreibe alle Lootklassen mit denen, die in der 'lootclasses.yml' Konfigurationsdatei gespeichert sind.");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "help " + Messages.F_NORMAL + "Zeige diese Seite an.");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK,10.0f,0.625f);
            }
            default ->
            {
                Messages.showCommandUsage(p,"/loot class <load | help>");
            }
        }
    }
}
