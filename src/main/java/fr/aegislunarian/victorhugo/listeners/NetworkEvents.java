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
import fr.aegislunarian.victorhugo.utils.MessageTemplate;
import fr.aegislunarian.victorhugo.utils.TitleUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NetworkEvents implements Listener
{
    final Main main = Main.get();
    FileConfiguration configuration = main.getConfig();

    /**
     * Logique lorsque le joueur rejoint la partie.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Account playerAccount = main.getAccountManager().createOrLoadAccount(player.getUniqueId());

        String motd = configuration.getString("motd");

        if(!motd.isEmpty())
        {
            TitleUtils.sendTitleToPlayer(player, "§d[VICTOR HUGO]", ChatColor.translateAlternateColorCodes('&', motd), 10, 70, 20);
        }

        event.setJoinMessage(MessageTemplate.adminMessage(playerAccount.getRank().getPrefix() + player.getName() + (playerAccount.isModerator() ? "§c*" : "") + " §eà rejoint la partie !"));
    }


    /**
     * Logique lorsque le joueur quitte la partie.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Account playerAccount = main.getAccountManager().createOrLoadAccount(player.getUniqueId());

        saveLocation(player);

        event.setQuitMessage(MessageTemplate.adminMessage(playerAccount.getRank().getPrefix() + player.getName() + (playerAccount.isModerator() ? "§c*" : "") + " §eà quitté la partie !"));
    }

    /**
     * Fonction pour sauvegarder la localisation du joueur.
     */
    public static void saveLocation(Player player)
    {
        if(!player.getWorld().getName().equals("loggin")) Main.get().getAccountManager().getAccount(player.getUniqueId()).setLastKnownLocation(player.getLocation());
    }

}
