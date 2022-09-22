package com.gegenphase.battleroyale.loot.lootcontainer.services;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.util.firework.FireWorkUtil;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class LootContainerService implements ILootContainerService
{
    private final Set<LootContainer> _randomLootContainers;
    private final ILootContainerFileService _fileService;
    /*
     * Feldvariablen
     */
    private Set<LootContainer> _lootContainers;

    /**
     * Konstruktor der Klasse LootContainerService.
     *
     * @param mainPath Der Hauptpfad zum Ordner des Plugins.
     */
    public LootContainerService(final File mainPath)
    {
        _randomLootContainers = new HashSet<>();
        _fileService = new LootContainerFileService(mainPath);
        _lootContainers = _fileService.load();
    }

    @Override
    public void addDefined(final LootContainer l)
    {
        _lootContainers.add(l);
    }

    @Override
    public void addRandom(LootContainer l)
    {
        _randomLootContainers.add(l);
    }

    @Override
    public void clearRandom()
    {
        for (LootContainer l : _randomLootContainers)
        {
            l.getLocation().getBlock().setType(Material.AIR);
        }

        _randomLootContainers.clear();
    }

    @Override
    public void removeDefined(final int x, final int y, final int z, final World w)
    {
        for (LootContainer l : getAllContainers())
        {
            if (l.getLocation().getBlockX() == x && l.getLocation().getBlockY() == y && l.getLocation().getBlockZ() == z && l.getLocation().getWorld().equals(w))
            {
                if (_randomLootContainers.contains(l))
                {
                    _randomLootContainers.remove(l);
                }
                else
                {
                    _lootContainers.remove(l);
                }
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
    public Set<LootContainer> getDefinedLootContainers()
    {
        return new HashSet<LootContainer>(_lootContainers);
    }

    @Override
    public Set<LootContainer> getRandomLootContainers()
    {
        return _randomLootContainers;
    }

    @Override
    public boolean isContainer(int x, int y, int z, World w)
    {
        for (LootContainer c : getAllContainers())
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
        for (LootContainer l : getAllContainers())
        {
            if (l.getLocation().getBlockX() == x && l.getLocation().getBlockY() == y && l.getLocation().getBlockZ() == z && l.getLocation().getWorld().equals(w))
            {
                return l;
            }
        }

        return null;
    }

    @Override
    public void placeAllDefinedLootContainers()
    {
        for (LootContainer l : _lootContainers)
        {
            // Erneuere das Siegel
            l.seal();

            // Bekomme die Welt und die Koordinaten.
            World w = l.getLocation().getWorld();
            int x = l.getLocation().getBlockX();
            int y = l.getLocation().getBlockY();
            int z = l.getLocation().getBlockZ();

            // (Er)setze den LC. Setze erstmal Luft, um die enthaltenen Gegenstände zu löschen und dann den LootContainer.
            w.getBlockAt(x, y, z).setType(Material.AIR);
            w.getBlockAt(x, y, z).setType(Material.valueOf(l.getType().toUpperCase()));
        }
    }

    @Override
    public void unplaceAll()
    {
        for (LootContainer l : getAllContainers())
        {
            l.seal();
            l.getLocation().getBlock().setType(Material.AIR);
        }
    }

    @Override
    public Set<LootContainer> getAllContainers()
    {
        Set<LootContainer> container = new HashSet<>(_lootContainers);
        container.addAll(_randomLootContainers);

        return container;
    }

    /*
     * FireWork TODO: Move somewhere else
     */

    @Override
    public void fireWorkUnsealed()
    {
        for (LootContainer l : getAllContainers())
        {
            if (l.isPlaced())
            {
                FireWorkUtil.spawnFireWork(l.getLocation());
            }
        }
    }
}
