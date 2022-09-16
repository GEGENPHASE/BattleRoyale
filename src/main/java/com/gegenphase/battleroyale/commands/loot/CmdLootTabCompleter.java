package com.gegenphase.battleroyale.commands.loot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class CmdLootTabCompleter implements TabCompleter
{
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {

        Player p = null;
        if (commandSender instanceof Player)
        {
            p = (Player) commandSender;
        }

        /*
         * Kommando : /loot
         */
        if (command.getName().equals(CmdLoot.CMD_LOOT))
        {
            List<String> list = new ArrayList<>();

            /*
             * /loot ...
             */
            if (strings.length == 1)
            {
                list.add("container");
                list.add("class");
                list.add("help");
            }

            /*
             * /loot container ...
             */
            if (strings.length == 2 && strings[0].equalsIgnoreCase("container"))
            {
                list.add("wand");
                list.add("generate");
                list.add("placeall");
                list.add("unplaceall");
                list.add("save");
                list.add("load");
                list.add("spread");
                list.add("removespread");
                list.add("help");
            }

            /*
             * /loot class ...
             */
            if (strings.length == 2 && strings[0].equalsIgnoreCase("class"))
            {
                list.add("load");
                list.add("help");
            }

            return list;
        }


        return null;
    }
}
