package fr.dezost.traderplugin.listeners;

import fr.dezost.traderplugin.TraderPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final TraderPlugin plugin;

    private static final String INTERACTING_NPC_METADATA = "interactingNPC";
    private static final String SELECTED_ITEM_INDEX_METADATA = "selectedItemIndex";

    public PlayerQuitListener(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata(INTERACTING_NPC_METADATA)) {
            player.removeMetadata(INTERACTING_NPC_METADATA, plugin);
        }
        if (player.hasMetadata(SELECTED_ITEM_INDEX_METADATA)) {
            player.removeMetadata(SELECTED_ITEM_INDEX_METADATA, plugin);
        }
    }
}