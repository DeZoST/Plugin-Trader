package fr.dezost.shop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides tab completion for the /trader command.
 */
public class TraderTabCompleter implements TabCompleter {

    private static final String CREATE_COMMAND = "create";
    private static final String REMOVE_COMMAND = "remove";
    private static final String HELP_COMMAND = "help";

    /**
     * Handles tab completion for the /trader command.
     *
     * @param sender  the source of the command
     * @param command the command which was executed
     * @param alias   the alias of the command which was used
     * @param args    the arguments passed to the command
     * @return a list of possible completions for the final argument, or null to
     *         default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // Check if the command is "trader" and if the first argument is being typed
        if (args.length == 1) {
            if ("trader".equalsIgnoreCase(command.getName())) {
                // If the first argument is empty, suggest all subcommands
                if (args[0].isEmpty()) {
                    completions.add(CREATE_COMMAND);
                    completions.add(REMOVE_COMMAND);
                    completions.add(HELP_COMMAND);
                } else {
                    // Suggest subcommands that start with the typed argument
                    if (CREATE_COMMAND.startsWith(args[0].toLowerCase()))
                        completions.add(CREATE_COMMAND);
                    if (REMOVE_COMMAND.startsWith(args[0].toLowerCase()))
                        completions.add(REMOVE_COMMAND);
                    if (HELP_COMMAND.startsWith(args[0].toLowerCase()))
                        completions.add(HELP_COMMAND);
                }
                return completions;
            }
        }
        return null;
    }
}