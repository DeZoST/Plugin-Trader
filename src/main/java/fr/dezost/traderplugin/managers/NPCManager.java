package fr.dezost.traderplugin.managers;

import fr.dezost.traderplugin.traits.TraderTrait;
import fr.dezost.traderplugin.gui.NPCRemoveGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;

public class NPCManager {
    private final NPCRemoveGUI npcRemoveGUI;

    public NPCManager(NPCRemoveGUI npcRemoveGUI) {
        this.npcRemoveGUI = npcRemoveGUI;
    }

    public void handleCreate(Player player, String[] args) {
        if (args.length < 2) {
            handleHelp(player);
            return;
        }
        String npcName = args[1];
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc.addTrait(TraderTrait.class);
        npc.spawn(player.getLocation());

        player.sendMessage("§aTrader NPC " + npcName + " created with TraderTrait.");
    }

    public void handleRemove(Player player) {
        npcRemoveGUI.openRemoveNPCGUI(player);
    }

    public void handleHelp(Player player) {
        player.sendMessage("§aTrader Plugin Commands:");
        player.sendMessage(
                "§e/trader create <npc_name> §7- Creates a new trader NPC with TraderTrait. Requires permission: trader.create");
        player.sendMessage("§e/trader remove §7- Removes the selected NPC. Requires permission: trader.remove");
        player.sendMessage("§e/trader help §7- Shows this help message.");

    }
}
