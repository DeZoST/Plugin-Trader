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

public class QuantitySettingGUI {
        private final JavaPlugin plugin;

        public QuantitySettingGUI(JavaPlugin plugin) {
                this.plugin = plugin;
        }

        public Inventory createQuantitySettingGUI(ItemStack item) {
                Inventory gui = plugin.getServer().createInventory(null, 27,
                                net.kyori.adventure.text.Component.text(Constants.QUANTITY_GUI_TITLE));
                InventoryUtils.applyInventoryStyle(gui, Constants.QUANTITY_GUI_TITLE);
                setupQuantityAdjustmentButtons(gui, item);
                return gui;
        }

        public void openQuantitySettingGUI(Player player, ItemStack item) {
                Inventory quantityGUI = createQuantitySettingGUI(item);
                player.openInventory(quantityGUI);
        }

        public void adjustQuantity(Player player, Inventory inventory, String adjustment) {
                InventoryUtils.adjustQuantity(player, inventory, adjustment);
        }

        private void setupQuantityAdjustmentButtons(Inventory gui, ItemStack item) {

                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                        List<Component> lore = meta.lore();
                        if (lore != null) {
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

                gui.setItem(10,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_64_TEXT,
                                                Constants.DECREASE_64_LORE));
                gui.setItem(11,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_10_TEXT,
                                                Constants.DECREASE_10_LORE));
                gui.setItem(12, ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, Constants.DECREASE_1_TEXT,
                                Constants.DECREASE_1_LORE));
                gui.setItem(13, item.clone());
                gui.setItem(14,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_1_TEXT,
                                                Constants.INCREASE_1_LORE));
                gui.setItem(15,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_10_TEXT,
                                                Constants.INCREASE_10_LORE));
                gui.setItem(16,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, Constants.INCREASE_64_TEXT,
                                                Constants.INCREASE_64_LORE));
                gui.setItem(22, ItemUtils.createGuiItem(Material.BOOK, Constants.INSTRUCTIONS_TEXT,
                                Constants.INSTRUCTIONS_LORE_1, Constants.INSTRUCTIONS_LORE_2));
                gui.setItem(26,
                                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, Constants.CONFIRM_TEXT,
                                                Constants.CONFIRM_LORE));
        }
}