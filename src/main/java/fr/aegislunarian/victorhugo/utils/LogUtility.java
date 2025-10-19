package fr.aegislunarian.victorhugo.utils;

import fr.aegislunarian.victorhugo.Main;
import org.bukkit.Bukkit;

public class LogUtility
{
    public static String prefix = Main.get().getConfig().getString("admin_prefix");

    public static void log(String message)
    {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + message);
    }
}
