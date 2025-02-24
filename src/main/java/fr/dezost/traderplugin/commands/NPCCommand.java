package fr.dezost.traderplugin.commands;

import fr.dezost.traderplugin.managers.NPCManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {
    private final NPCManager npcManager;

    public NPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande est utilisable seulement par les joueurs.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            npcManager.handleHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                if (player.hasPermission("trader.create")) {
                    npcManager.handleCreate(player, args);
                } else {
                    player.sendMessage("§cVous n'avez pas la permission de créer des PNJs.");
                }
                break;
            case "remove":
                if (player.hasPermission("trader.remove")) {
                    npcManager.handleRemove(player);
                } else {
                    player.sendMessage("§cVous n'avez pas la permission de supprimer des PNJs.");
                }
                break;
            case "help":
            default:
                npcManager.handleHelp(player);
                break;
        }
        return true;
    }
}