package fr.dezost.traderplugin.listeners;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.gui.AdminGUI;
import fr.dezost.traderplugin.gui.NPCRemoveGUI;
import fr.dezost.traderplugin.gui.PriceSettingGUI;
import fr.dezost.traderplugin.gui.QuantitySettingGUI;
import fr.dezost.traderplugin.gui.ShopGUI;
import fr.dezost.traderplugin.traits.TraderTrait;
import fr.dezost.traderplugin.gui.PaymentMethodGUI;
import fr.dezost.traderplugin.utils.InventoryUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

import static fr.dezost.traderplugin.utils.ItemUtils.getItemPrice;

import java.util.List;
import java.util.Objects;

public class NPCGUIListener implements Listener {
    private final AdminGUI adminGUI;
    private final PriceSettingGUI priceSettingGUI;
    private final QuantitySettingGUI quantitySettingGUI;
    private final ShopGUI shopGUI;
    private final PaymentMethodGUI paymentMethodGUI;
    private final NPCRemoveGUI npcRemoveGUI;
    private final TraderPlugin plugin;

    public NPCGUIListener(TraderPlugin plugin, AdminGUI adminGUI, PriceSettingGUI priceSettingGUI,
            QuantitySettingGUI quantitySettingGUI, ShopGUI shopGUI, PaymentMethodGUI paymentMethodGUI,
            NPCRemoveGUI npcRemoveGUI) {
        this.plugin = plugin;
        this.adminGUI = adminGUI;
        this.priceSettingGUI = priceSettingGUI;
        this.quantitySettingGUI = quantitySettingGUI;
        this.shopGUI = shopGUI;
        this.paymentMethodGUI = paymentMethodGUI;
        this.npcRemoveGUI = npcRemoveGUI;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;

        String inventoryTitle = event.getView().getTitle();

        if (!InventoryUtils.isRecognizedInventory(inventoryTitle)) {
            return;
        }
        if (!player.hasMetadata("interactingNPC")) {
            event.setCancelled(true);
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR
                || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE) {
            event.setCancelled(true);
            return;
        }

        if (event.getClickedInventory() == player.getInventory()) {
            event.setCancelled(true);
            return;
        }

        switch (inventoryTitle) {
            case AdminGUI.SHOP_TITLE:
                handleAdminActions(player, event, clickedItem);
                break;
            case AdminGUI.SELECT_ITEM_TITLE:
                handleAddItems(player, event, clickedItem);
                break;
            case AdminGUI.MODIFY_ITEMS_TITLE:
                handleModifyItems(player, event, clickedItem);
                break;
            case AdminGUI.DELETE_ITEMS_TITLE:
                handleDeleteItems(player, event, clickedItem);
                break;
            case NPCRemoveGUI.REMOVE_NPC_GUI_TITLE:
                handleRemoveNPC(player, event, clickedItem);
                break;
            case PriceSettingGUI.PRICE_GUI_TITLE:
                handlePriceSetting(player, event, clickedItem);
                break;
            case QuantitySettingGUI.QUANTITY_GUI_TITLE:
                handleQuantitySetting(player, event, clickedItem);
                break;
            case ShopGUI.BUY_OR_SELL_TITLE:
                handleBuyOrSellSelection(player, event, clickedItem);
                break;
            case PaymentMethodGUI.BUY_PAYMENT_GUI_TITLE:
            case PaymentMethodGUI.SELL_PAYMENT_GUI_TITLE:
                handlePaymentMethod(player, event, clickedItem);
                break;
            default:
                if (inventoryTitle.equals(ShopGUI.SHOP_TITLE)) {
                    shopGUI.handleShopItemClick(player, event, clickedItem);
                }
                break;
        }
    }

