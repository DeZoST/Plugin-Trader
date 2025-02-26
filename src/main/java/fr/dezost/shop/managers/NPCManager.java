package fr.dezost.shop.managers;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

import fr.dezost.shop.gui.NPCRemoveGUI;
import fr.dezost.shop.traits.ShopTrait;
import fr.dezost.shop.utils.Constants;

import org.bukkit.entity.EntityType;

/**
 * Manages the creation, removal, and help commands for NPCs.
 */
public class NPCManager {
    private final NPCRemoveGUI npcRemoveGUI;

    /**
     * Constructs an NPCManager with the specified NPCRemoveGUI.
     *
     * @param npcRemoveGUI the GUI for removing NPCs
     */
    public NPCManager(NPCRemoveGUI npcRemoveGUI) {
        this.npcRemoveGUI = npcRemoveGUI;
    }

    /**
     * Handles the creation of a new NPC.
     *
     * @param player the player creating the NPC
     * @param args   the command arguments
     */
    public void handleCreate(Player player, String[] args) {
        // Check if the command has enough arguments
        if (args.length < 2) {
            handleHelp(player);
            return;
        }
        String npcName = args[1];
        // Create and spawn the NPC
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, npcName);
        npc.addTrait(ShopTrait.class);
        npc.spawn(player.getLocation());

        // Send a confirmation message to the player
        player.sendMessage(String.format(Constants.NPC_CREATED_MESSAGE, npcName));
    }

    /**
     * Handles the removal of an NPC.
     *
     * @param player the player removing the NPC
     */
    public void handleRemove(Player player) {
        // Open the GUI for removing NPCs
        npcRemoveGUI.openRemoveNPCGUI(player);
    }

    /**
     * Handles displaying the help message.
     *
     * @param player the player requesting help
     */
    public void handleHelp(Player player) {
        // Send help messages to the player
        player.sendMessage(Constants.HELP_HEADER_MESSAGE);
        player.sendMessage(Constants.HELP_CREATE_COMMAND_MESSAGE);
        player.sendMessage(Constants.HELP_REMOVE_COMMAND_MESSAGE);
        player.sendMessage(Constants.HELP_HELP_COMMAND_MESSAGE);
    }
}