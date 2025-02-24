package fr.dezost.shop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dezost.shop.managers.NPCManager;
import fr.dezost.shop.utils.Constants;

public class NPCCommand implements CommandExecutor {
    private final NPCManager npcManager;

    public NPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.ONLY_PLAYERS_MESSAGE);
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
                    player.sendMessage(Constants.NO_PERMISSION_CREATE_MESSAGE);
                }
                break;
            case "remove":
                if (player.hasPermission("trader.remove")) {
                    npcManager.handleRemove(player);
                } else {
                    player.sendMessage(Constants.NO_PERMISSION_REMOVE_MESSAGE);
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