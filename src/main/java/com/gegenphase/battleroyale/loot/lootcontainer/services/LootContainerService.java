package com.gegenphase.battleroyale.loot.lootcontainer.services;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import javax.lang.model.type.UnionType;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class LootContainerService implements ILootContainerService
{
    /*
     * Feldvariablen
     */
    private Set<LootContainer> _lootContainers;
    private final ILootContainerFileService _fileService;

    /**
     * Konstruktor der Klasse LootContainerService.
     *
     * @param mainPath Der Hauptpfad zum Ordner des Plugins.
     */
    public LootContainerService(final File mainPath)
    {
        _fileService = new LootContainerFileService(mainPath);
        _lootContainers = _fileService.load();
    }

    @Override
    public void add(final LootContainer l)
    {
        _lootContainers.add(l);
    }

    @Override
    public void remove(final LootContainer l)
    {
        _lootContainers.remove(l);
    }

    @Override
    public void remove(final int x, final int y, final int z, final World w)
    {
        for (LootContainer l : _lootContainers)
        {
            if (l.getLocation().getBlockX() == x && l.getLocation().getBlockY() == y && l.getLocation().getBlockZ() == z && l.getLocation().getWorld().equals(w))
            {
                _lootContainers.remove(l);
                break;
            }
        }
    }

    @Override
    public void load()
    {
        _lootContainers = _fileService.load();
    }

    @Override
    public void save()
    {
        _fileService.save(new HashSet<>(_lootContainers));
    }

    @Override
    public Set<LootContainer> getLootContainers()
    {
        return new HashSet<LootContainer>(_lootContainers);
    }

    @Override
    public boolean isContainer(int x, int y, int z, World w)
    {
        for (LootContainer c : _lootContainers)
        {
            if (c.getLocation().getBlockX() == x && c.getLocation().getBlockY() == y && c.getLocation().getBlockZ() == z && c.getLocation().getWorld().equals(w))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public LootContainer getContainerAt(int x, int y, int z, World w)
    {
        for (LootContainer l : _lootContainers)
        {
            if (l.getLocation().getBlockX() == x && l.getLocation().getBlockY() == y && l.getLocation().getBlockZ() == z && l.getLocation().getWorld().equals(w))
            {
                return l;
            }
        }

        return null;
    }

    @Override
    public void placeAllLootContainers(boolean remove)
    {
        for (LootContainer l : _lootContainers)
        {
            // Bekomme die Welt und die Koordinaten.
            World w = l.getLocation().getWorld();
            int x = l.getLocation().getBlockX();
            int y = l.getLocation().getBlockY();
            int z = l.getLocation().getBlockZ();

            // (Er)setze den LC. Setze erstmal Luft, um die enthaltenen Gegenstände zu löschen und dann den LootContainer.
            w.getBlockAt(x, y, z).setType(Material.AIR);

            // Wenn remove false ist, dann setze, sonst nicht.
            if (!remove)
            {
                w.getBlockAt(x, y, z).setType(Material.valueOf(l.getType().toUpperCase()));
            }
        }
    }
}
