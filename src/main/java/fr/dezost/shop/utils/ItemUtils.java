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

public class ItemUtils {

    public static ItemStack createGuiItem(Material material, String name, String... loreLines) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
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

    public static int getItemPrice(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
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

    public static int getItemCount(Player player, ItemStack item) {
        int count = 0;
        for (ItemStack is : player.getInventory().getContents()) {
            if (is != null && is.isSimilar(item)) {
                count += is.getAmount();
            }
        }
        return count;
    }

    public static void removeItems(Player player, ItemStack item, int quantity) {
        int remaining = quantity;
        for (ItemStack is : player.getInventory().getContents()) {
            if (is != null && is.isSimilar(item)) {
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

    public static void addItemLore(ItemStack item, String loreLine) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
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