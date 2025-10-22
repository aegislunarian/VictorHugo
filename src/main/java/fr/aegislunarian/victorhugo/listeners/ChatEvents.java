//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.listeners;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.core.player.Account;
import fr.aegislunarian.victorhugo.utils.LogUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatEvents implements Listener
{
    Main main = Main.get();
    List<String> bannedWords = main.getConfig().getStringList("banned_words");

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        Account playerAccount = main.getAccountManager().getAccount(player.getUniqueId());

        if(!main.getAuthManager().isLoggedIn(player))
        {
            return;
        }

        String message = event.getMessage();

        for (String word : bannedWords)
        {
            LogUtility.log(player.getPlayer().getName() + " a dit " + word + " !");
            message = message.replace(word, "*".repeat(word.length()));
        }

        event.setMessage(playerAccount.isModerator() ? ChatColor.translateAlternateColorCodes('&', message) : message);

        event.setFormat(playerAccount.getRank().getPrefix() + "%s §8» §r%s");
    }
}
