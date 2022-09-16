package com.gegenphase.battleroyale.loot.lootclasses.services;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootClass;

import java.io.File;
import java.util.*;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootClassService implements ILootClassService
{
    /*
     * Feldvariablen
     */
    private Map<String, LootClass> _lootclasses;
    private final LootClassFileService _fileService;

    /**
     * Das LootClass-Service.
     */
    public LootClassService(File mainpath)
    {
        _fileService = new LootClassFileService(mainpath);
        _lootclasses = _fileService.load();
    }

    @Override
    public LootClass getLootClass(String lc)
    {
        if (lc.equalsIgnoreCase("any"))
        {
            List<String> lootclasses = new ArrayList<>(_lootclasses.keySet());

            if (!MainConfig.ALLOW_RANDOM_CLASS_TO_BE_CHOSEN_IN_ANY)
            {
                lootclasses.remove("random");
            }

            return _lootclasses.get(lootclasses.get(new Random().nextInt(0, lootclasses.size())));
        }

        return _lootclasses.get(lc);
    }

    @Override
    public boolean exists(String lc)
    {
        return lc.equals("any") || _lootclasses.containsKey(lc);
    }

    @Override
    public void load()
    {
        _lootclasses = _fileService.load();
    }

    @Override
    public Set<String> getLootClassKeys()
    {
        return new HashSet<>(_lootclasses.keySet());
    }

}
