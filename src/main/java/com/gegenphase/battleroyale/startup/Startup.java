package com.gegenphase.battleroyale.startup;

import com.gegenphase.battleroyale.commands.game.CmdGame;
import com.gegenphase.battleroyale.commands.loot.*;
import com.gegenphase.battleroyale.commands.loot.lcgen.LootContainerSpawner;
import com.gegenphase.battleroyale.commands.loot.lcgen.LootSpreader;
import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.death.DeathEvents;
import com.gegenphase.battleroyale.game.Game;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootclasses.services.LootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.gui.LootContainerEditorUIEvents;
import com.gegenphase.battleroyale.loot.lootcontainer.scheduler.LootContainerHighlighter;
import com.gegenphase.battleroyale.loot.lootcontainer.scheduler.LootContainerOpenEvents;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.loot.lootcontainer.services.LootContainerService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author GEGENPHASE
 * @version 10.09.2022
 **/
public class Startup extends JavaPlugin
{
    /*
     * Feldvariablen
     */
    private ILootContainerService _lootContainerService;
    private ILootClassService _lootClassService;
    private MainConfig _config;
    private LootSpreader _spreader;
    private LootContainerSpawner _spawner;

    @Override
    public void onEnable()
    {
        super.onEnable();

        /*
         * Services
         */
        _config = new MainConfig(this);
        _lootContainerService = new LootContainerService(this.getDataFolder());
        _lootClassService = new LootClassService(this.getDataFolder());

        _spawner = new LootContainerSpawner(_lootClassService);
        _spreader = new LootSpreader(_spawner, _lootContainerService);

        /*
         * Game
         */
        Game game = new Game(this, _lootContainerService, _lootClassService);

        /*
         * Commands
         */
        CmdLoot cmdLoot = new CmdLoot(_lootContainerService, _lootClassService, _spreader);
        CmdGame cmdGame = new CmdGame(game);

        this.getCommand(CmdLoot.CMD_LOOT).setExecutor(cmdLoot);
        this.getCommand(CmdGame.CMD_GAME).setExecutor(cmdGame);

        this.getCommand(CmdLoot.CMD_LOOT).setTabCompleter(new CmdLootTabCompleter());

        /*
         * Events
         */
        new LootContainerAddRemoveEvents(this, _lootContainerService);
        new LootContainerEditorUIEvents(this, _lootContainerService, _lootClassService);
        new LootContainerBreakEvents(this, _lootContainerService);
        new LootContainerOpenEvents(this, _lootContainerService);
        new DeathEvents(this, game);

        /*
         * Scheduler
         */
        new LootContainerHighlighter(this, _lootContainerService, game).startHighlighter();
        new LootContainerIndicator(this, _lootContainerService).startIndicatorScheduler();

        Bukkit.getLogger().info("[BattleRoyale] gestartet!");
    }

    @Override
    public void onDisable()
    {
        _lootContainerService.clearRandom();
        _lootContainerService.save();
        _config.saveConfig();
        super.onDisable();
    }
}
