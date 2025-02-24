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

public class QuantitySettingGUI {
        private final JavaPlugin plugin;

        public QuantitySettingGUI(JavaPlugin plugin) {
                this.plugin = plugin;
        }

        public static final String QUANTITY_GUI_TITLE = "§6§lSelect Quantity";

        public Inventory createQuantitySettingGUI(ItemStack item) {
                Inventory gui = plugin.getServer().createInventory(null, 27,
                                net.kyori.adventure.text.Component.text(QUANTITY_GUI_TITLE));
                InventoryUtils.applyInventoryStyle(gui, QUANTITY_GUI_TITLE);
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
                                        if (!lineText.contains("§7Click to select this item")) {
                                                customLore.add(line);
                                        }
                                }
                                meta.lore(customLore);
                                item.setItemMeta(meta);
                        }
                }

                gui.setItem(10,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, "§a§l-64",
                                                "§7Decrease quantity by 64"));
                gui.setItem(11,
                                ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, "§c§l-10",
                                                "§7Decrease quantity by 10"));
                gui.setItem(12, ItemUtils.createGuiItem(Material.RED_STAINED_GLASS_PANE, "§c§l-1",
                                "§7Decrease quantity by 1"));
                gui.setItem(13, item.clone());
                gui.setItem(14,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "§a§l+1",
                                                "§7Increase quantity by 1"));
                gui.setItem(15,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "§a§l+10",
                                                "§7Increase quantity by 10"));
                gui.setItem(16,
                                ItemUtils.createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "§a§l+64",
                                                "§7Increase quantity by 64"));
                gui.setItem(22, ItemUtils.createGuiItem(Material.BOOK, "§a§lInstructions",
                                "§7Use + / - to adjust the quantity", "§7Click 'Confirm' to set the quantity"));
                gui.setItem(26,
                                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, "§2§lConfirm",
                                                "§7Click to confirm the quantity"));
        }
}