package fr.aegislunarian.victorhugo.listeners;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.core.player.Account;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NetworkEvents implements Listener
{
    final Main main = Main.get();

    /**
     * Logique lorsque le joueur rejoint la partie.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Account playerAccount = main.getAccountManager().createOrLoadAccount(player.getUniqueId());
    }

    /**
     * Logique lorsque le joueur quitte la partie.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Account playerAccount = main.getAccountManager().getAccount(player.getUniqueId());
    }


}
