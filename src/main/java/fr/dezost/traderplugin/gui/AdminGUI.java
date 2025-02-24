package fr.dezost.traderplugin.gui;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.utils.InventoryUtils;
import fr.dezost.traderplugin.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fr.dezost.traderplugin.utils.InventoryUtils.isBorderSlot;

public class AdminGUI {
    private final TraderPlugin plugin;

    public static final String SHOP_TITLE = "§6§lNPC Shop Actions";
    public static final String SELECT_ITEM_TITLE = "§6§lSelect Item to Add";
    public static final String MODIFY_ITEMS_TITLE = "§6§lModify Shop Items";
    public static final String DELETE_ITEMS_TITLE = "§6§lDelete Shop Items";

    public AdminGUI(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    public void openAdminActionSelectionGUI(Player player) {
        Inventory adminActionInventory = plugin.getServer().createInventory(null, 9,
                net.kyori.adventure.text.Component.text(SHOP_TITLE));
        InventoryUtils.applyInventoryStyle(adminActionInventory, SHOP_TITLE);
        adminActionInventory.setItem(2,
                ItemUtils.createGuiItem(Material.EMERALD_BLOCK, "§a§lAdd Items", "§7Click to add new items"));
        adminActionInventory.setItem(4,
                ItemUtils.createGuiItem(Material.GOLD_BLOCK, "§e§lModify Items", "§7Click to modify items"));
        adminActionInventory.setItem(6,
                ItemUtils.createGuiItem(Material.REDSTONE_BLOCK, "§c§lDelete Items", "§7Click to delete items"));
        player.openInventory(adminActionInventory);

    }

    public void openPlayerInventoryToAdd(Player player) {
        Inventory playerInventory = plugin.getServer().createInventory(null, 54,
                net.kyori.adventure.text.Component.text(SELECT_ITEM_TITLE));
        InventoryUtils.applyInventoryStyle(playerInventory, SELECT_ITEM_TITLE);

        Set<Material> addedItems = new HashSet<>();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (!addedItems.contains(item.getType())) {
                    ItemStack displayItem = item.clone();
                    displayItem.setAmount(1);
                    ItemMeta meta = displayItem.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§7Click to select this item");
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
                net.kyori.adventure.text.Component.text(MODIFY_ITEMS_TITLE));
        InventoryUtils.applyInventoryStyle(modifyItemsInventory, MODIFY_ITEMS_TITLE);

        int slot = 0;

        for (ItemStack item : npcShopInventory.getContents()) {
            if (item != null && item.getType() != Material.AIR && item.getType() != Material.GRAY_STAINED_GLASS_PANE) {

                while (slot < modifyItemsInventory.getSize() && (isBorderSlot(slot, modifyItemsInventory.getSize())
                        || modifyItemsInventory.getItem(slot) != null)) {
                    slot++;
                }
                if (slot >= modifyItemsInventory.getSize()) {
                    player.sendMessage("§cNo more space available for items.");
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
                net.kyori.adventure.text.Component.text(DELETE_ITEMS_TITLE));
        InventoryUtils.applyInventoryStyle(deleteItemsInventory, DELETE_ITEMS_TITLE);

        int slot = 0;

        for (ItemStack item : npcShopInventory.getContents()) {
            if (item != null && item.getType() != Material.AIR && item.getType() != Material.GRAY_STAINED_GLASS_PANE) {

                while (slot < deleteItemsInventory.getSize() && (isBorderSlot(slot, deleteItemsInventory.getSize())
                        || deleteItemsInventory.getItem(slot) != null)) {
                    slot++;
                }
                if (slot >= deleteItemsInventory.getSize()) {
                    player.sendMessage("§cNo more space available for items.");
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
