package fr.dezost.traderplugin.gui;

import fr.dezost.traderplugin.TraderPlugin;
import fr.dezost.traderplugin.utils.InventoryUtils;
import fr.dezost.traderplugin.utils.ItemUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NPCRemoveGUI {
    private final TraderPlugin plugin;

    public static final String REMOVE_NPC_GUI_TITLE = "§6§lRemove NPC";
    private static final String NPC_ITEM_NAME_PREFIX = "§a";
    private static final String NPC_ID_PREFIX = "§7ID: ";
    private static final String NPC_REMOVE_LORE = "§7Click to remove this NPC";

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
                net.kyori.adventure.text.Component.text(REMOVE_NPC_GUI_TITLE));

        for (NPC npc : npcs) {
            ItemStack npcItem = ItemUtils.createGuiItem(Material.PLAYER_HEAD, NPC_ITEM_NAME_PREFIX + npc.getName(),
                    NPC_ID_PREFIX + npc.getId(), NPC_REMOVE_LORE);
            gui.addItem(npcItem);
        }

        return gui;
    }
}