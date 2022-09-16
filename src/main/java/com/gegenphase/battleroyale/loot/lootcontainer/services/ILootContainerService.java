package com.gegenphase.battleroyale.loot.lootcontainer.services;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import org.bukkit.World;

import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public interface ILootContainerService
{
    /**
     * Registriere einen LootContainer.
     *
     * @param l Der LootContainers.
     */
    public void add(LootContainer l);

    /**
     * Entferne einen LootContainer.
     *
     * @param l Der zu entfernende LootContainer.
     */
    public void remove(LootContainer l);

    /**
     * Entferne einen LootContainer.
     *
     * @param x Die x-Koordinate des LootContainers
     * @param y Die y-Koordinate des LootContainers
     * @param z Die z-Koordinate des LootContainers
     * @param w Die Welt, in der sich der LootContainers befindet.
     */
    public void remove(int x, int y, int z, World w);

    /**
     * Überschreibe alle bestehenden LootContainers mit denen, die von der Festplatte gelesen werden.
     */
    public void load();

    /**
     * Speichere alle LootContainers in die Datei.
     */
    public void save();

    /**
     * Bekomme alle LootContainers.
     *
     * @return Eine Kopie der Menge aller LootContainers.
     */
    public Set<LootContainer> getLootContainers();

    /**
     * Schaue, ob ein Container auch ein LootContainer ist.
     *
     * @return Wahr, wenn die oben genannte Bedingung wahr ist.
     */
    public boolean isContainer(int x, int y, int z, World w);

    /**
     * Bekomme den LootContainer von einer bestimmten Position.
     *
     * @param x Die x-Koordinate des LootContainers.
     * @param y Die y-Koordinate des LootContainers
     * @param z Die z-Koordinate des LootContainers
     * @param w Die Welt, in der sich der LootContainers befindet.
     * @return Den LootContainer.
     */
    public LootContainer getContainerAt(int x, int y, int z, World w);

    /**
     * Setze alle LootContainer in die Welt.
     *
     * @param remove Sollen die LootContainer gesetzt werden, oder entfernt werden. Wahr, wenn durch AIR ersetzt werden soll, also entfernt werden soll.
     */
    public void placeAllLootContainers(boolean remove);
}
