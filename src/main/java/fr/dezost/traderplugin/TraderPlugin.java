package fr.dezost.traderplugin;

import fr.dezost.traderplugin.commands.NPCCommand;
import fr.dezost.traderplugin.commands.TraderTabCompleter;
import fr.dezost.traderplugin.gui.*;
import fr.dezost.traderplugin.listeners.NPCGUIListener;
import fr.dezost.traderplugin.managers.NPCManager;
import fr.dezost.traderplugin.traits.TraderTrait;
import fr.dezost.traderplugin.utils.Constants;
import fr.dezost.traderplugin.utils.InventoryUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TraderPlugin extends JavaPlugin {
    private AdminGUI adminGUI;
    private PriceSettingGUI priceSettingGUI;
    private QuantitySettingGUI quantitySettingGUI;
    private ShopGUI shopGUI;
    private PaymentMethodGUI paymentMethodGUI;
    private NPCRemoveGUI npcRemoveGUI;
    private NPCManager npcManager;

    private static Economy econ;

    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();
        Constants.loadConfig(getConfig());

        adminGUI = new AdminGUI(this);
        priceSettingGUI = new PriceSettingGUI(this);
        quantitySettingGUI = new QuantitySettingGUI(this);
        shopGUI = new ShopGUI(this);
        paymentMethodGUI = new PaymentMethodGUI(this);
        npcRemoveGUI = new NPCRemoveGUI(this);
        npcManager = new NPCManager(npcRemoveGUI);

        getCommand("trader").setExecutor(new NPCCommand(npcManager));

        getServer().getPluginManager().registerEvents(new NPCGUIListener(this, adminGUI, priceSettingGUI,
                quantitySettingGUI, shopGUI, paymentMethodGUI, npcRemoveGUI), this);

        if (!setupEconomy()) {
            getLogger().severe("Disabled because no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        net.citizensnpcs.api.CitizensAPI.getTraitFactory()
                .registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(TraderTrait.class));

        Objects.requireNonNull(getCommand("trader")).setExecutor(new NPCCommand(npcManager));
        Objects.requireNonNull(getCommand("trader")).setTabCompleter(new TraderTabCompleter());

        getServer().getScheduler().runTaskLater(this, InventoryUtils::loadAllInventories, 40L);

        getLogger().info("TraderPlugin enabled!");
    }

    @Override
    public void onDisable() {
        InventoryUtils.saveAllInventories();
        getLogger().info("TraderPlugin disabled!");
    }

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
        Economy econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}