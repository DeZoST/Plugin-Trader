package fr.dezost.shop.gui;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.dezost.shop.TraderPlugin;
import fr.dezost.shop.traits.TraderTrait;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;
import fr.dezost.shop.utils.ItemUtils;

/**
 * GUI for the NPC shop.
 */
public class ShopGUI {
    private final TraderPlugin plugin;

    /**
     * Constructs a ShopGUI with the specified plugin.
     *
     * @param plugin the plugin instance
     */
    public ShopGUI(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles item clicks in the shop GUI.
     *
     * @param player      the player interacting with the GUI
     * @param event       the InventoryClickEvent
     * @param clickedItem the clicked item
     */
    public void handleShopItemClick(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        if (event.getClickedInventory() != null) {
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                if (event.getClick().isLeftClick()) {
                    ItemStack item = clickedItem.clone();
                    Inventory buyOrSellGUI = createBuyOrSellGUI(item);
                    player.openInventory(buyOrSellGUI);
                }
            }
        }
        event.setCancelled(true);
    }

    /**
     * Opens the shop GUI for the player.
     *
     * @param player the player to open the GUI for
     * @param npc    the NPC whose shop to open
     */
    public void openShop(Player player, NPC npc) {
        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
        if (traderTrait != null) {
            Inventory shopInventory = traderTrait.getShopInventory();
            Inventory sortedInventory = Bukkit.createInventory(null, shopInventory.getSize(),
                    net.kyori.adventure.text.Component.text(Constants.SHOP_TITLE));
            InventoryUtils.applyInventoryStyle(sortedInventory, Constants.SHOP_TITLE);

            int slot = 0;

            for (ItemStack item : shopInventory.getContents()) {
                if (item != null && item.getType() != Material.AIR
                        && item.getType() != Material.GRAY_STAINED_GLASS_PANE) {
                    while (slot < sortedInventory.getSize()
                            && (InventoryUtils.isBorderSlot(slot, sortedInventory.getSize())
                                    || sortedInventory.getItem(slot) != null)) {
                        slot++;
                    }
                    if (slot >= sortedInventory.getSize()) {
                        shopInventory = InventoryUtils.resizeInventory(shopInventory, npc);
                        sortedInventory = Bukkit.createInventory(null, shopInventory.getSize(),
                                net.kyori.adventure.text.Component.text(Constants.SHOP_TITLE));
                        InventoryUtils.applyInventoryStyle(sortedInventory, Constants.SHOP_TITLE);
                        slot = 0;
                    }
                    sortedInventory.setItem(slot, item);
                    slot++;
                }
            }

            player.openInventory(sortedInventory);
        }
    }

    /**
     * Creates the buy or sell GUI for the specified item.
     *
     * @param item the item to buy or sell
     * @return the created buy or sell GUI
     */
    private Inventory createBuyOrSellGUI(ItemStack item) {
        Inventory gui = Bukkit.createInventory(null, 27,
                net.kyori.adventure.text.Component.text(Constants.BUY_OR_SELL_TITLE));
        InventoryUtils.applyInventoryStyle(gui, Constants.BUY_OR_SELL_TITLE);
        setupBuyOrSellButtons(gui, item);
        return gui;
    }

    /**
     * Sets up the buy or sell buttons in the GUI.
     *
     * @param gui  the GUI to set up the buttons in
     * @param item the item to buy or sell
     */
    private void setupBuyOrSellButtons(Inventory gui, ItemStack item) {
        gui.setItem(11,
                ItemUtils.createGuiItem(Material.GOLD_INGOT, Constants.BUY_BUTTON_TEXT, Constants.BUY_BUTTON_LORE));
        gui.setItem(15,
                ItemUtils.createGuiItem(Material.IRON_INGOT, Constants.SELL_BUTTON_TEXT, Constants.SELL_BUTTON_LORE));
        ItemStack displayItem = item.clone();
        gui.setItem(13, displayItem);
    }
}