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
                                lore.add(0, Component.text("§ePrice: 5"));
                                meta.lore(lore);
                                item.setItemMeta(meta);
                        }
                }

                priceGUI.setItem(10,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, "§c§l-100",
                                                "§7Decrease price by 100"));
                priceGUI.setItem(11,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, "§c§l-10",
                                                "§7Decrease price by 10"));
                priceGUI.setItem(12,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, "§c§l-1",
                                                "§7Decrease price by 1"));
                priceGUI.setItem(13, item.clone());
                priceGUI.setItem(14,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "§a§l+1",
                                                "§7Increase price by 1"));
                priceGUI.setItem(15,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "§a§l+10",
                                                "§7Increase price by 10"));
                priceGUI.setItem(16,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "§a§l+100",
                                                "§7Increase price by 100"));
                priceGUI.setItem(22, ItemUtils.createGuiItem(Material.BOOK, "§a§lInstructions",
                                "§7Use + / - to adjust the price", "§7Click 'Confirm' to set the price"));
                priceGUI.setItem(26,
                                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, "§2§lConfirm",
                                                "§7Click to confirm the price"));
        }
}