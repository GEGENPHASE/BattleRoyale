package com.gegenphase.battleroyale.loot.lootcontainer.services;

import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class LootContainerFileService implements ILootContainerFileService
{
    /*
     * Klassenkonstanten
     */
    private final String CSV_SEPARATOR_DECLARATION = "\"sep=,\"";
    private final String CSV_FORMAT_HEADER = "Lootklasse,Spawnchance,X,Y,Z,Welt,Dichte,Leerspawn,Typ";

    /*
     * Feldvariablen
     */
    private final File _csvFile;

    /**
     * Konstruktor der Klasse LootContainerFileService.
     *
     * @param mainPath Der Hauptpfad des Plugins.
     */
    public LootContainerFileService(File mainPath)
    {
        _csvFile = new File(mainPath, "lootContainers.csv");

        mainPath.mkdirs();

        if (!_csvFile.exists())
        {
            try
            {
                _csvFile.createNewFile();

                try (PrintWriter pw = new PrintWriter(_csvFile))
                {
                    pw.println(CSV_SEPARATOR_DECLARATION);
                    pw.println(CSV_FORMAT_HEADER);
                }
                catch (Exception ignored)
                {
                }

            }
            catch (IOException e)
            {
                Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "Konnte die Datei 'lootContainers.csv' nicht erstellen!");
            }
        }
    }

    @Override
    public Set<LootContainer> load()
    {
        /*
         * Rohdaten einlesen.
         */
        Set<String> rawData = new HashSet<>();

        try (Scanner scn = new Scanner(_csvFile))
        {
            while (scn.hasNextLine())
            {
                String line = scn.nextLine();

                // Ersetze ';' durch ',', damit das Format stimmt.
                line = line.replace(";", ",");

                // Wenn Formatkopf oder Kategorienzeile, dann überspringe
                if (line.equals(CSV_FORMAT_HEADER) || line.equals(CSV_SEPARATOR_DECLARATION))
                {
                    continue;
                }

                rawData.add(line);
            }
        }
        catch (FileNotFoundException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "Konnte die Datei 'lootContainers.csv' nicht lesen, da die Datei nicht existiert oder der Zugriff verweigert wurde!");
        }

        /*
         * Daten umwandeln.
         */
        Set<LootContainer> lootContainers = new HashSet<>();

        for (String raw : rawData)
        {
            LootContainer toAdd = parseLootContainer(raw);

            if (toAdd != null)
            {
                lootContainers.add(toAdd);
            }
        }

        /*
         * Daten zurückgeben.
         */
        return lootContainers;
    }

    @Override
    public void save(Set<LootContainer> lootContainers)
    {
        /*
         * In Datei schreiben.
         */
        try (PrintWriter printWriter = new PrintWriter(_csvFile))
        {
            // Die Headers schreiben
            printWriter.println(CSV_SEPARATOR_DECLARATION);
            printWriter.println(CSV_FORMAT_HEADER);
            /*
             * LootContainer umwandeln und schreiben.
             */
            for (LootContainer lootContainer : lootContainers)
            {
                printWriter.println(lootContainer.toString());
            }
        }
        catch (FileNotFoundException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "Konnte aktuelle LootContainer nicht in die Datei 'lootContainers.csv' schreiben, da die Datei nicht existiert!");
        }
    }

    /*
     * Converter/Parser Section
     */
    private LootContainer parseLootContainer(final String raw)
    {
        String[] split = raw.split(",");

        if (split.length != 9)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Ein Lootcontainer weist ein ungültiges Format auf! (" + raw + ")");
            return null;
        }

        /*
         * Lootklasse
         */
        String lootClass = split[0];

        /*
         * Spawnchance
         */
        double pctSpawn = 0.0f;
        try
        {
            pctSpawn = Double.parseDouble(split[1]);
        }
        catch (NumberFormatException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Parsing fehlgeschlagen: '" + split[1] + "' ist keine gültige Gleitkommazahl! Wert auf 0.0 gesetzt. (" + raw + ").");
            pctSpawn = 0.0f;
        }

        /*
         * x-Koordinate
         */
        int x = 0;
        try
        {
            x = Integer.parseInt(split[2]);
        }
        catch (NumberFormatException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Parsing fehlgeschlagen: '" + split[2] + "' ist keine gültige Ganzzahl! Rettung unmöglich. (" + raw + ").");
            return null;
        }

        /*
         * y-Koordinate
         */
        int y = 0;
        try
        {
            y = Integer.parseInt(split[3]);
        }
        catch (NumberFormatException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Parsing fehlgeschlagen: '" + split[3] + "' ist keine gültige Ganzzahl! Rettung unmöglich. (" + raw + ").");
            return null;
        }

        /*
         * z-Koordinate
         */
        int z = 0;
        try
        {
            z = Integer.parseInt(split[4]);
        }
        catch (NumberFormatException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Parsing fehlgeschlagen: '" + split[4] + "' ist keine gültige Ganzzahl! Rettung unmöglich. (" + raw + ").");
            return null;
        }

        /*
         * Dichtheit
         */
        double density = 0.0f;
        try
        {
            density = Double.parseDouble(split[6]);
        }
        catch (NumberFormatException e)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Parsing fehlgeschlagen: '" + split[6] + "' ist keine gültige Gleitkommazahl! Wert wurde auf 0.0 gesetzt. (" + raw + ").");
            density = 0.0f;
        }

        /*
         * Welt
         */
        World world = Bukkit.getWorld(split[5]);
        if (world == null)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Parsing fehlgeschlagen: '" + split[5] + "' ist keine geladene Welt! Rettung unmöglich. (" + raw + ").");
            return null;
        }

        /*
         * Spawn
         */
        boolean spawnWhenEmpty = Boolean.parseBoolean(split[7]);

        /*
         * Typ
         */
        String type = split[8].toUpperCase();

        /*
         * Intervallkorrektur
         */
        if (density > 1)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Die Gegenstandsdichtheit liegt außerhalb des festgelegten Intervalls [0, 1]. Wert wurde auf 1.0 korrigiert. (" + raw + ")");
            density = 1.0f;
        }
        else if (density < 0)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Die Gegenstandsdichtheit liegt außerhalb des festgelegten Intervalls [0, 1]. Wert wurde auf 0.0 korrigiert. (" + raw + ")");
            density = 0.0f;
        }

        if (pctSpawn > 1)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Die Spawnchance liegt außerhalb des festgelegten Intervalls [0, 1]. Wert wurde auf 1.0 korrigiert. (" + raw + ")");
            pctSpawn = 1.0f;
        }
        else if (pctSpawn < 0)
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Die Spawnchance liegt außerhalb des festgelegten Intervalls [0, 1]. Wert wurde auf 0.0 korrigiert. (" + raw + ")");
            pctSpawn = 0.0f;
        }

        /*
         * Typkorrektur
         */
        if(!type.equals("CHEST") && !type.equals("DISPENSER"))
        {
            Bukkit.getLogger().warning(Messages.PREFIX_LOGGER + "!|!LootContainerParser!|! Der Containertyp ist ungültig. Es wurde auf einen DISPENSER korrigiert. (" + raw + ")");
            type = LootContainer.TYPE_DISPENSER;
        }

        /*
         * Rückgabe
         */
        return new LootContainer(lootClass, pctSpawn, x, y, z, world, density, spawnWhenEmpty, type);
    }

}
