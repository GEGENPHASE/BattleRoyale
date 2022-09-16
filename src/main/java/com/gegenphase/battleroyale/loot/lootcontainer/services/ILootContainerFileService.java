package com.gegenphase.battleroyale.loot.lootcontainer.services;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;

import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public interface ILootContainerFileService
{
    /**
     * Lade alle Lootcontainers aus der Datei.
     *
     * @return Eine Menge mit allen Lootcontainers.
     */
    public Set<LootContainer> load();

    /**
     * Speichere alle Lootcontainers in die Datei auf die Festplatte.
     *
     * @param lootContainers Die Menge mit den Lootcontainers.
     */
    public void save(Set<LootContainer> lootContainers);

}
