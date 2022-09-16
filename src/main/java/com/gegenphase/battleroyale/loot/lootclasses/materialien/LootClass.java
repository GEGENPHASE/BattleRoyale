package com.gegenphase.battleroyale.loot.lootclasses;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootClass
{
    /*
     * Feldvariablen
     */
    private final List<LootItem> _lootitems;

    /**
     * Eine Lootklasse.
     *
     * @param items Die Items, die zur Lootklasse dazugehören.
     */
    public LootClass(List<LootItem> items)
    {
        _lootitems = items;
    }

    /**
     * Eine Lootklasse mit einem leeren Bestand.
     */
    public LootClass()
    {
        _lootitems = new ArrayList<>();
    }

    /**
     * Bekomme eine Kopie des ganzen Bestandes.
     *
     * @return Die Kopie als ArrayList.
     */
    public ArrayList<LootItem> getContents()
    {
        return new ArrayList<>(_lootitems);
    }

    /**
     * Bekomme ein zufälliges LootItem.
     *
     * @return Das LootItem.
     */
    public LootItem getRandomLootItem()
    {
        int random = new Random().nextInt(_lootitems.size());

        return _lootitems.get(random);
    }

    /**
     * Bekomme ein zufälliges Item, bereit um in einen LootContainer einzufügen.
     *
     * @return Das LootItem.
     */
    public ItemStack getRandomReadyItem()
    {
        int random = new Random().nextInt(_lootitems.size());

        return _lootitems.get(random).getForLoot();
    }

    /**
     * Füge ein LootItem zum Bestand hinzu.
     *
     * @param li Das LootItem.
     */
    public void addLootItem(LootItem li)
    {
        _lootitems.add(li);
    }

    @Override
    public String toString()
    {
        StringBuilder stringRepresentation = new StringBuilder();

        for (LootItem li : _lootitems)
        {
            stringRepresentation.append("[").append(li.toString()).append("], ");
        }

        return "{" + stringRepresentation.substring(0, stringRepresentation.length() - 2) + "}";
    }

}
