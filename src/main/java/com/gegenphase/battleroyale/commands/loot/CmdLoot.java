package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.commands.loot.lcgen.LootSpreader;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class CmdLoot implements CommandExecutor
{
    /*
     * Klassenkonstanten
     */
    public static final String CMD_LOOT = "loot";

    /*
     * Feldvariablen
     */
    private final ILootContainerService _lootContainerService;
    private final ILootClassService _lootClassService;
    private final LootSpreader _spreader;

    /**
     * Konstruktor der Klasse CmdLoot.
     *
     * @param lootContainerService Das LootContainerService.
     * @param lootClassService     Das LootClassService.
     */
    public CmdLoot(ILootContainerService lootContainerService, ILootClassService lootClassService, LootSpreader spreader)
    {
        _spreader = spreader;
        _lootContainerService = lootContainerService;
        _lootClassService = lootClassService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = null;
        if (commandSender instanceof Player)
        {
            player = (Player) commandSender;
        }

        if (command.getName().equalsIgnoreCase(CMD_LOOT))
        {
            if (strings.length == 0)
            {
                Messages.showCommandUsage(player, "/loot <container | class | help>");
                return true;
            }

            /*
             * Erstes Argument <container | class | help>
             */
            switch (strings[0])
            {
                case "container" ->
                {
                    new CmdLootContainerSubWerkzeug(_lootClassService,_lootContainerService, _spreader).containerSubCommand(player, strings);
                }
                case "class" ->
                {
                    new CmdLootClassSubWerkzeug(_lootClassService).classSubCommand(player, strings);
                }
                case "help" ->
                {
                    Messages.showCommandUsage(player,"/loot <container | class | help>");
                    player.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "container " + Messages.F_NORMAL + " Bearbeite die LootContainer-Einstellungen. Ein LootContainer ist ein Container (bspw. Kiste oder Dispenser) in denen Gegenst??nde generiert werden k??nnen, die im Spiel dann gelootet werden k??nnen.");
                    player.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "class " + Messages.F_NORMAL + " Bearbeite die LootKlassen-Einstellungen. Eine Loot-Klasse ist eine spezielle Ansammlung von Gegenst??nden, die zuf??llig oder gezielt in LootContainern generiert werden. Klassen k??nnen zum Beispiel Bogenschuetze, Medizin, Nahrung, Muell oder ??hnliches sein.");
                    player.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "help " + Messages.F_NORMAL + " Zeige diese Hilfsseite an.");
                }
                default ->
                {
                    Messages.showCommandUsage(player,"/loot <container | class | help>");
                }
            }

            return true;
        }
        return false;
    }
}
