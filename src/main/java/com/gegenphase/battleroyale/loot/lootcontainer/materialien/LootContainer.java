package com.gegenphase.battleroyale.loot.lootcontainer.materialien;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class LootContainer
{
    /*
     * Klassenvariablen
     */
    public static final String TYPE_DISPENSER = "DISPENSER";
    public static final String TYPE_CHEST = "CHEST";

    /*
     * Feldvariablen
     */
    private String _lootClass;
    private double _pctSpawn;
    private Location _location;
    private double _density;
    private boolean _spawnBlockEvenWhenEmpty;
    private String _type;
    private boolean _sealed;

    /**
     * Konstruktor der Klasse LootContainer.
     *
     * @param lootClass               Die Lootklasse als String.
     * @param pctSpawn                Die Chance, dass der LootContainer überhaupt spawnt.
     * @param loc                     Die Position des LootContainers.
     * @param density                 Die Itemdichte.
     * @param spawnBlockEvenWhenEmpty Soll der Block erscheinen, auch, wenn der LootContainer leer spawnt?
     * @param type                    Der Blocktyp.
     */
    public LootContainer(String lootClass, double pctSpawn, Location loc, double density, boolean spawnBlockEvenWhenEmpty, String type)
    {
        _lootClass = lootClass;
        _pctSpawn = pctSpawn;
        _location = loc;
        _density = density;
        _spawnBlockEvenWhenEmpty = spawnBlockEvenWhenEmpty;
        _type = type;
        _sealed = true;
    }

    /**
     * Konstruktor der Klasse LootContainer.
     *
     * @param lootClass               Die Lootklasse als String.
     * @param pctSpawn                Die Chance, dass der LootContainer überhaupt spawnt.
     * @param x                       Die x-Koordinate des LootContainers.
     * @param y                       Die y-Koordinate des LootContainers.
     * @param z                       Die z-Koordinate des LootContainers.
     * @param w                       Die Welt, in der sich der LootContainer befindet.
     * @param density                 Die Itemdichte.
     * @param spawnBlockEvenWhenEmpty Soll der Block erscheinen, auch, wenn der LootContainer leer spawnt?
     */
    public LootContainer(String lootClass, double pctSpawn, int x, int y, int z, World w, double density, boolean spawnBlockEvenWhenEmpty, String type)
    {
        this(lootClass, pctSpawn, new Location(w, x, y, z), density, spawnBlockEvenWhenEmpty, type);
    }

    /**
     * Bekomme die Loot-Klasse.
     *
     * @return Die Loot-Klasse.
     */
    public String getLootClass()
    {
        return _lootClass;
    }

    /**
     * Redefiniere die Lootklasse.
     *
     * @param lootclass Die neue Lootklasse.
     */
    public void setLootClass(String lootclass)
    {
        _lootClass = lootclass;
    }

    /**
     * Bekomme die Wahrscheinlichkeit, dass ein Loot-Container überhaupt spawnt.
     *
     * @return Die Wahrscheinlichkeit von 0.0 - 1.0.
     */
    public double getPctSpawn()
    {
        return _pctSpawn;
    }

    /**
     * Redefiniere die Spawnchance.
     *
     * @param newPctSpawn Die neue SpawnChance.
     */
    public void setPctSpawn(double newPctSpawn)
    {
        if (newPctSpawn > 1.0)
        {
            newPctSpawn = 1.0;
        }
        else if (newPctSpawn < 0.0)
        {
            newPctSpawn = 0.0;
        }
        _pctSpawn = newPctSpawn;
    }

    /**
     * Bekomme die Location, bei der sich der Loot-Container befindet.
     *
     * @return Die Location.
     */
    public Location getLocation()
    {
        return _location;
    }

    /**
     * Bekomme die Itemdichte des Loot-Containers.
     *
     * @return Die Itemdichte von 0.0 - 1.0.
     */
    public double getDensity()
    {
        return _density;
    }

    /**
     * Redefiniere die Dichtheit.
     *
     * @param newDensity Die neue Dichtheit.
     */
    public void setDensity(double newDensity)
    {
        if (newDensity > 1.0)
        {
            newDensity = 1.0;
        }
        else if (newDensity < 0.0)
        {
            newDensity = 0.0;
        }

        _density = newDensity;
    }

    /**
     * Bekomme den ContainerTyp des Loot-Containers.
     *
     * @return Der ContainerTyp.
     */
    public String getType()
    {
        return _type;
    }

    /**
     * Redefiniere die Dichtheit.
     *
     * @param type Der neue Typ.
     */
    public void setType(String type)
    {
        _type = type;
    }

    /**
     * Bekomme, ob der Block gesetzt wird, obwohl der LootContainer leer gespawnt wird.
     *
     * @return Wahr, wenn der Block gesetzt wird.
     */
    public boolean getAllowSetBlockWhenEmpty()
    {
        return _spawnBlockEvenWhenEmpty;
    }

    /**
     * Redifiniere, ob der LootContainer auch spawnen soll, wenn dieser leer gespawnt wird, oder nicht.
     *
     * @param b Die obengenannte Bedingung.
     */
    public void setAllowSetBlockWhenEmpty(boolean b)
    {
        _spawnBlockEvenWhenEmpty = b;
    }

    @Override
    public String toString()
    {
        /*
         * Format: "lootClass,pctSpawn,x,y,z,world,density,emptyspawn"
         * Exemp.: "medicine,0.25,123,12,-125,world,0.01,false"
         */
        return _lootClass + "," + _pctSpawn + "," + _location.getBlockX() + "," + _location.getBlockY() + "," + _location.getBlockZ() + "," + _location.getWorld().getName() + "," + _density + "," + _spawnBlockEvenWhenEmpty + "," + _type;
    }

    /**
     * Breche das Siegel des LootContainers.
     */
    public void breakSeal()
    {
        _sealed = false;
    }

    /**
     * Erneuere das Siegel des LootContainers.
     */
    public void seal()
    {
        _sealed = true;
    }

    /**
     * Prüfe, ob jemand den LootContainer bereits geöffnet hat, oder nicht.
     *
     * @return wahr, wenn die Seal gebrochen ist.
     */
    public boolean isSealed()
    {
        return _sealed;
    }

    /**
     * Prüfe, ob der LootContainer Luft ist, oder nicht.
     *
     * @return Wahr, wenn er platziert ist, also keine Luft ist und falsch, wenn nicht.
     */
    public boolean isPlaced()
    {
        return !_location.getBlock().getType().equals(Material.AIR);
    }

}
