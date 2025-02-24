package fr.dezost.traderplugin.gui;

import fr.dezost.traderplugin.utils.Constants;
import fr.dezost.traderplugin.utils.InventoryUtils;
import fr.dezost.traderplugin.utils.ItemUtils;
import net.kyori.adventure.text.Component;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PriceSettingGUI {
        private final JavaPlugin plugin;

        public PriceSettingGUI(JavaPlugin plugin) {
                this.plugin = plugin;
        }

        public void openPriceSettingGUI(Player player, ItemStack item) {
                Inventory priceGUI = createPriceSettingInventory(item);
                player.openInventory(priceGUI);
        }

        private Inventory createPriceSettingInventory(ItemStack item) {
                Inventory priceGUI = plugin.getServer().createInventory(null, 27,
                                net.kyori.adventure.text.Component.text(Constants.PRICE_GUI_TITLE));
                populatePriceSettingInventory(priceGUI, item);
                return priceGUI;
        }

        private void populatePriceSettingInventory(Inventory priceGUI, ItemStack item) {
                InventoryUtils.applyInventoryStyle(priceGUI, Constants.PRICE_GUI_TITLE);

                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                        List<Component> lore = meta.lore();
                        if (lore == null) {
                                lore = new ArrayList<>();
                        }
                        if (lore.isEmpty() || !lore.get(0).toString().contains(Constants.PRICE_PREFIX)) {
                                lore.add(0, Component.text(Constants.DEFAULT_PRICE_LORE));
                                meta.lore(lore);
                                item.setItemMeta(meta);
                        }
                }

                priceGUI.setItem(10,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_100_TEXT,
                                                Constants.DECREASE_100_LORE));
                priceGUI.setItem(11,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_10_TEXT,
                                                Constants.DECREASE_10_LORE));
                priceGUI.setItem(12,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_1_TEXT,
                                                Constants.DECREASE_1_LORE));
                priceGUI.setItem(13, item.clone());
                priceGUI.setItem(14,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_1_TEXT,
                                                Constants.INCREASE_1_LORE));
                priceGUI.setItem(15,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_10_TEXT,
                                                Constants.INCREASE_10_LORE));
                priceGUI.setItem(16,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_100_TEXT,
                                                Constants.INCREASE_100_LORE));
                priceGUI.setItem(22, ItemUtils.createGuiItem(Material.BOOK, Constants.INSTRUCTIONS_TEXT,
                                Constants.INSTRUCTIONS_LORE_1, Constants.INSTRUCTIONS_LORE_2));
                priceGUI.setItem(26,
                                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, Constants.CONFIRM_TEXT,
                                                Constants.CONFIRM_LORE));
        }
}