package fr.dezost.traderplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class TraderTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {

            if ("trader".equalsIgnoreCase(command.getName())) {
                if (args[0].isEmpty()) {
                    completions.add("create");
                    completions.add("remove");
                    completions.add("help");
                } else {
                    if ("create".startsWith(args[0].toLowerCase()))
                        completions.add("create");
                    if ("remove".startsWith(args[0].toLowerCase()))
                        completions.add("remove");
                    if ("help".startsWith(args[0].toLowerCase()))
                        completions.add("help");
                }
                return completions;
            }
        }
        return null;
    }
}