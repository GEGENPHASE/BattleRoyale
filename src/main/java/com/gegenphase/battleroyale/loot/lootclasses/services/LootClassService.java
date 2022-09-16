package com.gegenphase.battleroyale.loot.lootclasses;

import com.gegenphase.battleroyale.config.MainConfig;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.lang.invoke.CallSite;
import java.util.*;

/**
 * @author GEGENPHASE
 * @version 12.09.2022
 **/
public class LootClassService
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

    /**
     * Bekomme eine LootKlasse von einem Namen.
     *
     * @param lc Die gesuchte LootKlasse.
     * @return Die LootKlasse als {@link LootClass}.
     */
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

    /**
     * Prüfe, ob es eine angegebene Lootklasse unter einem Namen gibt oder nicht.
     *
     * @param lc Die zu prüfende Lootklasse.
     * @return Wahr, wenn es diese Lootklasse im Bestand gibt und falsch wenn nicht. Gibt für "any" wahr zurück.
     */
    public boolean exists(String lc)
    {
        return lc.equals("any") || _lootclasses.containsKey(lc);
    }

    /**
     * Lade den Bestand von der Festplatte neu.
     */
    public void load()
    {
        _lootclasses = _fileService.load();
    }

    /**
     * Bekomme alle gültigen Lootklassennamen.
     *
     * @return Die Namen der Lootklassen.
     */
    public Set<String> getLootClassKeys()
    {
        return new HashSet<>(_lootclasses.keySet());
    }

}
