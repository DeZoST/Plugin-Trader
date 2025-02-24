package fr.dezost.traderplugin.gui;

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

        public static final String PRICE_GUI_TITLE = "§6§lSet Price for Item";
        private static final String DECREASE_100_TEXT = "§c§l-100";
        private static final String DECREASE_10_TEXT = "§c§l-10";
        private static final String DECREASE_1_TEXT = "§c§l-1";
        private static final String INCREASE_1_TEXT = "§a§l+1";
        private static final String INCREASE_10_TEXT = "§a§l+10";
        private static final String INCREASE_100_TEXT = "§a§l+100";
        private static final String INSTRUCTIONS_TEXT = "§a§lInstructions";
        private static final String CONFIRM_TEXT = "§2§lConfirm";
        private static final String DECREASE_100_LORE = "§7Decrease price by 100";
        private static final String DECREASE_10_LORE = "§7Decrease price by 10";
        private static final String DECREASE_1_LORE = "§7Decrease price by 1";
        private static final String INCREASE_1_LORE = "§7Increase price by 1";
        private static final String INCREASE_10_LORE = "§7Increase price by 10";
        private static final String INCREASE_100_LORE = "§7Increase price by 100";
        private static final String INSTRUCTIONS_LORE_1 = "§7Use + / - to adjust the price";
        private static final String INSTRUCTIONS_LORE_2 = "§7Click 'Confirm' to set the price";
        private static final String CONFIRM_LORE = "§7Click to confirm the price";
        private static final String DEFAULT_PRICE_LORE = "§ePrice: 5";

        public void openPriceSettingGUI(Player player, ItemStack item) {
                Inventory priceGUI = createPriceSettingInventory(item);
                player.openInventory(priceGUI);
        }

        private Inventory createPriceSettingInventory(ItemStack item) {
                Inventory priceGUI = plugin.getServer().createInventory(null, 27,
                                net.kyori.adventure.text.Component.text(PRICE_GUI_TITLE));
                populatePriceSettingInventory(priceGUI, item);
                return priceGUI;
        }

        private void populatePriceSettingInventory(Inventory priceGUI, ItemStack item) {
                InventoryUtils.applyInventoryStyle(priceGUI, PRICE_GUI_TITLE);

                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                        List<Component> lore = meta.lore();
                        if (lore == null) {
                                lore = new ArrayList<>();
                        }
                        if (lore.isEmpty() || !lore.get(0).toString().contains("Price: ")) {
                                lore.add(0, Component.text(DEFAULT_PRICE_LORE));
                                meta.lore(lore);
                                item.setItemMeta(meta);
                        }
                }

                priceGUI.setItem(10,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, DECREASE_100_TEXT,
                                                DECREASE_100_LORE));
                priceGUI.setItem(11,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, DECREASE_10_TEXT,
                                                DECREASE_10_LORE));
                priceGUI.setItem(12,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, DECREASE_1_TEXT,
                                                DECREASE_1_LORE));
                priceGUI.setItem(13, item.clone());
                priceGUI.setItem(14,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, INCREASE_1_TEXT,
                                                INCREASE_1_LORE));
                priceGUI.setItem(15,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, INCREASE_10_TEXT,
                                                INCREASE_10_LORE));
                priceGUI.setItem(16,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, INCREASE_100_TEXT,
                                                INCREASE_100_LORE));
                priceGUI.setItem(22, ItemUtils.createGuiItem(Material.BOOK, INSTRUCTIONS_TEXT,
                                INSTRUCTIONS_LORE_1, INSTRUCTIONS_LORE_2));
                priceGUI.setItem(26,
                                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, CONFIRM_TEXT, CONFIRM_LORE));
        }
}