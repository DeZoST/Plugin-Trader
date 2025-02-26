package fr.dezost.shop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.dezost.shop.ShopPlugin;
import fr.dezost.shop.utils.CheckUtils;
import fr.dezost.shop.utils.Constants;

/**
 * Listener for handling player quit events.
 * Cleans up metadata when a player quits the game.
 */
public class PlayerQuitListener implements Listener {
    private final ShopPlugin plugin;

    /**
     * Constructs a PlayerQuitListener with the specified plugin.
     *
     * @param plugin the plugin instance
     */
    public PlayerQuitListener(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the PlayerQuitEvent.
     * Removes metadata related to NPC interaction and selected item index when a
     * player quits.
     *
     * @param event the PlayerQuitEvent
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Remove metadata related to NPC interaction
        if (CheckUtils.hasMetadata(player, Constants.INTERACTING_NPC_METADATA)) {
            player.removeMetadata(Constants.INTERACTING_NPC_METADATA, plugin);
        }
        // Remove metadata related to selected item index
        if (CheckUtils.hasMetadata(player, Constants.SELECTED_ITEM_INDEX_METADATA)) {
            player.removeMetadata(Constants.SELECTED_ITEM_INDEX_METADATA, plugin);
        }
    }
}