package fr.dezost.traderplugin.listeners;

import fr.dezost.traderplugin.TraderPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final TraderPlugin plugin;

    public PlayerQuitListener(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("interactingNPC")) {
            player.removeMetadata("interactingNPC", plugin);
        }
        if (player.hasMetadata("selectedItemIndex")) {
            player.removeMetadata("selectedItemIndex", plugin);
        }
    }
}
