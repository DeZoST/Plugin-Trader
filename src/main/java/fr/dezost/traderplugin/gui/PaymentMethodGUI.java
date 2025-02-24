package fr.dezost.traderplugin.gui;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.utils.InventoryUtils;
import fr.dezost.traderplugin.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class PaymentMethodGUI {
        public static final String BUY_PAYMENT_GUI_TITLE = "§6§lSelect Payment Method (Buy)";
        public static final String SELL_PAYMENT_GUI_TITLE = "§6§lSelect Payment Method (Sell)";
        private JavaPlugin plugin;

        public PaymentMethodGUI(JavaPlugin plugin) {
                this.plugin = plugin;
        }

        public void openPaymentMethodGUI(Player player, ItemStack item, int quantity, boolean isBuying) {
                String transactionType = isBuying ? "buy" : "sell";
                player.setMetadata("transactionType",
                                new FixedMetadataValue(JavaPlugin.getPlugin(TraderPlugin.class), transactionType));

                String title = isBuying ? PaymentMethodGUI.BUY_PAYMENT_GUI_TITLE
                                : PaymentMethodGUI.SELL_PAYMENT_GUI_TITLE;
                Inventory paymentGUI = Bukkit.createInventory(null, 27, net.kyori.adventure.text.Component.text(title));
                InventoryUtils.applyInventoryStyle(paymentGUI, title);

                ItemStack displayItem = item.clone();
                displayItem.setAmount(quantity);
                paymentGUI.setItem(13, displayItem);

                if (isBuying) {
                        paymentGUI.setItem(11, ItemUtils.createGuiItem(Material.EMERALD, "§a§lPay with Emeralds",
                                        "§7Pay using emeralds in inventory"));
                        paymentGUI.setItem(15, ItemUtils.createGuiItem(Material.PAPER, "§a§lPay with the bank",
                                        "§7Pay using money in the bank"));
                } else {
                        paymentGUI.setItem(11, ItemUtils.createGuiItem(Material.EMERALD, "§a§lReceive emeralds",
                                        "§7Receive payment in emeralds"));
                        paymentGUI.setItem(15, ItemUtils.createGuiItem(Material.PAPER, "§a§lReceive in the bank",
                                        "§7Receive payment in the bank"));
                }

                player.openInventory(paymentGUI);
        }
}