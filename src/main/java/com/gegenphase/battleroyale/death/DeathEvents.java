package com.gegenphase.battleroyale.death;

import com.gegenphase.battleroyale.game.Game;
import com.gegenphase.battleroyale.util.firework.FireWorkUtil;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * @author GEGENPHASE
 * @version 22.09.2022
 **/
public class DeathEvents implements Listener
{
    /*
     * Feldvariablen
     */
    private final Game _game;
    private final Plugin _pl;
    private final List<String> _deathmessages;

    /**
     * Konstruktor der Klasse {@link DeathEvents}.
     *
     * @param plugin Das Plugin.
     * @param game   Das Spiel.
     */
    public DeathEvents(Plugin plugin, Game game)
    {
        _pl = plugin;
        _game = game;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        _deathmessages = new ArrayList<>();
        _deathmessages.add("Scheint, als hätte jemand das Beste aus dir herausgeholt, besseres Glück nächstes mal! ... oder nicht, besser nicht.");
        _deathmessages.add("Das schien nicht so wie geplant zu laufen. Gab es überhaupt einen Plan?");
        _deathmessages.add("\"Wie kann man nur so oft daneben schießen?\" Dachten sie, als sie deine Leiche nach Beute durchsuchten.");
        _deathmessages.add("Also das sah schon zwischendurch recht gut aus, also für ein, zwei Minuten.");

    }

    @EventHandler
    public void onDamage(EntityDamageEvent evt)
    {
        if (!(evt.getEntity() instanceof Player p) || p.getHealth() - evt.getDamage() >= 1 || !_game.isRunning())
        {
            return;
        }

        showDeathAnimation(p);
        dropLoot(p);
        evt.setCancelled(true);
        potentiallyStopGame();
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent evt)
    {
        if (!_game.isRunning())
        {
            return;
        }

        Player player = evt.getPlayer();
        showDeathAnimation(player);
        potentiallyStopGame();

        Player killer = evt.getPlayer().getKiller();

        if (killer != null)
        {
            player.teleport(killer);

            player.setSpectatorTarget(null);
            player.teleport(killer);

            Bukkit.getScheduler().scheduleSyncDelayedTask(_pl, () ->
            {
                player.setSpectatorTarget(killer);
            }, 8);
        }

    }

    private void showDeathAnimation(Player p)
    {
        p.getInventory().clear();
        p.setGameMode(GameMode.SPECTATOR);
        Messages.showOverseerMessage(p, _deathmessages.get(new Random().nextInt(_deathmessages.size())));
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 10.0f, 0.95f);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 10.0f, 0.95f);
        p.setHealth(20);
        p.setFoodLevel(20);

        for (PotionEffect e : p.getActivePotionEffects())
        {
            p.removePotionEffect(e.getType());
        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 0, true));
        p.sendTitle("§4§lPlatz §c§l" + (getAmountOfAlivePlayers() + 1) + "§7§l/§c§l" + _game.getTotalAmountOfPlayers(), "§cDu bist gestorben!", 30, 40, 20);
        FireWorkUtil.spawnFireWork(p.getLocation());
        p.setVelocity(new Vector(0, 1.5, 0));

        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.sendMessage(Messages.F_OVERSEER + p.getName() + " ist gestorben!");
        }
    }

    private int getAmountOfAlivePlayers()
    {
        Set<Player> alives = new HashSet<>();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (p.getGameMode().equals(GameMode.ADVENTURE))
            {
                alives.add(p);
            }
        }
        return alives.size();
    }

    private Player getAlivePlayer()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getGameMode().equals(GameMode.ADVENTURE))
            {
                return player;
            }
        }
        return null;
    }

    private void potentiallyStopGame()
    {
        if (getAmountOfAlivePlayers() <= 1)
        {
            Player lastManStanding = getAlivePlayer();

            if (lastManStanding == null)
            {
                return;
            }

            lastManStanding.sendTitle("§a§lGewonnen!", "§2Du bist auf Platz §e§l#1");
            lastManStanding.setHealth(20);
            lastManStanding.playSound(lastManStanding.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10.0f, 1.2f);

            placeWinnersHead(lastManStanding);

            _game.stop();
        }
    }

    private void placeWinnersHead(Player winner)
    {
        // TODO: Methode zu fixed auf einen Anwendungsfall.
        if (winner == null)
        {
            return;
        }

        Block b = new Location(Bukkit.getWorld("world"), 397, 11, 701).getBlock();

        if (!b.getType().equals(Material.PLAYER_HEAD))
        {
            return;
        }

        Skull skull = (Skull) b.getState();
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(winner.getUniqueId()));
        skull.update();
    }

    private void dropLoot(Player p)
    {
        for (ItemStack itemStack : p.getInventory().getContents())
        {
            if (itemStack == null)
            {
                continue;
            }
            p.getLocation().getWorld().dropItem(p.getLocation(), itemStack);
        }
    }

}
