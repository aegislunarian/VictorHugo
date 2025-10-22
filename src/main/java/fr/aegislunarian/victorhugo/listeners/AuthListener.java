//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.listeners;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.utils.MessageTemplate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AuthListener implements Listener
{

    /**
     * Vérifie si le joueur est dans le monde d'authentification.
     * @param player Le joueur.
     * @return Si le joueur est dans le monde.
     */
    private boolean isInAuthWorld(Player player)
    {
        String worldName = Main.get().getConfig().getString("spawn.world");
        return player.getWorld().getName().equals(worldName);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();

        if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            String command = event.getMessage().split(" ")[0].toLowerCase();

            if (!command.equals("/login") && !command.equals("/register"))
            {
                player.sendMessage(MessageTemplate.adminErrorMessage("Tu dois te connecter avant d'utiliser une commande !"));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            if (player.getLocation().getY() < 0)
            {
                player.teleport(getSpawn());
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {
            if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            event.setCancelled(true);
            player.sendMessage(MessageTemplate.adminErrorMessage("Tu dois te connecter avant de l'ouvrir !"));
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event)
    {
        Player player = event.getPlayer();
        if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getWhoClicked() instanceof Player player)
        {
            if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            Action action = event.getAction();
            if (action != Action.PHYSICAL)
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event)
    {
        Player player = event.getPlayer();
        if(isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if(event.getEntity() instanceof Player player)
        {
            if(isInAuthWorld(player) && !Main.get().getAuthManager().isLoggedIn(player))
            {
                event.setCancelled(true);
            }
        }

    }

    /**
     * Récupérer la localisation du spawn du monde d'authentification.
     * @return la localisation du spawn.
     */
    private Location getSpawn()
    {
        return new Location(
                Bukkit.getWorld(Main.get().getConfig().getString("spawn.world")),
                Main.get().getConfig().getDouble("spawn.x"),
                Main.get().getConfig().getDouble("spawn.y"),
                Main.get().getConfig().getDouble("spawn.z")
        );
    }

}
