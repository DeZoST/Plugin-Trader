package fr.dezost.shop.listeners;

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

import fr.dezost.shop.TraderPlugin;
import fr.dezost.shop.gui.AdminGUI;
import fr.dezost.shop.gui.NPCRemoveGUI;
import fr.dezost.shop.gui.PaymentMethodGUI;
import fr.dezost.shop.gui.PriceSettingGUI;
import fr.dezost.shop.gui.QuantitySettingGUI;
import fr.dezost.shop.gui.ShopGUI;
import fr.dezost.shop.traits.TraderTrait;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;

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
        if (!player.hasMetadata(Constants.INTERACTING_NPC_METADATA)) {
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
            case Constants.ADMIN_SHOP_TITLE:
                handleAdminActions(player, event, clickedItem);
                break;
            case Constants.SELECT_ITEM_TITLE:
                handleAddItems(player, event, clickedItem);
                break;
            case Constants.MODIFY_ITEMS_TITLE:
                handleModifyItems(player, event, clickedItem);
                break;
            case Constants.DELETE_ITEMS_TITLE:
                handleDeleteItems(player, event, clickedItem);
                break;
            case Constants.REMOVE_NPC_GUI_TITLE:
                handleRemoveNPC(player, event, clickedItem);
                break;
            case Constants.PRICE_GUI_TITLE:
                handlePriceSetting(player, event, clickedItem);
                break;
            case Constants.QUANTITY_GUI_TITLE:
                handleQuantitySetting(player, event, clickedItem);
                break;
            case Constants.BUY_OR_SELL_TITLE:
                handleBuyOrSellSelection(player, event, clickedItem);
                break;
            case Constants.BUY_PAYMENT_GUI_TITLE:
            case Constants.SELL_PAYMENT_GUI_TITLE:
                handlePaymentMethod(player, event, clickedItem);
                break;
            default:
                if (inventoryTitle.equals(Constants.SHOP_TITLE)) {
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

                    if (lineText.contains(Constants.NPC_ID_PREFIX)) {
                        try {
                            String idPart = lineText.split(Constants.NPC_ID_PREFIX)[1].split("§")[0];
                            int npcId = Integer.parseInt(idPart);
                            NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
                            if (npc != null) {
                                npc.destroy();
                                player.sendMessage(String.format(Constants.NPC_REMOVED_MESSAGE, npc.getName()));
                                npcRemoveGUI.openRemoveNPCGUI(player);
                            } else {
                                player.sendMessage(Constants.NPC_NOT_FOUND_MESSAGE);
                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            player.sendMessage(Constants.ERROR_PARSE_NPC_ID_MESSAGE);
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            } else {
                player.sendMessage(Constants.ERROR_ITEM_LORE_NULL_MESSAGE);
            }
        } else {
            player.sendMessage(Constants.ERROR_ITEM_META_NULL_MESSAGE);
        }
        event.setCancelled(true);
    }

    private void handleQuantitySetting(Player player, InventoryClickEvent event, ItemStack clickedItem) {
        Component displayNameComponent = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
        if (displayNameComponent != null) {
            String displayName = LegacyComponentSerializer.legacySection().serialize(displayNameComponent);
            Inventory inventory = event.getClickedInventory();
            if (displayName.equals(Constants.CONFIRM_TEXT)) {
                assert inventory != null;
                ItemStack item = inventory.getItem(13);
                if (item != null) {
                    player.setMetadata(Constants.SELECTED_ITEM_METADATA,
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
            player.sendMessage(Constants.NO_PERMISSION_MODIFY_NPCS_MESSAGE);
            event.setCancelled(true);
            return;
        }
        Component displayNameComponent = Objects.requireNonNull(clickedItem.getItemMeta()).displayName();
        String action = LegacyComponentSerializer.legacySection().serialize(displayNameComponent);

        switch (action) {
            case Constants.ADD_ITEMS_BUTTON_TEXT:
                adminGUI.openPlayerInventoryToAdd(player);
                break;
            case Constants.MODIFY_ITEMS_BUTTON_TEXT:
                adminGUI.openModifyItemsGUI(player, InventoryUtils.getNPCShopInventory(player));
                break;
            case Constants.DELETE_ITEMS_BUTTON_TEXT:
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
                    player.sendMessage(Constants.ITEM_DELETED_MESSAGE);
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
                    player.setMetadata(Constants.SELECTED_ITEM_INDEX_METADATA, new FixedMetadataValue(plugin, slot));
                    quantitySettingGUI.openQuantitySettingGUI(player, clickedItem);
                } else {
                    player.sendMessage(Constants.ITEM_NOT_FOUND_MESSAGE);
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
            if (displayName.equals(Constants.CONFIRM_TEXT)) {
                assert inventory != null;
                ItemStack item = inventory.getItem(13);
                if (item != null) {
                    NPC npc = CitizensAPI.getNPCRegistry()
                            .getById(player.getMetadata(Constants.INTERACTING_NPC_METADATA).get(0).asInt());
                    Inventory shopInventory = npc.data().get(Constants.SHOP_INVENTORY_DATA, null);
                    if (shopInventory != null) {
                        if (player.hasMetadata(Constants.SELECTED_ITEM_INDEX_METADATA)) {
                            int index = player.getMetadata(Constants.SELECTED_ITEM_INDEX_METADATA).get(0).asInt();
                            shopInventory.setItem(index, item);
                            player.removeMetadata(Constants.SELECTED_ITEM_INDEX_METADATA, plugin);
                        } else {
                            InventoryUtils.addNewShopItem(item, shopInventory, npc);
                            shopInventory = npc.data().get(Constants.SHOP_INVENTORY_DATA, shopInventory);
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
            if (displayName.equals(Constants.BUY_BUTTON_TEXT)) {
                ItemStack item = Objects.requireNonNull(event.getClickedInventory()).getItem(13);
                int quantity = (item != null) ? item.getAmount() : 0;
                paymentMethodGUI.openPaymentMethodGUI(player, item, quantity, true);
            } else if (displayName.equals(Constants.SELL_BUTTON_TEXT)) {
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
        player.sendMessage(Constants.FEATURE_NOT_RELEASED_MESSAGE);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata(Constants.INTERACTING_NPC_METADATA)) {
            player.removeMetadata(Constants.INTERACTING_NPC_METADATA, plugin);
        }
    }
}