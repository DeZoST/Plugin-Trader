package fr.dezost.shop.managers;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

import fr.dezost.shop.gui.NPCRemoveGUI;
import fr.dezost.shop.traits.TraderTrait;
import fr.dezost.shop.utils.Constants;

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

        player.sendMessage(String.format(Constants.NPC_CREATED_MESSAGE, npcName));
    }

    public void handleRemove(Player player) {
        npcRemoveGUI.openRemoveNPCGUI(player);
    }

    public void handleHelp(Player player) {
        player.sendMessage(Constants.HELP_HEADER_MESSAGE);
        player.sendMessage(Constants.HELP_CREATE_COMMAND_MESSAGE);
        player.sendMessage(Constants.HELP_REMOVE_COMMAND_MESSAGE);
        player.sendMessage(Constants.HELP_HELP_COMMAND_MESSAGE);
    }
}