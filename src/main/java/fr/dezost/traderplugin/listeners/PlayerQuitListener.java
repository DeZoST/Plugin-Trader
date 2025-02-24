package fr.dezost.traderplugin.listeners;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.utils.Constants;
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
        if (player.hasMetadata(Constants.INTERACTING_NPC_METADATA)) {
            player.removeMetadata(Constants.INTERACTING_NPC_METADATA, plugin);
        }
        if (player.hasMetadata(Constants.SELECTED_ITEM_INDEX_METADATA)) {
            player.removeMetadata(Constants.SELECTED_ITEM_INDEX_METADATA, plugin);
        }
    }
}