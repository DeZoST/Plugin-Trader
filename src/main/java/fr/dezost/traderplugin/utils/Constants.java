package fr.dezost.traderplugin.utils;

public class Constants {
    // Duplicate Constants
    public static final String INCREASE_1_TEXT = "§a§l+1";
    public static final String INCREASE_10_TEXT = "§a§l+10";
    public static final String INCREASE_100_TEXT = "§a§l+100";
    public static final String INCREASE_1_LORE = "§7Augmenter de 1";
    public static final String INCREASE_10_LORE = "§7Augmenter de 10";
    public static final String INCREASE_100_LORE = "§7Augmenter de 100";
    public static final String DECREASE_1_TEXT = "§c§l-1";
    public static final String DECREASE_10_TEXT = "§c§l-10";
    public static final String DECREASE_100_TEXT = "§c§l-100";
    public static final String DECREASE_1_LORE = "§7Diminuer de 1";
    public static final String DECREASE_10_LORE = "§7Diminuer de 10";
    public static final String DECREASE_100_LORE = "§7Diminuer de 100";
    public static final String CONFIRM_TEXT = "§2§lConfirmer";
    public static final String CONFIRM_LORE = "§7Cliquez pour confirmer";
    public static final String INSTRUCTIONS_TEXT = "§a§lInstructions";
    public static final String INSTRUCTIONS_LORE_1 = "§7Utilisez + / - pour ajuster la valeur";
    public static final String INSTRUCTIONS_LORE_2 = "§7Cliquez sur 'Confirmer' pour définir la valeur";

    // AdminGUI
    public static final String ADMIN_SHOP_TITLE = "§6§lActions de la boutique NPC";
    public static final String SELECT_ITEM_TITLE = "§6§lSélectionner un article à ajouter";
    public static final String MODIFY_ITEMS_TITLE = "§6§lModifier les articles de la boutique";
    public static final String DELETE_ITEMS_TITLE = "§6§lSupprimer les articles de la boutique";
    public static final String ADD_ITEMS_BUTTON_TEXT = "§a§lAjouter des articles";
    public static final String MODIFY_ITEMS_BUTTON_TEXT = "§e§lModifier les articles";
    public static final String DELETE_ITEMS_BUTTON_TEXT = "§c§lSupprimer les articles";
    public static final String ADD_ITEMS_BUTTON_LORE = "§7Cliquez pour ajouter de nouveaux articles";
    public static final String MODIFY_ITEMS_BUTTON_LORE = "§7Cliquez pour modifier les articles";
    public static final String DELETE_ITEMS_BUTTON_LORE = "§7Cliquez pour supprimer les articles";
    public static final String SELECT_ITEM_LORE = "§7Cliquez pour sélectionner cet article";
    public static final String NO_MORE_SPACE_MESSAGE = "§cPlus d'espace disponible pour les articles.";

    // NPCRemoveGUI
    public static final String REMOVE_NPC_GUI_TITLE = "§6§lSupprimer NPC";
    public static final String NPC_ITEM_NAME_PREFIX = "§a";
    public static final String NPC_ID_PREFIX = "§7ID: ";
    public static final String NPC_REMOVE_LORE = "§7Cliquez pour supprimer ce NPC";

    // PaymentMethodGUI
    public static final String BUY_PAYMENT_GUI_TITLE = "§6§lSélectionner le mode de paiement (Achat)";
    public static final String SELL_PAYMENT_GUI_TITLE = "§6§lSélectionner le mode de paiement (Vente)";
    public static final String PAY_WITH_EMERALDS_TEXT = "§a§lPayer avec des émeraudes";
    public static final String PAY_WITH_BANK_TEXT = "§a§lPayer avec la banque";
    public static final String RECEIVE_EMERALDS_TEXT = "§a§lRecevoir des émeraudes";
    public static final String RECEIVE_BANK_TEXT = "§a§lRecevoir à la banque";
    public static final String PAY_WITH_EMERALDS_LORE = "§7Payer avec des émeraudes dans l'inventaire";
    public static final String PAY_WITH_BANK_LORE = "§7Payer avec de l'argent à la banque";
    public static final String RECEIVE_EMERALDS_LORE = "§7Recevoir le paiement en émeraudes";
    public static final String RECEIVE_BANK_LORE = "§7Recevoir le paiement à la banque";

    // PriceSettingGUI
    public static final String PRICE_GUI_TITLE = "§6§lDéfinir le prix de l'article";
    public static final String DEFAULT_PRICE_LORE = "§ePrix: 5";

    // QuantitySettingGUI
    public static final String QUANTITY_GUI_TITLE = "§6§lSélectionner la quantité";
    public static final String DECREASE_64_TEXT = "§a§l-64";
    public static final String INCREASE_64_TEXT = "§a§l+64";
    public static final String DECREASE_64_LORE = "§7Diminuer de 64";
    public static final String INCREASE_64_LORE = "§7Augmenter de 64";

    // ShopGUI
    public static final String SHOP_TITLE = "§6§lMagasin";
    public static final String BUY_OR_SELL_TITLE = "§6§lAcheter ou Vendre";
    public static final String BUY_BUTTON_TEXT = "§a§lAcheter";
    public static final String SELL_BUTTON_TEXT = "§a§lVendre";
    public static final String BUY_BUTTON_LORE = "§7Acheter cet article";
    public static final String SELL_BUTTON_LORE = "§7Vendre cet article";

