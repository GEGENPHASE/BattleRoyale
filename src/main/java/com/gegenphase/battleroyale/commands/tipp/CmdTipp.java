package com.gegenphase.battleroyale.commands.tipp;

import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author GEGENPHASE
 * @version 23.09.2022
 **/
public class CmdTipp implements CommandExecutor
{
    /*
     * Klassenkonstanten
     */
    public static final String CMD_TIPP = "tipp";

    /*
     * Feldvariablen
     */
    private final List<String> _tipps;

    public CmdTipp()
    {
        _tipps = new ArrayList<>();

        _tipps.add("Stimpaks sind Items, die dich heilen, wenn du sie in der Hand hälst und einen Rechtsklick machst.");
        _tipps.add("Abgelaufene Stimpaks stellen 2 Herzen wieder her. Normale hingegen 4.");
        _tipps.add("Waffen schießt du mit rechtsklick. Sie haben unendlich viel Munition.");
        _tipps.add("Während der DeathMatch-Phase hast du 2 Minuten Zeit in den WarGear-Park zu kommen, bevor dich der Sturm tötet.");
        _tipps.add("Alle 5 Minuten erscheinen Feuerwerksraketen bei LootContainern. Halte nach ihnen Ausschau.");
        _tipps.add("Nicht-geöffnete LootContainer machen Geräusche und erzeugen grüne Partikel.");
        _tipps.add("Leere LootContainer zerspringen, wenn die geschlossen werden.");
        _tipps.add("Stealh Boys machen dich für 30 Sekunden unsichtbar.");
        _tipps.add("Du bekommst in Phase 2 Unsichtbarkeit für 5 Minuten. Warte bis dahin mit dem Anziehen von Rüstung.");
        _tipps.add("Zum Spielbeginn generieren 4 Powerrüstungssets. Jedoch nur an der Oberfläche.");
        _tipps.add("Items haben ihren Verwendungszweck in der Beschreibung.");
        _tipps.add("LootContainer können auch in Gebäuden spawnen.");
        _tipps.add("Am Spawn erscheinen maximal 16 LootContainer.");
        _tipps.add("Powerrüstungen verlangsamen dich massiv.");
        _tipps.add("Du findest einen LootContainer nicht? Vielleicht ist er in einer Redstoneschaltung gespawnt. Dann bekommst du ihn nie.");
        _tipps.add("Die besten Items einer Sorte haben eine gelbe Namensfarbe.");
        _tipps.add("Benutze /tipp <Nummer>, um einen spezifischen Tipp anzusehen.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player p = null;
        if (commandSender instanceof Player)
        {
            p = (Player) commandSender;
        }

        if (command.getName().equalsIgnoreCase(CMD_TIPP))
        {
            int index = new Random().nextInt(_tipps.size()) + 1;

            if (strings.length != 0)
            {
                try
                {
                    index = Integer.parseInt(strings[0]);
                }
                catch (NumberFormatException e)
                {
                    p.sendMessage(Messages.PREFIX + "Der Wert '" + Messages.F_HIGHLIGHT + strings[0] + Messages.F_NORMAL + "' ist ungültig. Stelle sicher, dass du nur natürliche Zahlen als Nummer benutzt.");
                    return true;
                }
            }

            if (index > _tipps.size() || index < 1)
            {
                p.sendMessage(Messages.PREFIX + "Der Wert '" + Messages.F_HIGHLIGHT + strings[0] + Messages.F_NORMAL + "' ist ungültig. Stelle sicher, eine ganze Zahl zwischen " + Messages.F_HIGHLIGHT + "0" + Messages.F_NORMAL + " und " + Messages.F_HIGHLIGHT + _tipps.size() + Messages.F_NORMAL + " (inklusive jeweils) verwendest.");
                return true;
            }

            Messages.showTipp(p, _tipps.get(index - 1));
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 10.0f, 0.5f + (float) Math.random() * 0.5f);
            return true;
        }


        return false;
    }
}
