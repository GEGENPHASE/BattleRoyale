package com.gegenphase.battleroyale.commands.loot.lcgen;

import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author GEGENPHASE
 * @version 18.09.2022
 * <p>
 * Der LootSpreader Verteilt LootContainer um einen gewissen Punkt herum.
 * Der LootContainerSpawner setzt dort einen LootContainer und füllt diesen Mit Loot auf.
 * <p>
 * Die Zuständigkeit des Spreaders beschränkt sich dahingehend, dass dieser nur für die Verteilung zuständig ist
 * und nicht für das auffüllen des LootContainers. Dafür ist der Spawner da.
 **/
public class LootSpreader
{
    /*
     * Feldvariablen
     */
    private final LootContainerSpawner _lootContainerSpawner;
    private final Set<Location> _lootContainers;

    /**
     * Konstruktor der Klasse LootSpreader.
     *
     * @param lootContainerSpawner Der LootContainer Spawner.
     */
    public LootSpreader(LootContainerSpawner lootContainerSpawner)
    {
        _lootContainerSpawner = lootContainerSpawner;
        _lootContainers = new HashSet<>();
    }

    /**
     * Verteile LootContainer um einen Punkt herum.
     *
     * @param l      Die Location des Mittelpunktes.
     * @param radius Der Radius.
     * @param amount Die Menge.
     * @param minY   Die Mindesthöhe.
     * @param maxY Die Maximalhöhe
     */
    public void spread(final Location l, final int radius, final int amount, final int minY, int maxY)
    {
        int centerX = l.getBlockX();
        int centerZ = l.getBlockZ();

        for (int i = 0; i < amount; i++)
        {
            spreadSingle(l.getWorld(), centerX, centerZ, minY, maxY, radius, 10);
        }
    }

    private void spreadSingle(final World w, final int centerX, final int centerZ, final int minY, final int maxY, final int radius, final int tries)
    {
        /*
         * Brich ab, wenn nach 10 Veruschen nichts gefunden wurde.
         */
        if (tries == 0)
        {
            Bukkit.getLogger().info(Messages.PREFIX_LOGGER + "(LootSpreader) Es konnte keine passende y-Koordinate generiert werden. Ist die mindest-y-Koordinate tief genug gewählt?");
            return;
        }

        /*
         * x und z Koordinate generieren
         */
        int x = getRandomCoord(centerX, radius);
        int z = getRandomCoord(centerZ, radius);
        int y = findYCoordinate(w, x, z, minY, maxY);

        /*
         * Wurde keine zulässige y-Koordinate gefunden, so brich ab und generiere neue Koordinaten.
         */
        if (y == minY - 1)
        {
            spreadSingle(w, centerX, centerZ, minY, maxY, radius, tries - 1);
            return;
        }

        _lootContainers.add(new Location(w, x, y, z));
        _lootContainerSpawner.spawnLootContainer(x, y, z, w);

    }

    private int findYCoordinate(final World w, final int x, final int z, final int minY, final int maxY)
    {
        /*
         * Falls eine y-Koordinate generiert wird, so dass bei (x, y, z)^t keine Luft ist gehe solange runter, bis der
         * erste Luftblock gefunden wird. Gehe anschließend so weit runter bis wieder ein Block != Luft gefunden wird
         * und nimm dann die vorherige y-Koordinate (wo sozusagen der letzte Luftblock ist).
         *
         * Brich ab, sobald die mindest-y-Koodinate erreicht und unterschritten wird. Gib die Mindest-y-Koordinate - 1
         * zurück.
         *
         * Wähle die y-Koordinate nicht durch y € [minY, 320] sondern durch a := höchster Block bei x, z in Welt w
         * mit y € [yMin, a]
         */
        boolean hasFoundAir = false;
        int upperBound = maxY <= -100 ? w.getHighestBlockYAt(x,z) + 5 : maxY;
        int y = new Random().nextInt(minY, upperBound);

        while (w.getBlockAt(x, y, z).getType().equals(Material.AIR) || !hasFoundAir)
        {
            /*
             * Wenn Luft gefunden, dann setze den Boolean auf wahr.
             */
            if (w.getBlockAt(x, y, z).getType().equals(Material.AIR))
            {
                hasFoundAir = true;
            }

            y--;

            if (y < minY)
            {
                return minY - 1;
            }
        }

        return y + 1;
    }

    private int getRandomCoord(final int center, final int radius)
    {
        /*
         * Generiere eine zufallskoodinate x im Intervall [center - radius, center + radius)
         */
        return new Random().nextInt(center - radius, center + radius);
    }

    /**
     * Entferne alle zufällig-platzierten LootContainer.
     */
    public void removeAll()
    {
        for (Location l : _lootContainers)
        {
            l.getBlock().setType(Material.AIR);
        }
    }
}
