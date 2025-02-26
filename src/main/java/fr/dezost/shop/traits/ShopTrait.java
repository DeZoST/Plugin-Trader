package fr.dezost.shop.traits;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dezost.shop.ShopPlugin;
import fr.dezost.shop.gui.AdminGUI;
import fr.dezost.shop.gui.PaymentMethodGUI;
import fr.dezost.shop.gui.PriceSettingGUI;
import fr.dezost.shop.gui.QuantitySettingGUI;
import fr.dezost.shop.gui.ShopGUI;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;

/**
 * Trait for NPCs that act as traders.
 * Handles the interaction and inventory management for trader NPCs.
 */
@TraitName("TraderShop")
public class ShopTrait extends Trait {
    private Inventory shopInventory;
    private final ShopPlugin plugin;
    private final AdminGUI adminGUI;
    private final PriceSettingGUI priceSettingGUI;
    private final QuantitySettingGUI quantitySettingGUI;
    private final ShopGUI shopGUI;
    private final PaymentMethodGUI paymentMethodGUI;

    /**
     * Constructs a TraderTrait and initializes the plugin and GUIs.
     */
    public ShopTrait() {
        super("TraderShop");
        plugin = JavaPlugin.getPlugin(ShopPlugin.class);
        adminGUI = new AdminGUI(plugin);
        priceSettingGUI = new PriceSettingGUI(plugin);
        quantitySettingGUI = new QuantitySettingGUI(plugin);
        shopGUI = new ShopGUI(plugin);
        paymentMethodGUI = new PaymentMethodGUI(plugin);
    }

    /**
     * Called when the trait is attached to an NPC.
     * Loads or creates the shop inventory for the NPC.
     */
    @Override
    public void onAttach() {
        // Check if the NPC already has an inventory
        Inventory existingInventory = this.getNPC().data().get(Constants.SHOP_INVENTORY_DATA, null);

        if (existingInventory != null) {
            shopInventory = existingInventory;
            plugin.getServer().getLogger().info(this.npc.getName() + Constants.INVENTORY_ALREADY_LOADED_MESSAGE);
        } else {
            // Load the inventory from file or create a new one
            Inventory loadedInventory = InventoryUtils.loadInventory(this.getNPC());

            if (loadedInventory != null) {
                shopInventory = loadedInventory;
                plugin.getServer().getLogger().info(this.npc.getName() + Constants.INVENTORY_LOADED_FROM_FILE_MESSAGE);
            } else {
                shopInventory = InventoryUtils.createShopInventory(27, this.getNPC());
                plugin.getServer().getLogger()
                        .info(this.npc.getName() + Constants.NEW_TRADERSHOP_ASSIGNED_MESSAGE);
            }

            this.getNPC().data().set(Constants.SHOP_INVENTORY_DATA, shopInventory);
        }
    }

    /**
     * Handles right-click events on the NPC.
     * Opens the admin GUI if the player is sneaking and is an operator, otherwise
     * opens the shop GUI.
     *
     * @param event the NPCRightClickEvent
     */
    @EventHandler
    public void onRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        if (npc == this.getNPC()) {
            if (player.isSneaking() && player.isOp()) {
                player.setMetadata(Constants.INTERACTING_NPC_METADATA, new FixedMetadataValue(plugin, npc.getId()));
                adminGUI.openAdminActionSelectionGUI(player);
            } else {
                player.setMetadata(Constants.INTERACTING_NPC_METADATA, new FixedMetadataValue(plugin, npc.getId()));
                shopGUI.openShop(player, npc);
            }
        }
    }

    /**
     * Sets the shop inventory for the NPC.
     *
     * @param newInventory the new inventory to set
     */
    public void setShopInventory(Inventory newInventory) {
        this.shopInventory = newInventory;
        this.getNPC().data().set(Constants.SHOP_INVENTORY_DATA, newInventory);
    }

    /**
     * Gets the shop inventory of the NPC.
     *
     * @return the shop inventory
     */
    public Inventory getShopInventory() {
        return shopInventory;
    }

    @Override
    public void onDespawn() {
        // No specific action needed on despawn
    }

    @Override
    public void onSpawn() {
        // No specific action needed on spawn
    }

    @Override
    public void onRemove() {
        // No specific action needed on remove
    }
}