    // NPCGUIListener
    public static final String INTERACTING_NPC_METADATA = "interactingNPC";
    public static final String SELECTED_ITEM_INDEX_METADATA = "selectedItemIndex";
    public static final String SELECTED_ITEM_METADATA = "selectedItem";
    public static final String NPC_REMOVED_MESSAGE = "§aNPC %s supprimé.";
    public static final String NPC_NOT_FOUND_MESSAGE = "§cNPC non trouvé.";
    public static final String ERROR_PARSE_NPC_ID_MESSAGE = "§cErreur: Impossible de parser l'ID du NPC.";
    public static final String ERROR_ITEM_LORE_NULL_MESSAGE = "§cErreur: Lore de l'article est nul.";
    public static final String ERROR_ITEM_META_NULL_MESSAGE = "§cErreur: Meta de l'article est nul.";
    public static final String NO_PERMISSION_MODIFY_NPCS_MESSAGE = "§cVous n'avez pas la permission de modifier les PNJs.";
    public static final String ITEM_DELETED_MESSAGE = "§aArticle supprimé";
    public static final String ITEM_NOT_FOUND_MESSAGE = "§cArticle non trouvé dans l'inventaire de la boutique";
    public static final String FEATURE_NOT_RELEASED_MESSAGE = "§cCette fonctionnalité n'est pas encore sortie.";

    // PlayerQuitListener
    public static final String ONLY_PLAYERS_MESSAGE = "Cette commande est utilisable seulement par les joueurs.";
    public static final String NO_PERMISSION_CREATE_MESSAGE = "§cVous n'avez pas la permission de créer des PNJs.";
    public static final String NO_PERMISSION_REMOVE_MESSAGE = "§cVous n'avez pas la permission de supprimer des PNJs.";

    // ItemUtils
    public static final String PRICE_PREFIX = "Prix: ";
    public static final String LORE_KEY = "lore";
    public static final String PLUGIN_NAMESPACE = "traderplugin";

    // InventoryUtils
    public static final String NPC_DOES_NOT_EXIST_MESSAGE = "NPC %d n'existe pas. Ignorer la sauvegarde.";
    public static final String INVENTORY_SAVED_MESSAGE = "Inventaire sauvegardé pour NPC %d dans le fichier %s";
    public static final String SAVING_ALL_INVENTORIES_MESSAGE = "Sauvegarde de tous les inventaires NPC...";
    public static final String ALL_INVENTORIES_SAVED_MESSAGE = "Tous les inventaires NPC ont été sauvegardés.";
    public static final String LOADING_INVENTORIES_MESSAGE = "Chargement des inventaires depuis le dossier: %s";
    public static final String PROCESSING_FILE_MESSAGE = "Traitement du fichier: %s";
    public static final String NPC_DOES_NOT_EXIST_DELETING_FILE_MESSAGE = "NPC avec ID %d n'existe pas. Suppression du fichier: %s";
    public static final String NPC_EXISTS_LOADING_INVENTORY_MESSAGE = "NPC avec ID %d existe. Chargement de l'inventaire.";
    public static final String INVENTORY_LOADED_MESSAGE = "Inventaire chargé pour NPC avec ID %d";
    public static final String NO_INVENTORY_FOUND_CREATING_DEFAULT_MESSAGE = "Aucun inventaire trouvé pour NPC avec ID %d. Création d'un inventaire par défaut.";
    public static final String NEW_PRICE_SET_MESSAGE = "§aNouveau prix défini: %d";
    public static final String ERROR_INVALID_PRICE_FORMAT_MESSAGE = "§cErreur: Format de prix invalide.";
    public static final String NEW_QUANTITY_SET_MESSAGE = "§aNouvelle quantité définie: %d";
    public static final String INVENTORY_FOLDER_PATH = "plugins/TraderPlugin/inventories";
    public static final String INVENTORY_SLOT_PREFIX = "inventory.slot";
    public static final String SHOP_INVENTORY_DATA = "shopInventory";
    public static final String PRICE_LORE_PREFIX = "§ePrix: ";
    public static final String UNIQUE_ID_LORE = "§7ID Unique:";
    public static final String BORDER_ITEM_DISPLAY_NAME = " ";

    // NPCManager
    public static final String NPC_CREATED_MESSAGE = "§aPNJ Trader %s créé avec TraderTrait.";
    public static final String HELP_HEADER_MESSAGE = "§aCommandes du Plugin Trader:";
    public static final String HELP_CREATE_COMMAND_MESSAGE = "§e/trader create <nom_pnj> §7- Crée un nouveau PNJ trader avec TraderTrait. Nécessite la permission: trader.create";
    public static final String HELP_REMOVE_COMMAND_MESSAGE = "§e/trader remove §7- Supprime le PNJ sélectionné. Nécessite la permission: trader.remove";
    public static final String HELP_HELP_COMMAND_MESSAGE = "§e/trader help §7- Affiche ce message d'aide.";

    // TraderTrait
    public static final String INVENTORY_ALREADY_LOADED_MESSAGE = "L'inventaire est déjà chargé.";
    public static final String INVENTORY_LOADED_FROM_FILE_MESSAGE = "L'inventaire a été chargé depuis le fichier.";
    public static final String NEW_TRADERSHOP_ASSIGNED_MESSAGE = " a été assigné à une nouvelle boutique Trader avec un inventaire par défaut.";
}