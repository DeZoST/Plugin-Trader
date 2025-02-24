package fr.dezost.shop.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dezost.shop.TraderPlugin;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;
import fr.dezost.shop.utils.ItemUtils;

import static fr.dezost.shop.utils.InventoryUtils.isBorderSlot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminGUI {
    private final TraderPlugin plugin;

    public AdminGUI(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    public void openAdminActionSelectionGUI(Player player) {
        Inventory adminActionInventory = plugin.getServer().createInventory(null, 9,
                net.kyori.adventure.text.Component.text(Constants.ADMIN_SHOP_TITLE));
        InventoryUtils.applyInventoryStyle(adminActionInventory, Constants.ADMIN_SHOP_TITLE);
        adminActionInventory.setItem(2,
                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, Constants.ADD_ITEMS_BUTTON_TEXT,
                        Constants.ADD_ITEMS_BUTTON_LORE));
        adminActionInventory.setItem(4,
                ItemUtils.createGuiItem(Material.GOLD_BLOCK, Constants.MODIFY_ITEMS_BUTTON_TEXT,
                        Constants.MODIFY_ITEMS_BUTTON_LORE));
        adminActionInventory.setItem(6,
                ItemUtils.createGuiItem(Material.REDSTONE_BLOCK, Constants.DELETE_ITEMS_BUTTON_TEXT,
                        Constants.DELETE_ITEMS_BUTTON_LORE));
        player.openInventory(adminActionInventory);
    }

    public void openPlayerInventoryToAdd(Player player) {
        Inventory playerInventory = plugin.getServer().createInventory(null, 54,
                net.kyori.adventure.text.Component.text(Constants.SELECT_ITEM_TITLE));
        InventoryUtils.applyInventoryStyle(playerInventory, Constants.SELECT_ITEM_TITLE);

        Set<Material> addedItems = new HashSet<>();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (!addedItems.contains(item.getType())) {
                    ItemStack displayItem = item.clone();
                    displayItem.setAmount(1);
                    ItemMeta meta = displayItem.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add(Constants.SELECT_ITEM_LORE);
                    assert meta != null;
                    meta.lore(lore.stream().map(net.kyori.adventure.text.Component::text).toList());
                    displayItem.setItemMeta(meta);
                    playerInventory.addItem(displayItem);
                    addedItems.add(item.getType());
                }
            }
        }
        player.openInventory(playerInventory);
    }

    public void openModifyItemsGUI(Player player, Inventory npcShopInventory) {
        Inventory modifyItemsInventory = plugin.getServer().createInventory(null, 54,
                net.kyori.adventure.text.Component.text(Constants.MODIFY_ITEMS_TITLE));
        InventoryUtils.applyInventoryStyle(modifyItemsInventory, Constants.MODIFY_ITEMS_TITLE);

        int slot = 0;

        for (ItemStack item : npcShopInventory.getContents()) {
            if (item != null && item.getType() != Material.AIR && item.getType() != Material.GRAY_STAINED_GLASS_PANE) {

                while (slot < modifyItemsInventory.getSize() && (isBorderSlot(slot, modifyItemsInventory.getSize())
                        || modifyItemsInventory.getItem(slot) != null)) {
                    slot++;
                }
                if (slot >= modifyItemsInventory.getSize()) {
                    player.sendMessage(Constants.NO_MORE_SPACE_MESSAGE);
                    break;
                }

                ItemStack displayItem = item.clone();
                modifyItemsInventory.setItem(slot, displayItem);
                slot++;
            }
        }

        player.openInventory(modifyItemsInventory);
    }

    public void openShopForDeletion(Player player, Inventory npcShopInventory) {
        Inventory deleteItemsInventory = plugin.getServer().createInventory(null, 54,
                net.kyori.adventure.text.Component.text(Constants.DELETE_ITEMS_TITLE));
        InventoryUtils.applyInventoryStyle(deleteItemsInventory, Constants.DELETE_ITEMS_TITLE);

        int slot = 0;

        for (ItemStack item : npcShopInventory.getContents()) {
            if (item != null && item.getType() != Material.AIR && item.getType() != Material.GRAY_STAINED_GLASS_PANE) {

                while (slot < deleteItemsInventory.getSize() && (isBorderSlot(slot, deleteItemsInventory.getSize())
                        || deleteItemsInventory.getItem(slot) != null)) {
                    slot++;
                }
                if (slot >= deleteItemsInventory.getSize()) {
                    player.sendMessage(Constants.NO_MORE_SPACE_MESSAGE);
                    break;
                }

                ItemStack displayItem = item.clone();
                deleteItemsInventory.setItem(slot, displayItem);
                slot++;
            }
        }

        player.openInventory(deleteItemsInventory);
    }
}