package com.gegenphase.battleroyale.loot.lootclasses.services;

import com.gegenphase.battleroyale.loot.lootclasses.materialien.LootClass;

import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 15.09.2022
 **/
public interface ILootClassService
{
    /**
     * Bekomme die Lootklasse, die zum Namen passt. Case-Sensitive!
     * <p>
     * Die 'any'-Klasse liefert eine zufällige Klasse.
     * Die 'random'-Klasse liefert eine Klasse mit allen Items. (Sowas wie die Mengenvereinigung über alle Mengen, nur dass auch Duplikate erlaubt sind).
     * <p>
     * Die Random-Klasse ist in der 'any'-Klasse vertreten, sobald dies in der {@link com.gegenphase.battleroyale.config.MainConfig} explizit konfiguriert wurde.
     *
     * @param lc Der Name der Lootklasse.
     * @return Die Lootklasse.
     */
    public LootClass getLootClass(String lc);

    /**
     * Prüfe, ob eine Lootklasse im Bestand existiert oder nicht.
     *
     * @param lc Der Name der zu prüfenden Lootklasse.
     * @return Wahr, wenn sie im Bestand auftaucht und falsch wenn nicht.
     */
    public boolean exists(String lc);

    /**
     * Lade alle Lootklassen aus der 'lootclasses.yml' Datei.
     */
    public void load();

    /**
     * Bekomme alle gültigen Identifikatoren für Lootklassen.
     *
     * @return Eine Menge mit allen LootClass-Namen.
     */
    public Set<String> getLootClassKeys();
}
