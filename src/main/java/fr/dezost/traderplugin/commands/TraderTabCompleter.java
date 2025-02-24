package fr.dezost.traderplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class TraderTabCompleter implements TabCompleter {

    private static final String CREATE_COMMAND = "create";
    private static final String REMOVE_COMMAND = "remove";
    private static final String HELP_COMMAND = "help";

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {

            if ("trader".equalsIgnoreCase(command.getName())) {
                if (args[0].isEmpty()) {
                    completions.add(CREATE_COMMAND);
                    completions.add(REMOVE_COMMAND);
                    completions.add(HELP_COMMAND);
                } else {
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