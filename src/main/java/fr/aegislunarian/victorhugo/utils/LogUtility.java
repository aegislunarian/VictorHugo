//   ___      ___ ___  ________ _________  ________  ________          ___  ___  ___  ___  ________  ________
//  |\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \        |\  \|\  \|\  \|\  \|\   ____\|\   __  \
//  \ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \       \ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \
//   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\       \ \   __  \ \  \\\  \ \  \  __\ \  \\\  \
//    \ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \|       \ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \
//     \ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\        \ \__\ \__\ \_______\ \_______\ \_______\
//      \|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|        \|__|\|__|\|_______|\|_______|\|_______|

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
