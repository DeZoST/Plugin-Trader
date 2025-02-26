package fr.dezost.shop.gui;

import net.kyori.adventure.text.Component;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dezost.shop.utils.CheckUtils;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;
import fr.dezost.shop.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI for setting the quantity of items.
 */
public class QuantitySettingGUI {
        private final JavaPlugin plugin;

        /**
         * Constructs a QuantitySettingGUI with the specified plugin.
         *
         * @param plugin the plugin instance
         */
        public QuantitySettingGUI(JavaPlugin plugin) {
                this.plugin = plugin;
        }

        /**
         * Creates the quantity setting GUI for the specified item.
         *
         * @param item the item to set the quantity for
         * @return the created quantity setting GUI
         */
        public Inventory createQuantitySettingGUI(ItemStack item) {
                Inventory gui = plugin.getServer().createInventory(null, 27,
                                net.kyori.adventure.text.Component.text(Constants.QUANTITY_GUI_TITLE));
                InventoryUtils.applyInventoryStyle(gui, Constants.QUANTITY_GUI_TITLE);
                setupQuantityAdjustmentButtons(gui, item);
                return gui;
        }

        /**
         * Opens the quantity setting GUI for the player.
         *
         * @param player the player to open the GUI for
         * @param item   the item to set the quantity for
         */
        public void openQuantitySettingGUI(Player player, ItemStack item) {
                Inventory quantityGUI = createQuantitySettingGUI(item);
                player.openInventory(quantityGUI);
        }

        /**
         * Adjusts the quantity of the specified item in the inventory.
         *
         * @param player     the player adjusting the quantity
         * @param inventory  the inventory containing the item
         * @param adjustment the adjustment to apply to the quantity
         */
        public void adjustQuantity(Player player, Inventory inventory, String adjustment) {
                InventoryUtils.adjustQuantity(player, inventory, adjustment);
        }

        /**
         * Sets up the quantity adjustment buttons in the GUI.
         *
         * @param gui  the GUI to set up the buttons in
         * @param item the item to set the quantity for
         */
        private void setupQuantityAdjustmentButtons(Inventory gui, ItemStack item) {
                if (CheckUtils.hasItemMeta(item)) {
                        ItemMeta meta = item.getItemMeta();
                        if (CheckUtils.hasItemLore(meta)) {
                                List<Component> lore = meta.lore();
                                List<Component> customLore = new ArrayList<>();
                                for (Component line : lore) {
                                        String lineText = line.toString();
                                        if (!lineText.contains(Constants.SELECT_ITEM_LORE)) {
                                                customLore.add(line);
                                        }
                                }
                                meta.lore(customLore);
                                item.setItemMeta(meta);
                        }
                }

                gui.setItem(10, ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_64_TEXT,
                                Constants.DECREASE_64_LORE));
                gui.setItem(11, ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_10_TEXT,
                                Constants.DECREASE_10_LORE));
                gui.setItem(12, ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_1_TEXT,
                                Constants.DECREASE_1_LORE));
                gui.setItem(13, item.clone());
                gui.setItem(14, ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_1_TEXT,
                                Constants.INCREASE_1_LORE));
                gui.setItem(15, ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_10_TEXT,
                                Constants.INCREASE_10_LORE));
                gui.setItem(16, ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_64_TEXT,
                                Constants.INCREASE_64_LORE));
                gui.setItem(22, ItemUtils.createGuiItem(Material.BOOK, Constants.INSTRUCTIONS_TEXT,
                                Constants.INSTRUCTIONS_LORE_1, Constants.INSTRUCTIONS_LORE_2));
                gui.setItem(26, ItemUtils.createGuiItem(Material.EMERALD_BLOCK, Constants.CONFIRM_TEXT,
                                Constants.CONFIRM_LORE));
        }
}