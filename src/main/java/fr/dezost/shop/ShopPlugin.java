package fr.dezost.shop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.dezost.shop.commands.NPCCommand;
import fr.dezost.shop.commands.ShopTabCompleter;
import fr.dezost.shop.gui.*;
import fr.dezost.shop.listeners.NPCGUIListener;
import fr.dezost.shop.managers.NPCManager;
import fr.dezost.shop.traits.ShopTrait;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;
import fr.dezost.shop.utils.CheckUtils;

/**
 * Main class for the TraderPlugin.
 * Handles the initialization and setup of the plugin.
 */
public class ShopPlugin extends JavaPlugin {
    private AdminGUI adminGUI;
    private PriceSettingGUI priceSettingGUI;
    private QuantitySettingGUI quantitySettingGUI;
    private ShopGUI shopGUI;
    private PaymentMethodGUI paymentMethodGUI;
    private NPCRemoveGUI npcRemoveGUI;
    private NPCManager npcManager;

    private static Economy econ;

    /**
     * Called when the plugin is enabled.
     * Initializes the plugin, loads configuration, sets up commands and event
     * listeners.
     */
    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();
        Constants.loadConfig(getConfig());

        // Initialize GUIs and managers
        initializeGUIs();
        initializeManagers();

        // Register commands
        CheckUtils.registerCommand(this, "trader", new NPCCommand(npcManager), new ShopTabCompleter());

        // Register event listeners
        registerEventListeners();

        // Setup economy integration
        if (!setupEconomy()) {
            getLogger().severe("Disabled because no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register NPC trait
        registerNPCTrait();

        // Load all NPC inventories after a delay
        getServer().getScheduler().runTaskLater(this, InventoryUtils::loadAllInventories, 40L);

        getLogger().info("TraderPlugin enabled!");
    }

    /**
     * Called when the plugin is disabled.
     * Saves all NPC inventories.
     */
    @Override
    public void onDisable() {
        InventoryUtils.saveAllInventories();
        getLogger().info("TraderPlugin disabled!");
    }

    /**
     * Sets up the economy integration using Vault.
     *
     * @return true if the economy was successfully set up, otherwise false
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault dependency not found!");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().severe("No economy provider found!");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    /**
     * Gets the economy provider.
     *
     * @return the economy provider
     */
    public static Economy getEconomy() {
        return econ;
    }

    /**
     * Initializes the GUIs.
     */
    private void initializeGUIs() {
        adminGUI = new AdminGUI(this);
        priceSettingGUI = new PriceSettingGUI(this);
        quantitySettingGUI = new QuantitySettingGUI(this);
        shopGUI = new ShopGUI(this);
        paymentMethodGUI = new PaymentMethodGUI(this);
        npcRemoveGUI = new NPCRemoveGUI(this);
    }

    /**
     * Initializes the managers.
     */
    private void initializeManagers() {
        npcManager = new NPCManager(npcRemoveGUI);
    }

    /**
     * Registers the event listeners.
     */
    private void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new NPCGUIListener(this, adminGUI, priceSettingGUI,
                quantitySettingGUI, shopGUI, paymentMethodGUI, npcRemoveGUI), this);
    }

    /**
     * Registers the NPC trait.
     */
    private void registerNPCTrait() {
        net.citizensnpcs.api.CitizensAPI.getTraitFactory()
                .registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(ShopTrait.class));
    }
}