package net.herospvp.plugins.practiceheros.match;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.scheduler.BukkitTask;

public class MatchUtils {
    public static HashMap<String, BukkitTask> SCOREBOARD_WAITING = new HashMap();
    public static HashMap<String, String> PLAYER_QUIT_CHECK = new HashMap();
    public static HashMap<String, String> TYPE_MATCH_IN_GAME = new HashMap();
    public static ArrayList<String> FREEZE_START = new ArrayList();
    public static String TYPE_MATCH;
}

