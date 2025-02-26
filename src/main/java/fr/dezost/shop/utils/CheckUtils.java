package fr.dezost.shop.utils;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CheckUtils {

    public static boolean isItemNullOrAir(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static boolean hasItemMeta(ItemStack item) {
        return item != null && item.hasItemMeta();
    }

    public static boolean hasItemLore(ItemMeta meta) {
        return meta != null && meta.hasLore();
    }

    public static boolean hasMetadata(Player player, String metadataKey) {
        return player.hasMetadata(metadataKey);
    }

    public static boolean isRecognizedInventory(String inventoryTitle) {
        return InventoryUtils.isRecognizedInventory(inventoryTitle);
    }

    public static boolean isClickedItemValid(ItemStack clickedItem) {
        return clickedItem != null && clickedItem.getType() != Material.AIR
                && clickedItem.getType() != Material.GRAY_STAINED_GLASS_PANE;
    }

    public static boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

    public static boolean isSenderPlayer(Object sender) {
        return sender instanceof Player;
    }

    public static void registerCommand(JavaPlugin plugin, String commandName, CommandExecutor executor,
            TabCompleter completer) {
        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(executor);
        Objects.requireNonNull(plugin.getCommand(commandName)).setTabCompleter(completer);
    }
}