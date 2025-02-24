package fr.dezost.traderplugin.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class Constants {
    // Configurable Constants
    public static String INCREASE_1_TEXT;
    public static String INCREASE_10_TEXT;
    public static String INCREASE_100_TEXT;
    public static String INCREASE_1_LORE;
    public static String INCREASE_10_LORE;
    public static String INCREASE_100_LORE;
    public static String DECREASE_1_TEXT;
    public static String DECREASE_10_TEXT;
    public static String DECREASE_100_TEXT;
    public static String DECREASE_1_LORE;
    public static String DECREASE_10_LORE;
    public static String DECREASE_100_LORE;
    public static String CONFIRM_TEXT;
    public static String CONFIRM_LORE;
    public static String INSTRUCTIONS_TEXT;
    public static String INSTRUCTIONS_LORE_1;
    public static String INSTRUCTIONS_LORE_2;

    // AdminGUI
    public static String ADMIN_SHOP_TITLE;
    public static String SELECT_ITEM_TITLE;
    public static String MODIFY_ITEMS_TITLE;
    public static String DELETE_ITEMS_TITLE;
    public static String ADD_ITEMS_BUTTON_TEXT;
    public static String MODIFY_ITEMS_BUTTON_TEXT;
    public static String DELETE_ITEMS_BUTTON_TEXT;
    public static String ADD_ITEMS_BUTTON_LORE;
    public static String MODIFY_ITEMS_BUTTON_LORE;
    public static String DELETE_ITEMS_BUTTON_LORE;
    public static String SELECT_ITEM_LORE;
    public static String NO_MORE_SPACE_MESSAGE;

    // NPCRemoveGUI
    public static String REMOVE_NPC_GUI_TITLE;
    public static String NPC_ITEM_NAME_PREFIX;
    public static String NPC_ID_PREFIX;
    public static String NPC_REMOVE_LORE;

    // PaymentMethodGUI
    public static String BUY_PAYMENT_GUI_TITLE;
    public static String SELL_PAYMENT_GUI_TITLE;
    public static String PAY_WITH_EMERALDS_TEXT;
    public static String PAY_WITH_BANK_TEXT;
    public static String RECEIVE_EMERALDS_TEXT;
    public static String RECEIVE_BANK_TEXT;
    public static String PAY_WITH_EMERALDS_LORE;
    public static String PAY_WITH_BANK_LORE;
    public static String RECEIVE_EMERALDS_LORE;
    public static String RECEIVE_BANK_LORE;

    // PriceSettingGUI
    public static String PRICE_GUI_TITLE;
    public static String DEFAULT_PRICE_LORE;

    // QuantitySettingGUI
    public static String QUANTITY_GUI_TITLE;
    public static String DECREASE_64_TEXT;
    public static String INCREASE_64_TEXT;
    public static String DECREASE_64_LORE;
    public static String INCREASE_64_LORE;

    // ShopGUI
    public static String SHOP_TITLE;
    public static String BUY_OR_SELL_TITLE;
    public static String BUY_BUTTON_TEXT;
    public static String SELL_BUTTON_TEXT;
    public static String BUY_BUTTON_LORE;
    public static String SELL_BUTTON_LORE;

    // NPCGUIListener
    public static String NPC_REMOVED_MESSAGE;
    public static String NPC_NOT_FOUND_MESSAGE;
    public static String ERROR_PARSE_NPC_ID_MESSAGE;
    public static String ERROR_ITEM_LORE_NULL_MESSAGE;
    public static String ERROR_ITEM_META_NULL_MESSAGE;
    public static String NO_PERMISSION_MODIFY_NPCS_MESSAGE;
    public static String ITEM_DELETED_MESSAGE;
    public static String ITEM_NOT_FOUND_MESSAGE;
    public static String FEATURE_NOT_RELEASED_MESSAGE;

    // PlayerQuitListener
    public static String ONLY_PLAYERS_MESSAGE;
    public static String NO_PERMISSION_CREATE_MESSAGE;
    public static String NO_PERMISSION_REMOVE_MESSAGE;

    // InventoryUtils
    public static String NPC_DOES_NOT_EXIST_MESSAGE;
    public static String INVENTORY_SAVED_MESSAGE;
    public static String SAVING_ALL_INVENTORIES_MESSAGE;
    public static String ALL_INVENTORIES_SAVED_MESSAGE;
    public static String LOADING_INVENTORIES_MESSAGE;
    public static String PROCESSING_FILE_MESSAGE;
    public static String NPC_DOES_NOT_EXIST_DELETING_FILE_MESSAGE;
    public static String NPC_EXISTS_LOADING_INVENTORY_MESSAGE;
    public static String INVENTORY_LOADED_MESSAGE;
    public static String NO_INVENTORY_FOUND_CREATING_DEFAULT_MESSAGE;
    public static String NEW_PRICE_SET_MESSAGE;
    public static String ERROR_INVALID_PRICE_FORMAT_MESSAGE;
    public static String NEW_QUANTITY_SET_MESSAGE;

    // NPCManager
    public static String NPC_CREATED_MESSAGE;
    public static String HELP_HEADER_MESSAGE;
    public static String HELP_CREATE_COMMAND_MESSAGE;
    public static String HELP_REMOVE_COMMAND_MESSAGE;
    public static String HELP_HELP_COMMAND_MESSAGE;

    // TraderTrait
    public static String INVENTORY_ALREADY_LOADED_MESSAGE;
    public static String INVENTORY_LOADED_FROM_FILE_MESSAGE;
    public static String NEW_TRADERSHOP_ASSIGNED_MESSAGE;

    // Non-configurable Constants
    public static final String INTERACTING_NPC_METADATA = "interactingNPC";
    public static final String SELECTED_ITEM_INDEX_METADATA = "selectedItemIndex";
    public static final String SELECTED_ITEM_METADATA = "selectedItem";
    public static final String PRICE_PREFIX = "Prix: ";
    public static final String LORE_KEY = "lore";
    public static final String PLUGIN_NAMESPACE = "traderplugin";
    public static final String INVENTORY_FOLDER_PATH = "plugins/TraderPlugin/inventories";
    public static final String INVENTORY_SLOT_PREFIX = "inventory.slot";
    public static final String SHOP_INVENTORY_DATA = "shopInventory";
    public static final String PRICE_LORE_PREFIX = "§ePrix: ";
    public static final String UNIQUE_ID_LORE = "§7ID Unique:";
    public static final String BORDER_ITEM_DISPLAY_NAME = " ";

    public static void loadConfig(FileConfiguration config) {
        INCREASE_1_TEXT = config.getString("messages.increase_1_text");
        INCREASE_10_TEXT = config.getString("messages.increase_10_text");
        INCREASE_100_TEXT = config.getString("messages.increase_100_text");
        INCREASE_1_LORE = config.getString("messages.increase_1_lore");
        INCREASE_10_LORE = config.getString("messages.increase_10_lore");
        INCREASE_100_LORE = config.getString("messages.increase_100_lore");
        DECREASE_1_TEXT = config.getString("messages.decrease_1_text");
        DECREASE_10_TEXT = config.getString("messages.decrease_10_text");
        DECREASE_100_TEXT = config.getString("messages.decrease_100_text");
        DECREASE_1_LORE = config.getString("messages.decrease_1_lore");
        DECREASE_10_LORE = config.getString("messages.decrease_10_lore");
        DECREASE_100_LORE = config.getString("messages.decrease_100_lore");
        CONFIRM_TEXT = config.getString("messages.confirm_text");
        CONFIRM_LORE = config.getString("messages.confirm_lore");
        INSTRUCTIONS_TEXT = config.getString("messages.instructions_text");
        INSTRUCTIONS_LORE_1 = config.getString("messages.instructions_lore_1");
        INSTRUCTIONS_LORE_2 = config.getString("messages.instructions_lore_2");

        ADMIN_SHOP_TITLE = config.getString("messages.admin_shop_title");
        SELECT_ITEM_TITLE = config.getString("messages.select_item_title");
        MODIFY_ITEMS_TITLE = config.getString("messages.modify_items_title");
        DELETE_ITEMS_TITLE = config.getString("messages.delete_items_title");
        ADD_ITEMS_BUTTON_TEXT = config.getString("messages.add_items_button_text");
        MODIFY_ITEMS_BUTTON_TEXT = config.getString("messages.modify_items_button_text");
        DELETE_ITEMS_BUTTON_TEXT = config.getString("messages.delete_items_button_text");
        ADD_ITEMS_BUTTON_LORE = config.getString("messages.add_items_button_lore");
        MODIFY_ITEMS_BUTTON_LORE = config.getString("messages.modify_items_button_lore");
        DELETE_ITEMS_BUTTON_LORE = config.getString("messages.delete_items_button_lore");
        SELECT_ITEM_LORE = config.getString("messages.select_item_lore");
        NO_MORE_SPACE_MESSAGE = config.getString("messages.no_more_space_message");

        REMOVE_NPC_GUI_TITLE = config.getString("messages.remove_npc_gui_title");
        NPC_ITEM_NAME_PREFIX = config.getString("messages.npc_item_name_prefix");
        NPC_ID_PREFIX = config.getString("messages.npc_id_prefix");
        NPC_REMOVE_LORE = config.getString("messages.npc_remove_lore");

        BUY_PAYMENT_GUI_TITLE = config.getString("messages.buy_payment_gui_title");
        SELL_PAYMENT_GUI_TITLE = config.getString("messages.sell_payment_gui_title");
        PAY_WITH_EMERALDS_TEXT = config.getString("messages.pay_with_emeralds_text");
        PAY_WITH_BANK_TEXT = config.getString("messages.pay_with_bank_text");
        RECEIVE_EMERALDS_TEXT = config.getString("messages.receive_emeralds_text");
        RECEIVE_BANK_TEXT = config.getString("messages.receive_bank_text");
        PAY_WITH_EMERALDS_LORE = config.getString("messages.pay_with_emeralds_lore");
        PAY_WITH_BANK_LORE = config.getString("messages.pay_with_bank_lore");
        RECEIVE_EMERALDS_LORE = config.getString("messages.receive_emeralds_lore");
        RECEIVE_BANK_LORE = config.getString("messages.receive_bank_lore");

        PRICE_GUI_TITLE = config.getString("messages.price_gui_title");
        DEFAULT_PRICE_LORE = config.getString("messages.default_price_lore");

        QUANTITY_GUI_TITLE = config.getString("messages.quantity_gui_title");
        DECREASE_64_TEXT = config.getString("messages.decrease_64_text");
        INCREASE_64_TEXT = config.getString("messages.increase_64_text");
        DECREASE_64_LORE = config.getString("messages.decrease_64_lore");
        INCREASE_64_LORE = config.getString("messages.increase_64_lore");

        SHOP_TITLE = config.getString("messages.shop_title");
        BUY_OR_SELL_TITLE = config.getString("messages.buy_or_sell_title");
        BUY_BUTTON_TEXT = config.getString("messages.buy_button_text");
        SELL_BUTTON_TEXT = config.getString("messages.sell_button_text");
        BUY_BUTTON_LORE = config.getString("messages.buy_button_lore");
        SELL_BUTTON_LORE = config.getString("messages.sell_button_lore");

        NPC_REMOVED_MESSAGE = config.getString("messages.npc_removed_message");
        NPC_NOT_FOUND_MESSAGE = config.getString("messages.npc_not_found_message");
        ERROR_PARSE_NPC_ID_MESSAGE = config.getString("messages.error_parse_npc_id_message");
        ERROR_ITEM_LORE_NULL_MESSAGE = config.getString("messages.error_item_lore_null_message");
        ERROR_ITEM_META_NULL_MESSAGE = config.getString("messages.error_item_meta_null_message");
        NO_PERMISSION_MODIFY_NPCS_MESSAGE = config.getString("messages.no_permission_modify_npcs_message");
        ITEM_DELETED_MESSAGE = config.getString("messages.item_deleted_message");
        ITEM_NOT_FOUND_MESSAGE = config.getString("messages.item_not_found_message");
        FEATURE_NOT_RELEASED_MESSAGE = config.getString("messages.feature_not_released_message");

        ONLY_PLAYERS_MESSAGE = config.getString("messages.only_players_message");
        NO_PERMISSION_CREATE_MESSAGE = config.getString("messages.no_permission_create_message");
        NO_PERMISSION_REMOVE_MESSAGE = config.getString("messages.no_permission_remove_message");

        NPC_DOES_NOT_EXIST_MESSAGE = config.getString("messages.npc_does_not_exist_message");
        INVENTORY_SAVED_MESSAGE = config.getString("messages.inventory_saved_message");
        SAVING_ALL_INVENTORIES_MESSAGE = config.getString("messages.saving_all_inventories_message");
        ALL_INVENTORIES_SAVED_MESSAGE = config.getString("messages.all_inventories_saved_message");
        LOADING_INVENTORIES_MESSAGE = config.getString("messages.loading_inventories_message");
        PROCESSING_FILE_MESSAGE = config.getString("messages.processing_file_message");
        NPC_DOES_NOT_EXIST_DELETING_FILE_MESSAGE = config
                .getString("messages.npc_does_not_exist_deleting_file_message");
        NPC_EXISTS_LOADING_INVENTORY_MESSAGE = config.getString("messages.npc_exists_loading_inventory_message");
        INVENTORY_LOADED_MESSAGE = config.getString("messages.inventory_loaded_message");
        NO_INVENTORY_FOUND_CREATING_DEFAULT_MESSAGE = config
                .getString("messages.no_inventory_found_creating_default_message");
        NEW_PRICE_SET_MESSAGE = config.getString("messages.new_price_set_message");
        ERROR_INVALID_PRICE_FORMAT_MESSAGE = config.getString("messages.error_invalid_price_format_message");
        NEW_QUANTITY_SET_MESSAGE = config.getString("messages.new_quantity_set_message");

        NPC_CREATED_MESSAGE = config.getString("messages.npc_created_message");
        HELP_HEADER_MESSAGE = config.getString("messages.help_header_message");
        HELP_CREATE_COMMAND_MESSAGE = config.getString("messages.help_create_command_message");
        HELP_REMOVE_COMMAND_MESSAGE = config.getString("messages.help_remove_command_message");
        HELP_HELP_COMMAND_MESSAGE = config.getString("messages.help_help_command_message");

        INVENTORY_ALREADY_LOADED_MESSAGE = config.getString("messages.inventory_already_loaded_message");
        INVENTORY_LOADED_FROM_FILE_MESSAGE = config.getString("messages.inventory_loaded_from_file_message");
        NEW_TRADERSHOP_ASSIGNED_MESSAGE = config.getString("messages.new_tradershop_assigned_message");
    }
}