    private void handleRemoveNPC(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        ItemMeta itemMeta = clickedItem.getItemMeta();
        if (itemMeta != null) {
            List<Component> lore = itemMeta.lore();
            if (lore != null) {
                for (Component line : lore) {
                    String lineText = LegacyComponentSerializer.legacySection().serialize(line);

                    if (lineText.contains("§7ID: ")) {
                        try {
                            String idPart = lineText.split("§7ID: ")[1].split("§")[0];
                            int npcId = Integer.parseInt(idPart);
                            NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
                            if (npc != null) {
                                npc.destroy();
                                player.sendMessage("§aNPC " + npc.getName() + " removed.");
                                npcRemoveGUI.openRemoveNPCGUI(player);
                            } else {
                                player.sendMessage("§cNPC not found.");
                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            player.sendMessage("§cError: Unable to parse NPC ID.");
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            } else {
                player.sendMessage("§cError: Item lore is null.");
            }
        } else {
            player.sendMessage("§cError: Item meta is null.");
        }
        event.setCancelled(true);
    }

    private void handleQuantitySetting(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        Component displayNameComponent = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
        if (displayNameComponent != null) {
            String displayName = LegacyComponentSerializer.legacySection().serialize(displayNameComponent);
            Inventory inventory = event.getClickedInventory();
            if (displayName.equals("§2§lConfirm")) {
                assert inventory != null;
                ItemStack item = inventory.getItem(13);
                if (item != null) {
                    player.setMetadata("selectedItem",
                            new FixedMetadataValue(JavaPlugin.getPlugin(TraderPlugin.class), item));
                    priceSettingGUI.openPriceSettingGUI(player, item);
                }
            }
            if (InventoryUtils.isPriceAdjustmentButton(displayName)) {
                quantitySettingGUI.adjustQuantity(player, event.getClickedInventory(), displayName);
                ItemStack item = inventory.getItem(13);
                if (item != null && item.getAmount() > 64) {
                    item.setAmount(64);
                    inventory.setItem(13, item);
                }
            }
        }
        event.setCancelled(true);
    }

    private void handleAdminActions(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        if (!player.hasPermission("trader.modify")) {
            player.sendMessage("§cVous n'avez pas la permission de modifier les PNJs.");
            event.setCancelled(true);
            return;
        }
        Component displayNameComponent = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
        String action = LegacyComponentSerializer.legacySection().serialize(displayNameComponent);

        switch (action) {
            case "§a§lAdd Items":

                adminGUI.openPlayerInventoryToAdd(player);
                break;
            case "§e§lModify Items":

                adminGUI.openModifyItemsGUI(player, InventoryUtils.getNPCShopInventory(player));
                break;
            case "§c§lDelete Items":

                adminGUI.openShopForDeletion(player, InventoryUtils.getNPCShopInventory(player));
                break;
        }
        event.setCancelled(true);
    }

    private void handleAddItems(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        if (clickedItem.getType() != Material.GRAY_STAINED_GLASS_PANE && clickedItem.getType() != Material.AIR) {
            quantitySettingGUI.openQuantitySettingGUI(player, clickedItem);
        }
        event.setCancelled(true);
    }

    private void handleDeleteItems(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        if (clickedItem.getType() != Material.GRAY_STAINED_GLASS_PANE && clickedItem.getType() != Material.AIR) {
            Inventory npcShopInventory = InventoryUtils.getNPCShopInventory(player);
            if (npcShopInventory != null) {
                int slot = event.getSlot();
                ItemStack item = npcShopInventory.getItem(slot);
                if (item != null && item.isSimilar(clickedItem)) {
                    npcShopInventory.setItem(slot, null);
                    player.sendMessage("§aItem deleted");
                }
                adminGUI.openShopForDeletion(player, npcShopInventory);
            }
        }
        event.setCancelled(true);
    }

    private void handleModifyItems(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        if (clickedItem.getType() != Material.GRAY_STAINED_GLASS_PANE && clickedItem.getType() != Material.AIR) {
            Inventory npcShopInventory = InventoryUtils.getNPCShopInventory(player);
            if (npcShopInventory != null) {

                int slot = event.getSlot();
                ItemStack item = npcShopInventory.getItem(slot);
                if (item != null && item.isSimilar(clickedItem)) {

                    player.setMetadata("selectedItemIndex", new FixedMetadataValue(plugin, slot));

                    quantitySettingGUI.openQuantitySettingGUI(player, clickedItem);
                } else {
                    player.sendMessage("§cItem not found in the shop inventory");
                }
            }
        }
        event.setCancelled(true);
    }

    private void handlePriceSetting(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        Component displayNameComponent = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
        if (displayNameComponent != null) {
            String displayName = LegacyComponentSerializer.legacySection().serialize(displayNameComponent);

            Inventory inventory = event.getClickedInventory();
            if (displayName.equals("§2§lConfirm")) {
                assert inventory != null;
                ItemStack item = inventory.getItem(13);
                if (item != null) {

                    NPC npc = CitizensAPI.getNPCRegistry().getById(player.getMetadata("interactingNPC").get(0).asInt());
                    Inventory shopInventory = npc.data().get("shopInventory", null);
                    if (shopInventory != null) {

                        if (player.hasMetadata("selectedItemIndex")) {
                            int index = player.getMetadata("selectedItemIndex").get(0).asInt();

                            shopInventory.setItem(index, item);
                            player.removeMetadata("selectedItemIndex", plugin);
                        } else {
                            InventoryUtils.addNewShopItem(item, shopInventory, npc);
                            shopInventory = npc.data().get("shopInventory", shopInventory);
                        }
                        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
                        if (traderTrait != null) {
                            traderTrait.setShopInventory(shopInventory);
                        }
                        player.closeInventory();
                    }
                }
            } else if (InventoryUtils.isPriceAdjustmentButton(displayName)) {
                InventoryUtils.adjustPrice(player, Objects.requireNonNull(event.getClickedInventory()), displayName);
            } else {
                event.setCancelled(true);
            }
        }
        event.setCancelled(true);
    }

    private void handleBuyOrSellSelection(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        Component displayNameComponent = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
        if (displayNameComponent != null) {
            String displayName = LegacyComponentSerializer.legacySection().serialize(displayNameComponent);
            if (displayName.equals("§a§lBuy")) {
                ItemStack item = Objects.requireNonNull(event.getClickedInventory()).getItem(13);
                int quantity = (item != null) ? item.getAmount() : 0;
                paymentMethodGUI.openPaymentMethodGUI(player, item, quantity, true);
            } else if (displayName.equals("§a§lSell")) {
                ItemStack item = Objects.requireNonNull(event.getClickedInventory()).getItem(13);
                int quantity = (item != null) ? item.getAmount() : 0;
                paymentMethodGUI.openPaymentMethodGUI(player, item, quantity, false);
            } else {
                event.setCancelled(true);
            }
        }
        event.setCancelled(true);
    }

    private void handlePaymentMethod(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        event.setCancelled(true);
        player.sendMessage("§cCette fonctionnalite n'est pas encore sortie.");
        /*
         * Component displayNameComponent =
         * Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
         * if (displayNameComponent != null) {
         * String displayName =
         * LegacyComponentSerializer.legacySection().serialize(displayNameComponent);
         * ItemStack item =
         * Objects.requireNonNull(event.getClickedInventory()).getItem(13);
         * if (item != null) {
         * int quantity = item.getAmount();
         * int totalCost = calculateTotalCost(item, quantity);
         * IPurseAPI purseAPI = JavaPlugin.getPlugin(TraderPlugin.class).getPurseAPI();
         *
         * if (player.hasMetadata("transactionType")) {
         * String transactionType =
         * player.getMetadata("transactionType").get(0).asString();
         *
         * if (transactionType.equals("buy")) {
         * if (displayName.equals("§a§lPay with Emeralds")) {
         * if (purseAPI.getPurseMoney(player) >= totalCost) {
         * purseAPI.removeMoneyFromPurse(player, totalCost);
         * InventoryUtils.giveItem(player, item, quantity);
         * player.sendMessage("§aPayment successful! You received " + quantity + " "
         * + item.getType().name() + ".");
         * } else {
         * player.sendMessage("§cYou don't have enough emeralds.");
         * }
         * } else if (displayName.equals("§a§lPay with Vault")) {
         * if (purseAPI.getBankMoney(player) >= totalCost) {
         * purseAPI.removeMoneyFromBank(player, totalCost);
         * InventoryUtils.giveItem(player, item, quantity);
         * player.sendMessage("§aPayment successful! You received " + quantity + " "
         * + item.getType().name() + ".");
         * } else {
         * player.sendMessage("§cYou don't have enough money in your bank.");
         * }
         * }
         * } else if (transactionType.equals("sell")) {
         * int soldQuantity = 0;
         * if (displayName.equals("§a§lReceive emeralds")) {
         * soldQuantity = InventoryUtils.removeItemsFromInventory(player, item,
         * quantity);
         * if (soldQuantity > 0) {
         * purseAPI.addMoneyToPurse(player, soldQuantity * getItemPrice(item));
         * }
         * } else if (displayName.equals("§a§lReceive in the bank")) {
         * soldQuantity = InventoryUtils.removeItemsFromInventory(player, item,
         * quantity);
         * if (soldQuantity > 0) {
         * purseAPI.addMoneyToBank(player, soldQuantity * getItemPrice(item));
         * }
         * }
         *
         * if (soldQuantity > 0) {
         * player.sendMessage("§aYou sold " + soldQuantity + " " + item.getType().name()
         * + ".");
         * if (soldQuantity < quantity) {
         * player.sendMessage("§eYou didn't have enough items. Sold " + soldQuantity
         * + " instead of " + quantity + ".");
         * }
         * } else {
         * player.sendMessage("§cYou don't have enough items to sell.");
         * }
         * }
         * } else {
         * player.sendMessage("§cTransaction type not set. Please try again.");
         * }
         * player.removeMetadata("transactionType", plugin);
         * player.closeInventory();
         * }
         * } else {
         * event.setCancelled(true);
         * }
         * event.setCancelled(true);
         */
    }

    private int calculateTotalCost(ItemStack item, int quantity) {
        if (item == null || quantity <= 0)
            return 0;

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "price");
            if (dataContainer.has(key, PersistentDataType.INTEGER)) {
                int pricePerItem = dataContainer.get(key, PersistentDataType.INTEGER);
                return pricePerItem * quantity;
            }
        }
        return 0;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("interactingNPC")) {
            player.removeMetadata("interactingNPC", plugin);
        }
    }
}