package com.gegenphase.battleroyale.game;

import com.gegenphase.battleroyale.loot.generator.LootGeneratorService;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author GEGENPHASE
 * @version 15.09.2022
 **/
public class Game
{
    private final Plugin _pl;
    private final ILootContainerService _lootContainerService;
    private final ILootClassService _lootClassService;
    /*
     * Feldvariablen
     */
    private boolean _isRunning;
    private int _schedulerTask;
    private WorldBorder _wb;
    private int _playerAmount;

    /**
     * Konstruktor der Klasse {@link Game}.
     *
     * @param pl Das Plugin.
     */
    public Game(Plugin pl, ILootContainerService lootContainerService, ILootClassService lootClassService)
    {
        _lootContainerService = lootContainerService;
        _lootClassService = lootClassService;

        _wb = Bukkit.getWorld("world").getWorldBorder();

        _isRunning = false;
        _pl = pl;
        _playerAmount = -1;
    }

    /**
     * Starte ein Game.
     */
    public void start()
    {
        if (_isRunning)
        {
            return;
        }
        _isRunning = true;
        _playerAmount = getPlayerAmount();

        _schedulerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(_pl, new Runnable()
        {
            int counter = 1;
            int phase = 0;

            @Override
            public void run()
            {
                counter--;

                if (counter == 0)
                {
                    phase++;

                    if (phase == 1)
                    {
                        _wb.setCenter(392, 697);
                        _wb.setSize(20);
                        _lootContainerService.unplaceAll();
                        //teleportAllTo(_wb.getCenter().getBlockX(), _wb.getWorld().getHighestBlockYAt(_wb.getCenter().getBlockX(), _wb.getCenter().getBlockZ()), _wb.getCenter().getBlockZ());
                        teleportAllTo(391, 11, 696);
                        setGameMode();
                        clearAll();
                        removeDroppedItems(_wb.getWorld());
                        sendTitle("§9Phase §9§l1§8/§9§l3", "§8[§9§lBereit werden§8]");
                        counter = 15;
                    }
                    else if (phase == 2)
                    {
                        _wb.setCenter(392, 697);
                        _wb.setSize(1000, 2 * 60);
                        playStartMusic();
                        giveStartEffects();
                        new LootGeneratorService(_lootContainerService, _lootClassService).generateLoot();
                        sendTitle("§9Phase §9§l2§8/§9§l3", "§8[§9§lLooting und Equipping§8]");
                        counter = 20 * 60;
                    }
                    else if (phase == 3)
                    {
                        _wb.setCenter(392, 697);
                        _wb.setSize(100, 3 * 60);
                        sendTitle("§9Phase §9§l3§8/§9§l3", "§8[§9§lDeathmatch§8]");
                        stop();
                    }
                }

                displayTime(counter);
            }

        }, 20, 20);

    }

    /**
     * Stoppe das Spiel.
     */
    public void stop()
    {
        if (_isRunning)
        {
            _playerAmount = -1;
            Bukkit.getScheduler().cancelTask(_schedulerTask);
            _isRunning = false;
        }
    }

    /**
     * Bekomme die Anzahl der Mitspieler, die sich für das Spiel qualifiziert haben.
     * <p>
     * Gibt -1 zurück, wenn das Spiel nicht gestartet wurde.
     *
     * @return Die Anzahl aller Teilnehmer.
     */
    public int getTotalAmountOfPlayers()
    {
        return _playerAmount;
    }

    private void displayTime(int current)
    {
        String h = String.valueOf((current / 3600) % 60).length() == 1 ? "0" + (current / 3600) % 60 : String.valueOf((current / 3600) % 60);
        String min = String.valueOf((current / 60) % 60).length() == 1 ? "0" + (current / 60) % 60 : String.valueOf((current / 60) % 60);
        String sek = String.valueOf(current % 60).length() == 1 ? "0" + current % 60 : String.valueOf(current % 60);

        String color = current <= 10 ? "§c§l" : "§7§l";

        String display = h + ":" + min + ":" + sek;

        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color + display));

            if (current % 300 == 0 && current != 0)
            {
                //p.sendMessage(Messages.PREFIX + color + display);
                p.sendTitle(color + display, color + "Zeit übrig", 10, 60, 10);
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 10.0f, 1.25f);
                _lootContainerService.fireWorkUnsealed();
            }

            if (current <= 3)
            {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10.0f, 1.0f);
            }
        }
    }

    private void sendTitle(String title, String subtitle)
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 10.0f, 0.625f);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 10.0f, 0.725f);
            p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 10.0f, 1.0f);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10.0f, 0.9f);
            p.sendTitle(title, subtitle, 10, 60, 10);
        }
    }

    /**
     * Ist das Spiel gerade aktiv?
     *
     * @return Wahr, wenn obengenannte Bedingung zutrifft und falsch wenn nicht.
     */
    public boolean isRunning()
    {
        return _isRunning;
    }

    private void giveStartEffects()
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 60 * 5, 0, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 10, 10, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 10, 10, true));
        }
    }

    private void teleportAllTo(int x, int y, int z)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.teleport(new Location(_wb.getWorld(), x, y, z));
        }
    }

    private void setGameMode()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            //if(!player.getGameMode().equals(GameMode.CREATIVE))
            player.setGameMode(GameMode.ADVENTURE);
        }
    }

    private void clearAll()
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            for (PotionEffect e : p.getActivePotionEffects())
            {
                p.removePotionEffect(e.getType());
            }
            p.getInventory().clear();
        }
    }

    private void removeDroppedItems(World world)
    {
        for (Entity e : world.getEntities())
        {
            if (e.getType().equals(EntityType.DROPPED_ITEM))
            {
                e.remove();
            }
        }
    }

    private int getPlayerAmount()
    {
        return Bukkit.getOnlinePlayers().size();
    }

    private void playStartMusic()
    {
        Location location = _wb.getCenter();
        World w = _wb.getWorld();

        int x = location.getBlockX();
        int z = location.getBlockZ();
        int y = w.getHighestBlockYAt(x,z);

        w.playSound(new Location(w,x,y,z),Sound.MUSIC_DISC_CHIRP,0.5f,2.0f);

    }

}
