package com.gegenphase.battleroyale.util.messages;

import org.bukkit.entity.Player;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class Messages
{
    public static final String PREFIX_LOGGER;
    public static final String F_HIGHLIGHT;
    public static final String PREFIX;
    public static final String F_NORMAL;

    static
    {
        PREFIX_LOGGER = "[BattleRoyale] ";
        PREFIX = "§3Battle§7Royale §8| §7";
        F_HIGHLIGHT = "§b";
        F_NORMAL = "§7";
    }

    /**
     * Zeige die korrekte Befehlsnutzung an.
     *
     * @param p Der Spieler, der sie sehen soll.
     * @param s Die korrekte Befehlsnutzung.
     */
    public static void showCommandUsage(Player p, String s)
    {
        p.sendMessage(PREFIX + F_HIGHLIGHT + s);
    }
}
