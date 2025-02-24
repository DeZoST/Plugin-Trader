package fr.dezost.shop.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dezost.shop.TraderPlugin;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;
import fr.dezost.shop.utils.ItemUtils;

public class PaymentMethodGUI {
        private JavaPlugin plugin;

        public PaymentMethodGUI(JavaPlugin plugin) {
                this.plugin = plugin;
        }

        public void openPaymentMethodGUI(Player player, ItemStack item, int quantity, boolean isBuying) {
                String transactionType = isBuying ? "buy" : "sell";
                player.setMetadata("transactionType",
                                new FixedMetadataValue(JavaPlugin.getPlugin(TraderPlugin.class), transactionType));

                String title = isBuying ? Constants.BUY_PAYMENT_GUI_TITLE : Constants.SELL_PAYMENT_GUI_TITLE;
                Inventory paymentGUI = Bukkit.createInventory(null, 27, net.kyori.adventure.text.Component.text(title));
                InventoryUtils.applyInventoryStyle(paymentGUI, title);

                ItemStack displayItem = item.clone();
                displayItem.setAmount(quantity);
                paymentGUI.setItem(13, displayItem);

                if (isBuying) {
                        paymentGUI.setItem(11,
                                        ItemUtils.createGuiItem(Material.EMERALD, Constants.PAY_WITH_EMERALDS_TEXT,
                                                        Constants.PAY_WITH_EMERALDS_LORE));
                        paymentGUI.setItem(15, ItemUtils.createGuiItem(Material.PAPER, Constants.PAY_WITH_BANK_TEXT,
                                        Constants.PAY_WITH_BANK_LORE));
                } else {
                        paymentGUI.setItem(11,
                                        ItemUtils.createGuiItem(Material.EMERALD, Constants.RECEIVE_EMERALDS_TEXT,
                                                        Constants.RECEIVE_EMERALDS_LORE));
                        paymentGUI.setItem(15, ItemUtils.createGuiItem(Material.PAPER, Constants.RECEIVE_BANK_TEXT,
                                        Constants.RECEIVE_BANK_LORE));
                }

                player.openInventory(paymentGUI);
        }
}