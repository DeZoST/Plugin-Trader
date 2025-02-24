package fr.dezost.traderplugin.gui;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.traits.TraderTrait;
import fr.dezost.traderplugin.utils.InventoryUtils;
import fr.dezost.traderplugin.utils.ItemUtils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopGUI {
    public static final String SHOP_TITLE = "§6§lMagasin";
    public static final String BUY_OR_SELL_TITLE = "§6§lBuy or Sell";

    private final TraderPlugin plugin;

    public ShopGUI(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    public void handleShopItemClick(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        if (event.getClickedInventory() != null) {
            System.out.println("Clicked inventory: " + event.getClickedInventory().getType());
            System.out.println("Clicked inventory holder: " + event.getClickedInventory().getHolder());

            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                System.out.println("Clicked in shop inventory");
                if (event.getClick().isLeftClick()) {
                    System.out.println("Left click");
                    ItemStack item = clickedItem.clone();
                    Inventory buyOrSellGUI = createBuyOrSellGUI(item);
                    player.openInventory(buyOrSellGUI);
                }
            }
        } else {
            System.out.println("Clicked inventory is null");
        }
        event.setCancelled(true);
    }

    public void openShop(Player player, NPC npc) {
        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
        if (traderTrait != null) {
            Inventory shopInventory = traderTrait.getShopInventory();
            Inventory sortedInventory = Bukkit.createInventory(null, shopInventory.getSize(),
                    net.kyori.adventure.text.Component.text(SHOP_TITLE));
            InventoryUtils.applyInventoryStyle(sortedInventory, SHOP_TITLE);

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
                                net.kyori.adventure.text.Component.text(SHOP_TITLE));
                        InventoryUtils.applyInventoryStyle(sortedInventory, SHOP_TITLE);
                        slot = 0;
                    }
                    sortedInventory.setItem(slot, item);
                    slot++;
                }
            }

            player.openInventory(sortedInventory);
        }
    }

    private Inventory createBuyOrSellGUI(ItemStack item) {
        Inventory gui = Bukkit.createInventory(null, 27, net.kyori.adventure.text.Component.text(BUY_OR_SELL_TITLE));
        InventoryUtils.applyInventoryStyle(gui, BUY_OR_SELL_TITLE);
        setupBuyOrSellButtons(gui, item);
        return gui;
    }

    private void setupBuyOrSellButtons(Inventory gui, ItemStack item) {
        gui.setItem(11, ItemUtils.createGuiItem(Material.GOLD_INGOT, "§a§lBuy", "§7Buy this item"));
        gui.setItem(15, ItemUtils.createGuiItem(Material.IRON_INGOT, "§a§lSell", "§7Sell this item"));
        ItemStack displayItem = item.clone();
        gui.setItem(13, displayItem);
    }
}