package net.herospvp.plugins.practiceheros.lang;

import net.herospvp.plugins.practiceheros.match.Unranked;
import org.bukkit.ChatColor;

public enum Lang {
    CHALLENGER_FOUND("&eSfidante trovato! &7(%nome%) (%ping%ms)"),
    SEARCH_CHALLENGER("&eCercando il tuo sfidante..."),
    RIGHT_CLICK("&7(Tasto destro)"),
    EXIT_FROM_WAITING_ITEM("&cEsci " + RIGHT_CLICK.toString() + ": %type%"),
    EXIT_FROM_WAITING("&cSei uscito dalla lista di attesa."),
    EDIT_KIT("&bModifica kit " + RIGHT_CLICK.toString()),
    SETTINGS_ITEM("&eImpostazioni " + RIGHT_CLICK.toString()),
    SCOREBOARD_DISABLED("&cScoreboard disabilitata."),
    PLAYER_QUIT_FROM_MATCH("&eIl tuo sfidante \u00e8 uscito dal gioco. Hai vinto 30 monete!"),
    SCOREBOARD_ENABLED("&aScoreboard abilitata."),
    MONEY("Monete"),
    ARENAS_OCCUPIED("&cLe arene sono tutte occupate, attendi il tuo turno."),
    ARENA_FOUNDED("&aArena trovata! &7(ID #%id%, creata da: %creator%)"),
    SECOND_3("&73 secondi..."),
    SECOND_2("&72 secondi..."),
    SECOND_1("&71 secondo..."),
    YOU_WIN_MATCH_UNRANKED("&aHai vinto il match contro &7%player%&a!"),
    YOU_LOST_MATCH_UNRANKED("&cHai perso il match contro &7%player%&c! &7(%health%cuori)"),
    SCOREBOARD_NAME_WAITING("&6Attendi"),
    SCOREBOARD_WAITING_VAR(new String[]{"&e" + Unranked.TIME_WAITING + ":-5"}),
    SCOREBOARD_WAITING(new String[]{"&f", "&f&l> &fKit", "&e%nomekit%", "&c&f", "&f&l> &fTempo attesa", "&eNON CAMBIARE", "&a", "&f&l> &fTipo", "&e%typeMatch%", "&d"}),
    SCOREBOARD_EDITOR(new String[]{"&f", "&f&l> &fInfo", "&7Sei nella zona &eeditor", "&7apri la chest davanti a te", "&7e posiziona gli item", "&7come vuoi nel tuo inventario.", "&7Quando hai finito,", "&eapri&7 la porta!", "&a", "&f&l> &r&fKit", "&e&l%kiteditor%", "&c"}),
    EXIT_FROM_EDITOR("&eSei uscito dall'editor"),
    TELEPORT_SPAWN_EDIT("&eSei stato teletrasportato alla zona editor."),
    AMOUNT_ITEM_EDITOR("&cDevi avere tutti gli item della chest nell'inventario per poterlo chiudere!"),
    USAGE("/practice create <arena>\n/practice set <1/2> <arena>\n/practice warp [warp]"),
    ARENA_ALREADY_SET("Arena gi\u00e0 impostata"),
    ARENA_CREATED("&eArena %nome% creata."),
    ZONE_SET("&eZona %numero% impostata."),
    USAGE_KIT_MANAGER("/createkit <nome>"),
    KIT_EXIST("&c(Kit esistente)"),
    KIT_CREATED("&eKit %nome% creato.");
    
    private String string;
    private String[] strings;

    private Lang(String string2) {
        this.string = string2;
    }

    private Lang(String[] strings) {
        this.strings = strings;
    }

    public String toString() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.string);
    }

    public String[] getStrings() {
        return this.strings;
    }
}

