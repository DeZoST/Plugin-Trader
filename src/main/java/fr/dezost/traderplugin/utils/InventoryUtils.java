package fr.dezost.traderplugin.utils;

/*import fr.dezost.traderplugin.TraderPlugin;*/
import fr.dezost.traderplugin.gui.*;
import fr.dezost.traderplugin.traits.TraderTrait;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.CitizensAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*import static fr.dezost.traderplugin.utils.ItemUtils.getItemPrice;*/

public class InventoryUtils {

    private static JavaPlugin plugin;

    public static void applyInventoryStyle(Inventory inventory, String title) {
        int size = inventory.getSize();
        for (int i = 0; i < size; i++) {
            if (isBorderSlot(i, size)) {

                inventory.setItem(i, createBorderItem());
            }
        }
    }

    private static ItemStack createBorderItem() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(net.kyori.adventure.text.Component.text(" "));
            item.setItemMeta(meta);
        }
        return item;
    }

    public static Inventory getNPCShopInventory(Player player) {
        NPC npc = CitizensAPI.getNPCRegistry().getById(player.getMetadata("interactingNPC").getFirst().asInt());
        return npc.data().get("shopInventory", null);
    }

    public static void saveInventory(NPC npc, Inventory inventory) {
        if (!CitizensAPI.getNPCRegistry().isNPC(npc.getEntity())) {
            System.out.println("NPC " + npc.getId() + " does not exist. Skipping save.");
            return;
        }

        File file = new File("plugins/TraderPlugin/inventories", npc.getId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        for (int i = 0; i < inventory.getSize(); i++) {
            config.set("inventory.slot" + i, inventory.getItem(i));
        }

        try {
            config.save(file);
            System.out.println("Inventory saved for NPC " + npc.getId() + " to file " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Inventory loadInventory(NPC npc) {
        File file = new File("plugins/TraderPlugin/inventories", npc.getId() + ".yml");
        if (!file.exists()) {
            return null;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Inventory inventory = createShopInventory(27, npc);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, config.getItemStack("inventory.slot" + i));
        }

        npc.data().set("shopInventory", inventory);
        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
        if (traderTrait != null) {
            traderTrait.setShopInventory(inventory);
        } else {
            npc.data().set("shopInventory", inventory);
        }

        return inventory;
    }

    public static void saveAllInventories() {
        System.out.println("Saving all NPC inventories...");
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            Inventory inventory = npc.data().get("shopInventory", null);
            if (inventory != null) {
                saveInventory(npc, inventory);
            }
        }
        System.out.println("All NPC inventories saved.");
    }

    public static void loadAllInventories() {
        File folder = new File("plugins/TraderPlugin/inventories");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        System.out.println("Loading inventories from folder: " + folder.getAbsolutePath());

        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                System.out.println("Processing file: " + file.getName());
                int npcId = Integer.parseInt(file.getName().replace(".yml", ""));
                NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
                if (npc == null) {
                    System.out.println("NPC with ID " + npcId + " does not exist. Deleting file: " + file.getName());
                    file.delete();
                } else {
                    System.out.println("NPC with ID " + npcId + " exists. Loading inventory.");
                    Inventory inventory = loadInventory(npc);
                    if (inventory != null) {
                        System.out.println("Inventory loaded for NPC with ID " + npcId);
                    } else {
                        System.out.println(
                                "No inventory found for NPC with ID " + npcId + ". Creating default inventory.");
                        Inventory defaultInventory = createShopInventory(27, npc);
                        npc.data().set("shopInventory", defaultInventory);
                        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
                        if (traderTrait != null) {
                            traderTrait.setShopInventory(defaultInventory);
                        }
                    }
                }
            }
        }
    }

    public static boolean isRecognizedInventory(String inventoryTitle) {
        return inventoryTitle.equals(AdminGUI.SHOP_TITLE) ||
                inventoryTitle.equals(AdminGUI.SELECT_ITEM_TITLE) ||
                inventoryTitle.equals(AdminGUI.MODIFY_ITEMS_TITLE) ||
                inventoryTitle.equals(AdminGUI.DELETE_ITEMS_TITLE) ||
                inventoryTitle.equals(PriceSettingGUI.PRICE_GUI_TITLE) ||
                inventoryTitle.equals(QuantitySettingGUI.QUANTITY_GUI_TITLE) ||
                inventoryTitle.equals(PaymentMethodGUI.BUY_PAYMENT_GUI_TITLE) ||
                inventoryTitle.equals(PaymentMethodGUI.SELL_PAYMENT_GUI_TITLE) ||
                inventoryTitle.endsWith(ShopGUI.SHOP_TITLE) ||
                inventoryTitle.equals(ShopGUI.BUY_OR_SELL_TITLE) ||
                inventoryTitle.equals(NPCRemoveGUI.REMOVE_NPC_GUI_TITLE);
    }

    public static boolean isPriceAdjustmentButton(String displayName) {
        return displayName.contains("+") || displayName.contains("-");
    }

    public static void adjustPrice(Player player, Inventory inventory, String adjustment) {
        ItemStack item = inventory.getItem(13);
        if (item == null || item.getType() == Material.AIR)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasLore()) {
            List<Component> lore = meta.lore();
            if (lore != null && !lore.isEmpty()) {
                String priceLine = lore.get(0).toString();
                try {
                    int currentPrice = Integer.parseInt(priceLine.replaceAll("\\D", ""));
                    int change = Integer.parseInt(adjustment.replaceAll("[^\\d-]", ""));
                    int newPrice = Math.min(10000, Math.max(1, currentPrice + change));
                    lore.set(0, Component.text("§ePrice: " + newPrice));
                    meta.lore(lore);
                    item.setItemMeta(meta);
                    inventory.setItem(13, item);
                    player.sendMessage("§aNew price set: " + newPrice);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cError: Invalid price format.");
                }
            }
        }
    }

    public static void adjustQuantity(Player player, Inventory inventory, String adjustment) {
        ItemStack item = inventory.getItem(13);
        if (item == null || item.getType() == Material.AIR)
            return;

        int currentQuantity = item.getAmount();
        int change = Integer.parseInt(adjustment.replaceAll("[^\\d-]", ""));
        int newQuantity = currentQuantity + change;

        int maxStackSize = item.getMaxStackSize();

        if (newQuantity > maxStackSize) {
            newQuantity = maxStackSize;

        } else if (newQuantity < 1) {
            newQuantity = 1;
        }

        item.setAmount(newQuantity);
        inventory.setItem(13, item);
        player.sendMessage("§aNew quantity set: " + newQuantity);
    }

    public static void updateShopItemPrice(ItemStack modifiedItem, int index, Inventory inventory) {
        if (index >= 0 && index < inventory.getSize()) {
            ItemMeta meta = modifiedItem.getItemMeta();
            if (meta != null && meta.hasLore()) {
                List<String> lore = meta.hasLore() ? new ArrayList<>(meta.lore().stream()
                        .map(component -> net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
                                .plainText().serialize(component))
                        .toList()) : new ArrayList<>();
                if (lore != null) {
                    lore.removeIf(line -> line.contains("§7Unique ID:"));
                    meta.lore(lore.stream().map(net.kyori.adventure.text.Component::text).toList());
                    modifiedItem.setItemMeta(meta);
                }
            }
            inventory.setItem(index, modifiedItem);
            assert meta != null;
        }
    }

    public static void addNewShopItem(ItemStack newItem, Inventory inventory, NPC npc) {
        if (isInventoryFull(inventory) && inventory.getSize() < 54) {
            inventory = resizeInventory(inventory, npc);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null
                    || Objects.requireNonNull(inventory.getItem(i)).getType() == Material.AIR) {
                if (!isBorderSlot(i, inventory.getSize())) {
                    ItemMeta meta = newItem.getItemMeta();
                    if (meta != null) {
                        List<String> lore = meta.hasLore() ? new ArrayList<>(meta.lore().stream()
                                .map(component -> net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
                                        .plainText().serialize(component))
                                .toList()) : new ArrayList<>();
                        lore.removeIf(line -> line.equals("§7Click to select this item"));
                        if (lore.isEmpty() || !lore.get(0).startsWith("§ePrice:")) {
                            lore.add(0, "§ePrice: 5");
                        }
                        meta.lore(lore.stream().map(net.kyori.adventure.text.Component::text).toList());
                        newItem.setItemMeta(meta);
                    }
                    inventory.setItem(i, newItem);

                    break;
                }
            }
        }

        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
        if (traderTrait != null) {
            traderTrait.setShopInventory(inventory);
        }
    }

    private static boolean isInventoryFull(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBorderSlot(int slot, int inventorySize) {
        int rows = inventorySize / 9;
        int lastRowStart = (rows - 1) * 9;

        if (slot < 9 || slot >= lastRowStart) {
            return true;
        }

        return slot % 9 == 0 || slot % 9 == 8;
    }

    public static Inventory resizeInventory(Inventory inventory, NPC npc) {

        int newSize = Math.min(inventory.getSize() + 9, 54);

        Inventory newInventory = createShopInventory(newSize, npc);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null
                    && Objects.requireNonNull(inventory.getItem(i)).getType() != Material.AIR
                    && Objects.requireNonNull(inventory.getItem(i)).getType() != Material.GRAY_STAINED_GLASS_PANE) {
                newInventory.setItem(i, inventory.getItem(i));

            }
        }

        TraderTrait traderTrait = npc.getOrAddTrait(TraderTrait.class);
        if (traderTrait != null) {
            traderTrait.setShopInventory(newInventory);
        }

        return newInventory;
    }

    public static int calculateInventorySize(int itemCount) {
        return Math.min(54, ((itemCount / 9) + 1) * 9);
    }

    public static Inventory createShopInventory(int size, NPC npc) {
        String title = ShopGUI.SHOP_TITLE;
        Inventory inventory = Bukkit.createInventory(null, size, net.kyori.adventure.text.Component.text(title));
        applyInventoryStyle(inventory, title);
        return inventory;
    }

    public static void giveItem(Player player, ItemStack item, int quantity) {
        ItemStack itemToGive = item.clone();
        itemToGive.setAmount(quantity);
        player.getInventory().addItem(itemToGive);
    }

    public static int removeItemsFromInventory(Player player, ItemStack item, int quantity) {
        int itemsRemoved = 0;
        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem != null && invItem.isSimilar(item)) {
                int invItemAmount = invItem.getAmount();
                if (invItemAmount >= quantity - itemsRemoved) {
                    invItem.setAmount(invItemAmount - (quantity - itemsRemoved));
                    itemsRemoved = quantity;
                    break;
                } else {
                    itemsRemoved += invItemAmount;
                    invItem.setAmount(0);
                }
            }
        }
        return itemsRemoved;
    }
}
