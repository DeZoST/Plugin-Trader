package fr.dezost.shop.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling ItemStack operations.
 */
public class ItemUtils {

    /**
     * Creates an ItemStack with the specified material, name, and lore.
     *
     * @param material  the material of the item
     * @param name      the display name of the item
     * @param loreLines the lore lines of the item
     * @return the created ItemStack
     */
    public static ItemStack createGuiItem(Material material, String name, String... loreLines) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (CheckUtils.hasItemMeta(item)) {
            meta.displayName(Component.text(name));
            List<Component> lore = new ArrayList<>();
            for (String loreLine : loreLines) {
                lore.add(Component.text(loreLine));
            }
            meta.lore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    /**
     * Gets the price of an item from its lore.
     *
     * @param item the item to get the price from
     * @return the price of the item, or 0 if no price is found
     */
    public static int getItemPrice(ItemStack item) {
        if (!CheckUtils.hasItemMeta(item)) {
            return 0;
        }
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();
        if (lore == null || lore.isEmpty()) {
            return 0;
        }
        for (Component line : lore) {
            String lineText = line.toString();
            if (lineText.startsWith(Constants.PRICE_PREFIX)) {
                try {
                    return Integer.parseInt(lineText.substring(Constants.PRICE_PREFIX.length()));
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Gets the count of a specific item in a player's inventory.
     *
     * @param player the player to check the inventory of
     * @param item   the item to count
     * @return the count of the item in the player's inventory
     */
    public static int getItemCount(Player player, ItemStack item) {
        int count = 0;
        for (ItemStack is : player.getInventory().getContents()) {
            if (CheckUtils.hasItemMeta(is) && is.isSimilar(item)) {
                count += is.getAmount();
            }
        }
        return count;
    }

    /**
     * Removes a specific quantity of an item from a player's inventory.
     *
     * @param player   the player to remove the items from
     * @param item     the item to remove
     * @param quantity the quantity to remove
     */
    public static void removeItems(Player player, ItemStack item, int quantity) {
        int remaining = quantity;
        for (ItemStack is : player.getInventory().getContents()) {
            if (CheckUtils.hasItemMeta(is) && is.isSimilar(item)) {
                int amount = is.getAmount();
                if (amount > remaining) {
                    is.setAmount(amount - remaining);
                    remaining = 0;
                } else {
                    player.getInventory().remove(is);
                    remaining -= amount;
                }
                if (remaining <= 0) {
                    break;
                }
            }
        }
    }

    /**
     * Adds a lore line to an item.
     *
     * @param item     the item to add the lore to
     * @param loreLine the lore line to add
     */
    public static void addItemLore(ItemStack item, String loreLine) {
        ItemMeta meta = item.getItemMeta();
        if (CheckUtils.hasItemMeta(item)) {
            List<Component> lore = new ArrayList<>();
            NamespacedKey loreKey = new NamespacedKey(Constants.PLUGIN_NAMESPACE, Constants.LORE_KEY);
            if (meta.getPersistentDataContainer().has(loreKey, PersistentDataType.STRING)) {
                String loreString = meta.getPersistentDataContainer().get(loreKey, PersistentDataType.STRING);
                if (loreString != null) {
                    for (String line : loreString.split("\n")) {
                        lore.add(Component.text(line));
                    }
                }
            }
            lore.add(Component.text(loreLine));
            meta.lore(lore);
            meta.getPersistentDataContainer().set(loreKey, PersistentDataType.STRING, String.join("\n", loreLine));
            item.setItemMeta(meta);
        }
    }
}