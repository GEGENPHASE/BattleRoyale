package com.gegenphase.battleroyale.util.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class UI
{
    /*
     * Feldvariablen
     */
    protected final Inventory _i;

    /**
     * Erstelle ein UI mit einer festen Zeilenzahl und einem Titel.
     *
     * @param zeilen Die Anzahl der Zeilen der 9er Spalten.
     * @param titel  Der Titel, der angezeigt werden soll.
     */
    public UI(int zeilen, String titel)
    {
        _i = Bukkit.createInventory(null, zeilen * 9, titel);
    }

    /**
     * Setze ein Gegenstand an einer bestimmten Stelle.
     *
     * @param i    Der Gegenstand als {@link ItemStack}.
     * @param slot Der Slot, bei dem der Gegenstand platziert werden soll.
     */
    protected void setItem(ItemStack i, int slot)
    {
        _i.setItem(slot, i);
    }

    /**
     * Fülle den ganzen Platz mit Platzhaltern aus.
     *
     * @param placeholder Das Platzhalteritem.
     */
    protected void fillWithPlaceholders(ItemStack placeholder)
    {
        for (int i = 0; i < _i.getSize(); i++)
        {
            setItem(placeholder, i);
        }
    }

    /**
     * Lasse einen Spieler das Inventar öffnen.
     *
     * @param p Der Spieler, der das Inventar geöffnet bekommt.
     */
    public void openTo(Player p)
    {
        p.openInventory(_i);
    }

}
