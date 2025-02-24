package fr.dezost.shop.gui;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.dezost.shop.TraderPlugin;
import fr.dezost.shop.utils.Constants;
import fr.dezost.shop.utils.InventoryUtils;
import fr.dezost.shop.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class NPCRemoveGUI {
    private final TraderPlugin plugin;

    public NPCRemoveGUI(TraderPlugin plugin) {
        this.plugin = plugin;
    }

    public void openRemoveNPCGUI(Player player) {
        Inventory removeNPCGUI = createRemoveNPCInventory();
        player.openInventory(removeNPCGUI);
    }

    private Inventory createRemoveNPCInventory() {
        List<NPC> npcs = new ArrayList<>();
        CitizensAPI.getNPCRegistry().sorted().forEach(npcs::add);
        int size = InventoryUtils.calculateInventorySize(npcs.size());
        Inventory gui = plugin.getServer().createInventory(null, size,
                net.kyori.adventure.text.Component.text(Constants.REMOVE_NPC_GUI_TITLE));

        for (NPC npc : npcs) {
            ItemStack npcItem = ItemUtils.createGuiItem(Material.PLAYER_HEAD,
                    Constants.NPC_ITEM_NAME_PREFIX + npc.getName(),
                    Constants.NPC_ID_PREFIX + npc.getId(), Constants.NPC_REMOVE_LORE);
            gui.addItem(npcItem);
        }

        return gui;
    }
}