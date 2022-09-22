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
    /*
     * Dateien Laden und Speichern
     */

    /**
     * Überschreibe alle bestehenden LootContainers mit denen, die von der Festplatte gelesen werden.
     */
    public void load();

    /**
     * Speichere alle LootContainers in die Datei.
     */
    public void save();

    /*
     * Add
     */

    /**
     * Registriere einen LootContainer.
     *
     * @param l Der LootContainers.
     */
    public void addDefined(LootContainer l);

    /**
     * Registriere einen LootContainer als zufällig generiert.
     *
     * @param l Der LootContainers.
     */
    public void addRandom(LootContainer l);

    /*
     * Remove
     */

    /**
     * Entferne einen LootContainer.
     *
     * @param x Die x-Koordinate des LootContainers
     * @param y Die y-Koordinate des LootContainers
     * @param z Die z-Koordinate des LootContainers
     * @param w Die Welt, in der sich der LootContainers befindet.
     */
    public void removeDefined(int x, int y, int z, World w);

    /**
     * Leere alle zufälligen LootContainer.
     */
    public void clearRandom();

    /*
     * Get
     */

    /**
     * Bekomme alle LootContainer, die definiert worden sind.
     *
     * @return Eine Kopie der Menge aller LootContainers.
     */
    public Set<LootContainer> getDefinedLootContainers();

    /**
     * Bekomme alle LootContainer, die zufällig generiert worden sind.
     *
     * @return Eine Kopie der Menge aller LootContainer.
     */
    public Set<LootContainer> getRandomLootContainers();

    /**
     * Bekomme eine Kopie aller LootContainer zusammen in einer Menge.
     *
     * @return Alle LootContainer.
     */
    public Set<LootContainer> getAllContainers();

    /*
     * Check & get
     */

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

    /*
     * Place and unplace
     */

    /**
     * Setze alle LootContainer in die Welt.
     */
    public void placeAllDefinedLootContainers();

    /**
     * Entferne alle platzierten LootContainer.
     */
    public void unplaceAll();

    void fireWorkUnsealed();
}
