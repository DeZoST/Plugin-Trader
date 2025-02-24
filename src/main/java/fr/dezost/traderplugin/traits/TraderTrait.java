package fr.dezost.traderplugin.traits;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.gui.AdminGUI;
import fr.dezost.traderplugin.gui.PriceSettingGUI;
import fr.dezost.traderplugin.gui.QuantitySettingGUI;
import fr.dezost.traderplugin.gui.ShopGUI;
import fr.dezost.traderplugin.gui.PaymentMethodGUI;
import fr.dezost.traderplugin.utils.InventoryUtils;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

@TraitName("TraderShop")
public class TraderTrait extends Trait {
    private Inventory shopInventory;
    private final TraderPlugin plugin;
    private final AdminGUI adminGUI;
    private final PriceSettingGUI priceSettingGUI;
    private final QuantitySettingGUI quantitySettingGUI;
    private final ShopGUI shopGUI;
    private final PaymentMethodGUI paymentMethodGUI;

    public TraderTrait() {
        super("TraderShop");
        plugin = JavaPlugin.getPlugin(TraderPlugin.class);
        adminGUI = new AdminGUI(plugin);
        priceSettingGUI = new PriceSettingGUI(plugin);
        quantitySettingGUI = new QuantitySettingGUI(plugin);
        shopGUI = new ShopGUI(plugin);
        paymentMethodGUI = new PaymentMethodGUI(plugin);
    }

    @Override
    public void onAttach() {

        Inventory existingInventory = this.getNPC().data().get("shopInventory", null);

        if (existingInventory != null) {
            shopInventory = existingInventory;
            plugin.getServer().getLogger().info(this.npc.getName() + "'s inventory already loaded.");
        } else {

            Inventory loadedInventory = InventoryUtils.loadInventory(this.getNPC());

            if (loadedInventory != null) {
                shopInventory = loadedInventory;
                plugin.getServer().getLogger().info(this.npc.getName() + "'s inventory loaded from file.");
            } else {
                shopInventory = InventoryUtils.createShopInventory(27, this.getNPC());
                plugin.getServer().getLogger()
                        .info(this.npc.getName() + " has been assigned a new TraderShop with a default inventory.");
            }

            this.getNPC().data().set("shopInventory", shopInventory);
        }
    }

    @EventHandler
    public void onRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        if (npc == this.getNPC()) {
            if (player.isSneaking() && player.isOp()) {
                player.setMetadata("interactingNPC", new FixedMetadataValue(plugin, npc.getId()));
                adminGUI.openAdminActionSelectionGUI(player);
            } else {
                player.setMetadata("interactingNPC", new FixedMetadataValue(plugin, npc.getId()));
                shopGUI.openShop(player, npc);
            }
        }
    }

    public void setShopInventory(Inventory newInventory) {
        this.shopInventory = newInventory;
        this.getNPC().data().set("shopInventory", newInventory);

    }

    public Inventory getShopInventory() {
        return shopInventory;
    }

    @Override
    public void onDespawn() {
    }

    @Override
    public void onSpawn() {
    }

    @Override
    public void onRemove() {
    }
}