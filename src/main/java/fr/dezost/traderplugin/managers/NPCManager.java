package fr.dezost.traderplugin.managers;

import fr.dezost.traderplugin.traits.TraderTrait;
import fr.dezost.traderplugin.gui.NPCRemoveGUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;

public class NPCManager {
    private final NPCRemoveGUI npcRemoveGUI;

    private static final String NPC_CREATED_MESSAGE = "§aPNJ Trader %s créé avec TraderTrait.";
    private static final String HELP_HEADER_MESSAGE = "§aCommandes du Plugin Trader:";
    private static final String HELP_CREATE_COMMAND_MESSAGE = "§e/trader create <nom_pnj> §7- Crée un nouveau PNJ trader avec TraderTrait. Nécessite la permission: trader.create";
    private static final String HELP_REMOVE_COMMAND_MESSAGE = "§e/trader remove §7- Supprime le PNJ sélectionné. Nécessite la permission: trader.remove";
    private static final String HELP_HELP_COMMAND_MESSAGE = "§e/trader help §7- Affiche ce message d'aide.";

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

        player.sendMessage(String.format(NPC_CREATED_MESSAGE, npcName));
    }

    public void handleRemove(Player player) {
        npcRemoveGUI.openRemoveNPCGUI(player);
    }

    public void handleHelp(Player player) {
        player.sendMessage(HELP_HEADER_MESSAGE);
        player.sendMessage(HELP_CREATE_COMMAND_MESSAGE);
        player.sendMessage(HELP_REMOVE_COMMAND_MESSAGE);
        player.sendMessage(HELP_HELP_COMMAND_MESSAGE);
    }
}