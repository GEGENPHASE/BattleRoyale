package com.gegenphase.battleroyale.loot.lootclasses;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.ClassOrderer;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootItem
{
    /*
     * Feldvariablen
     */
    private final ItemStack _i;
    private final int _min;
    private final int _max;
    private final int _gewichtung;

    /**
     * Ein Lootitem.
     *
     * @param item Das zugrundeliegende Item.
     * @param min  Die Mindestmenge.
     * @param max  Die Maximalmenge.
     */
    public LootItem(ItemStack item, int min, int max, int weight)
    {
        _i = item;
        _min = min;
        _max = max;
        _gewichtung = weight;
    }

    /**
     * Bekomme ein Item, welches bereit ist, um es in einen LootContainer zu platzieren.
     *
     * @return Das Item.
     */
    public ItemStack getForLoot()
    {
        int amount = new Random().nextInt(_min, _max + 1);
        _i.setAmount(amount);
        return _i;
    }

    /**
     * Bekomme die Gewichtung des Gegenstandes (Wie oft es in der Lootklasse vertreten ist).
     *
     * @return Die Gewichtung des LootItems.
     */
    public int getWeight()
    {
        return _gewichtung;
    }

    @Override
    public String toString()
    {
        return _i.toString() + " min:" + _min + " max:" + _max;
    }

}
