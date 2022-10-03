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
    public static final String F_OVERSEER;
    public static final String F_TIPP;

    static
    {
        PREFIX_LOGGER = "[BattleRoyale] ";
        PREFIX = "§3Battle§7Royale §8| §7";
        F_HIGHLIGHT = "§b";
        F_NORMAL = "§7";
        F_OVERSEER = "§7§kX§e§o ";
        F_TIPP = "§7[§6§lTipp§7] §e";
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

    /**
     * Lass den Aufseher eine Nachricht senden.
     *
     * @param p       Der Spieler, der die Nachricht erhalten soll.
     * @param message Die Nachricht.
     */
    public static void showOverseerMessage(Player p, String message)
    {
        p.sendMessage(F_OVERSEER + message);
    }

    /**
     * Zeige einem Spieler einen Tipp an.
     *
     * @param p       Der Spieler, der die Nachricht erhalten soll.
     * @param message Der Tipp als Nachricht.
     */
    public static void showTipp(Player p, String message)
    {
        p.sendMessage(F_TIPP + message);
    }
}
