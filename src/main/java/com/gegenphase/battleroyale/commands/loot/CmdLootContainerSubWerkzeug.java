package com.gegenphase.battleroyale.commands.loot;

import com.gegenphase.battleroyale.commands.loot.lcgen.LootSpreader;
import com.gegenphase.battleroyale.loot.generator.LootGeneratorService;
import com.gegenphase.battleroyale.loot.lootclasses.services.ILootClassService;
import com.gegenphase.battleroyale.loot.lootcontainer.services.ILootContainerService;
import com.gegenphase.battleroyale.util.items.Items;
import com.gegenphase.battleroyale.util.messages.Messages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * @author GEGENPHASE
 * @version 13.09.2022
 **/
public class CmdLootContainerSubWerkzeug
{
    /*
     * Feldvariablen
     */
    private final ILootClassService _lootClassService;
    private final ILootContainerService _lootContainerService;
    private final LootSpreader _spreader;

    /**
     * Konstruktor der Klasse CmdLootContainerSubWerkzeug.
     *
     * @param lootClassService     Das Loot-Klassen-Service.
     * @param lootContainerService Das Loot-Container-Service.
     */
    public CmdLootContainerSubWerkzeug(final ILootClassService lootClassService, final ILootContainerService lootContainerService, LootSpreader spreader)
    {
        _spreader = spreader;
        _lootClassService = lootClassService;
        _lootContainerService = lootContainerService;
    }

    /**
     * Behandele den Subcommand 'container'.
     *
     * @param p    Der Spieler, der den Befehl "/loot container" ausführt.
     * @param args Die Argumente, die der Spieler eingetippt hat.
     */
    public void containerSubCommand(Player p, String[] args)
    {
        /*
         * Prüfe, ob Anzahl der Argumente okay ist.
         */
        if (args.length < 2)
        {
            Messages.showCommandUsage(p, "/loot container <wand | generate | placeall | reload | load | help | spread>");
            return;
        }

        switch (args[1])
        {
            case "wand" ->
            {
                p.getInventory().addItem(Items.lootcontainerWand());
                p.sendMessage(Messages.PREFIX + "Du hast nun ein Werkzeug, um LootContainer zu bearbeiten.");
                p.sendMessage("");
                p.sendMessage(Messages.PREFIX + "Mache einen " + Messages.F_HIGHLIGHT + "Rechtsklick " + Messages.F_NORMAL + "auf einen gültigen Container, um diesen als LootContainer zu setzen.");
                p.sendMessage(Messages.PREFIX + "Mache einen " + Messages.F_HIGHLIGHT + "Shift Rechtsklick " + Messages.F_NORMAL + "auf einen registrierten LootContainer, um dessen Einstellungen einzusehen und zu bearbeiten.");
                p.sendMessage(Messages.PREFIX + "Mache einen " + Messages.F_HIGHLIGHT + "Linksklick " + Messages.F_NORMAL + "auf einen registrierten LootContainer, um diesen zu entfernen.");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "Werfe " + Messages.F_NORMAL + "das Tool aus deinem Inventar oder Hotbar, um alle registrierten LootContainer zu setzen.");
                // Sound
                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 10.0f, 1.0f);
            }
            case "generate" ->
            {
                new LootGeneratorService(_lootContainerService, _lootClassService).generateLoot();
                p.sendMessage(Messages.PREFIX + "Der Loot wurde nun in allen registrierten LootContainern generiert.");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 10.0f, 0.625f);
            }
            case "placeall" ->
            {
                _lootContainerService.placeAllLootContainers(false);
                p.sendMessage(Messages.PREFIX + "Alle LootContainer sind nun gesetzt worden.");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_SHROOMLIGHT_PLACE, 10.0f, 0.625f);
            }
            case "unplaceall" ->
            {
                _lootContainerService.placeAllLootContainers(true);
                p.sendMessage(Messages.PREFIX + "Alle LootContainer sind nun versteckt worden.");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_SHROOMLIGHT_PLACE, 10.0f, 0.625f);
            }
            case "save" ->
            {
                _lootContainerService.save();
                p.sendMessage(Messages.PREFIX + "Alle LootContainer sind gespeichert worden!");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10.0f, 0.625f);
            }
            case "load" ->
            {
                _lootContainerService.load();
                p.sendMessage(Messages.PREFIX + "Alle LootContainer sind von der Festplatte geladen und überschrieben worden!");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10.0f, 0.625f);
            }
            case "spread" ->
            {
                // /loot container spread amount radius
                if (args.length < 5)
                {
                    Messages.showCommandUsage(p, "/loot container spread <anzahl> <radius> <minimum Y> [maximum Y]");
                    return;
                }

                int maxY = -100;

                /*
                 * Werte einlesen & generieren.
                 */
                try
                {

                    int amount = Integer.parseInt(args[2]);
                    int radius = Integer.parseInt(args[3]);
                    int minY = Integer.parseInt(args[4]);

                    if (args.length >= 6)
                    {
                        maxY = Integer.parseInt(args[5]);
                    }

                    _spreader.spread(p.getLocation(), radius, amount, minY, maxY);

                    p.sendMessage(Messages.PREFIX + "Es wurden " + amount + " LootContainer generiert, sofern alle Einstellungen korrekt vorgenommen worden sind. Siehe Konsole!");
                }
                catch (NumberFormatException e)
                {
                    p.sendMessage(Messages.PREFIX + "'" + args[2] + "', '" + args[3] + "' und/oder '" + args[4] + "' sind keine natürlichen Zahlen!");
                    return;
                }

                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10.0f, 0.625f);
            }
            case "help" ->
            {
                Messages.showCommandUsage(p, "/loot container <wand | generate | placeall | save | load | help>");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "wand" + Messages.F_NORMAL + " Bekomme ein Tool als Item, um LootContainer zu erstellen, entfernen und zu bearbeiten. Das Tool funktioniert ohne weitere Befehle und kann direkt an LootContainern benutzt werden. Es zeigt mittels Partikeln die Präsenz eines LootContainers an.");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "generate" + Messages.F_NORMAL + " Generiere Loot in die LootContainer. Dabei werden die individuellen Einstellungen jeder LootContainer und die globalen Einstellungen in der Konfigurationsdatei 'config.yml' berücksichtigt.");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "placeall" + Messages.F_NORMAL + " Setze alle LootContainer. Abgebaute, oder nicht erzeugte LootContainer werden gesetzt und geleert. Alternativ kann auch das Tool unter '/loot container wand' fallengelassen werden.");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "save" + Messages.F_NORMAL + " Speichere alle aktuellen LootContainer. Überschreibe den Bestand in 'lootcontainers.csv' auf der Festplatte. Kann nicht rückgängig gemacht werden!");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "save" + Messages.F_NORMAL + " Lade alle LootContainer aus der Datei. Überschreibe den aktuell geladenen Bestand mit dem Bestand der Festplatte. Kann nicht rückgängig gemacht werden!");
                p.sendMessage(Messages.PREFIX + Messages.F_HIGHLIGHT + "help" + Messages.F_NORMAL + " Zeigt diese Seite an.");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10.0f, 0.625f);
            }
            case "removespread" ->
            {
                _spreader.removeAll();
                p.sendMessage(Messages.PREFIX + "Alle zufällig generierten LootContainer sind entfernt worden.");
                // Sound
                p.playSound(p.getLocation(), Sound.BLOCK_LEVER_CLICK, 10.0f, 0.625f);
            }
            default ->
            {
                Messages.showCommandUsage(p, "/loot container <wand | generate | placeall | save | load | help>");
            }
        }


    }

}
