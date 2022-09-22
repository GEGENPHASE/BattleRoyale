package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.config.MainConfig;
import com.gegenphase.battleroyale.loot.lootcontainer.gui.LootContainerEditorGUI;
import com.gegenphase.battleroyale.loot.lootcontainer.materialien.LootContainer;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.util.items.Items;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * @author GEGENPHASE
 * @version 11.09.2022
 **/
public class LootContainerAddRemoveEvents implements Listener
{
    /*
     * Feldvariablen
     */
    private final ILootContainerService _lootContainerService;

    /**
     * Konstruktor der Klasse LootContainerAddRemoveEvents.
     *
     * @param lootContainerService Das LootContainerService.
     */
    public LootContainerAddRemoveEvents(Plugin pl, ILootContainerService lootContainerService)
    {
        _lootContainerService = lootContainerService;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onBlockClickWithWand(PlayerInteractEvent evt)
    {
        // Daten sammeln.
        Player p = evt.getPlayer();
        ItemStack held = p.getInventory().getItemInMainHand();

        // brich ab, wenn das Item nicht gleich dem Wand ist, der Spieler nicht Rechtsklick auf Block macht, oder die Hand nicht gleich der Haupthand ist.
        if (!held.equals(Items.lootcontainerWand()) || !evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !evt.getHand().equals(EquipmentSlot.HAND))
        {
            return;
        }

        Block b = evt.getClickedBlock();

        // Wenn der Block kein gültiger Container ist.
        // TODO: Material Collection
        if (!(b.getType().equals(Material.CHEST) || b.getType().equals(Material.DISPENSER)))
        {
            evt.getPlayer().sendMessage(Messages.PREFIX + "LootContainer können nur Kisten oder Werfer sein!");
            return;
        }

        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();
        World w = b.getLocation().getWorld();

        // Wenn der Spieler sneakt, dann öffne das Editor-GUI
        if (p.isSneaking())
        {
            // Wenn der Block kein Container ist, gib Fehlermeldung.
            if (!_lootContainerService.isContainer(x, y, z, w))
            {
                evt.getPlayer().sendMessage(Messages.PREFIX + "Dort ist kein LootContainer!");
                return;
            }

            // Bekomme den Container und öffne das Menu.
            LootContainer lc = _lootContainerService.getContainerAt(x, y, z, w);
            LootContainerEditorGUI gui = new LootContainerEditorGUI(w.getName() + " " + x + " " + y + " " + z, lc);
            gui.openTo(p);
            return;
        }

        // Wenn nicht sneakt und bei x, y, z in der Welt w kein Container ist, erstelle einen.
        if (!_lootContainerService.isContainer(x, y, z, w))
        {
            // übernehme den Typ.
            String type = w.getBlockAt(x, y, z).getType().toString();

            // Erstelle den LootContainer
            LootContainer l = new LootContainer(MainConfig.DEFAULT_LOOTCLASS, MainConfig.DEFAULT_PCT_SPAWN, x, y, z, w, MainConfig.DEFAULT_DENSITY, MainConfig.DEFAULT_SPAWN_WHEN_EMPTY, type);
            evt.getPlayer().sendMessage(Messages.PREFIX + "LootContainer erstellt!");
            _lootContainerService.addDefined(l);
            return;
        }

        // Wenn ein Container vorhanden ist, informiere den Spieler.
        evt.getPlayer().sendMessage(Messages.PREFIX + "Dort ist bereits ein LootContainer registriert!");
    }

    @EventHandler
    public void onBlockBreakWithWand(BlockBreakEvent evt)
    {
        // Daten sammeln.
        Player p = evt.getPlayer();
        Block b = evt.getBlock();

        // Wenn der Spieler nicht das LC-Tool in der Hand hält
        if (!p.getInventory().getItemInMainHand().equals(Items.lootcontainerWand()))
        {
            return;
        }

        // Brich das Event ab, sobald der Spieler etwas mit dem LC-Tool abbaut.

        //evt.setCancelled(p.getInventory().getItemInMainHand().equals(Items.getLootContainerWand()));
        evt.setCancelled(true);

        // Bekomme die Blockinformationen
        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();
        World w = b.getLocation().getWorld();

        // Brich ab, wenn dort kein LC ist.
        if (!_lootContainerService.isContainer(x, y, z, w))
        {
            return;
        }

        // Entferne den LootContainer und informiere Benutzer.
        _lootContainerService.removeDefined(x, y, z, w);
        evt.getPlayer().sendMessage(Messages.PREFIX + "LootContainer entfernt!");
    }

    /*
     * @EventHandler
     * public void onInventoryOpenEventWithWand(InventoryOpenEvent evt)
     * {
     *   Player p = (Player) evt.getPlayer();
     *  ItemStack held = p.getInventory().getItemInMainHand();
     *
     *        if (held.equals(Items.getLootContainerWand()) && evt.getView().getTitle().equals(""))
     *       {
     *          evt.setCancelled(true);
     *     }
     *
     *    }
     */

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent evt)
    {
        // Wenn das Item, das gedropt wird, nicht der LC-Wand ist, dann brich ab.
        if (!evt.getItemDrop().getItemStack().equals(Items.lootcontainerWand()))
        {
            return;
        }

        // Setze alle Container.
        _lootContainerService.placeAllDefinedLootContainers();

        // Brich das Event ab.
        evt.setCancelled(true);
        evt.getPlayer().sendMessage(Messages.PREFIX + "Alle Lootcontainer wurden gesetzt!");
    }
}
