package fr.dezost.shop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.dezost.shop.managers.NPCManager;
import fr.dezost.shop.utils.Constants;

/**
 * Handles the /trader command and its subcommands for managing NPCs.
 */
public class NPCCommand implements CommandExecutor {
    private final NPCManager npcManager;

    /**
     * Constructs an NPCCommand with the specified NPCManager.
     *
     * @param npcManager the NPCManager to handle NPC-related actions
     */
    public NPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    /**
     * Executes the given command, returning its success.
     *
     * @param sender  the source of the command
     * @param command the command which was executed
     * @param label   the alias of the command which was used
     * @param args    the arguments passed to the command
     * @return true if the command was successful, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.ONLY_PLAYERS_MESSAGE);
            return true;
        }

        Player player = (Player) sender;

        // If no arguments are provided, show help
        if (args.length == 0) {
            npcManager.handleHelp(player);
            return true;
        }

        // Handle different subcommands
        switch (args[0].toLowerCase()) {
            case "create":
                // Check if the player has permission to create NPCs
                if (player.hasPermission("trader.create")) {
                    npcManager.handleCreate(player, args);
                } else {
                    player.sendMessage(Constants.NO_PERMISSION_CREATE_MESSAGE);
                }
                break;
            case "remove":
                // Check if the player has permission to remove NPCs
                if (player.hasPermission("trader.remove")) {
                    npcManager.handleRemove(player);
                } else {
                    player.sendMessage(Constants.NO_PERMISSION_REMOVE_MESSAGE);
                }
                break;
            case "help":
            default:
                // Show help if the subcommand is "help" or unrecognized
                npcManager.handleHelp(player);
                break;
        }
        return true;
    }
}