package com.gegenphase.battleroyale.commands.game;

import com.gegenphase.battleroyale.game.Game;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author GEGENPHASE
 * @version 15.09.2022
 **/
public class CmdGame implements CommandExecutor
{
    /*
     * Klassenkonstanten
     */
    public static final String CMD_GAME = "game";

    /*
     * Feldvariablen
     */
    private final Game _game;

    /**
     * Konstruktor der Klasse CMD_GAME.
     *
     * @param game Das aktuelle Game.
     */
    public CmdGame(Game game)
    {
        _game = game;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player p = null;
        if (commandSender instanceof Player)
        {
            p = (Player) commandSender;
        }


        if (command.getName().equalsIgnoreCase(CMD_GAME))
        {
            if (strings.length != 1)
            {
                return true;
            }

            switch (strings[0].toLowerCase())
            {
                case "start" ->
                {
                    if (!_game.isRunning())
                    {
                        _game.start();
                    }
                    else
                    {
                        p.sendMessage(Messages.PREFIX + "Aktuell läuft ein Spiel. Stoppe es mit " + Messages.F_HIGHLIGHT + "/game stop" + Messages.F_NORMAL + ".");
                    }
                }
                case "stop" ->
                {
                    if (_game.isRunning())
                    {
                        _game.stop();
                    }
                    else
                    {
                        p.sendMessage(Messages.PREFIX + "Aktuell läuft kein Spiel. Starte eins mit " + Messages.F_HIGHLIGHT + "/game start" + Messages.F_NORMAL + ".");
                    }
                }
                default ->
                {
                    Messages.showCommandUsage(p, "/game <start | stop>");
                }
            }

            return true;
        }

        return false;
    }
}
