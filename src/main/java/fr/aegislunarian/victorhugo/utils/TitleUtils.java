//  ______   ___       ___  ___  ________   ________  ________  ___  ________  ________           ___       ___  ________  ______
// |\   ___\|\  \     |\  \|\  \|\   ___  \|\   __  \|\   __  \|\  \|\   __  \|\   ___  \        |\  \     |\  \|\   __  \|\___   \
// \ \  \__|\ \  \    \ \  \\\  \ \  \\ \  \ \  \|\  \ \  \|\  \ \  \ \  \|\  \ \  \\ \  \       \ \  \    \ \  \ \  \|\ /\|___|\  \
//  \ \  \   \ \  \    \ \  \\\  \ \  \\ \  \ \   __  \ \   _  _\ \  \ \   __  \ \  \\ \  \       \ \  \    \ \  \ \   __  \   \ \  \
//   \ \  \___\ \  \____\ \  \\\  \ \  \\ \  \ \  \ \  \ \  \\  \\ \  \ \  \ \  \ \  \\ \  \       \ \  \____\ \  \ \  \|\  \  _\_\  \
//    \ \______\ \_______\ \_______\ \__\\ \__\ \__\ \__\ \__\\ _\\ \__\ \__\ \__\ \__\\ \__\       \ \_______\ \__\ \_______\|\______\
//     \|______|\|_______|\|_______|\|__| \|__|\|__|\|__|\|__|\|__|\|__|\|__|\|__|\|__| \|__|        \|_______|\|__|\|_______|\|______|

package fr.aegislunarian.victorhugo.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Classe utilitaire pour l'envoi de Titles et ActionBars aux joueurs.
 * <p>
 * Compatible Spigot 1.21.x sans Adventure API.
 * Permet :
 * <ul>
 *     <li>Envoyer un Title avec ou sans sous-titre</li>
 *     <li>Envoyer un ActionBar simple ou pendant une durée spécifique</li>
 *     <li>Vérifier que le joueur est connecté avant chaque envoi</li>
 * </ul>
 */
public final class TitleUtils
{

    /** Instance du plugin nécessaire pour les tâches programmées */
    private static JavaPlugin plugin;

    private TitleUtils()
    {
        // Empêche l'instanciation
    }

    /**
     * Initialise la classe utilitaire avec l'instance du plugin.
     * <p>
     * DOIT être appelée dans la méthode {@code onEnable()} du plugin.
     *
     * @param plugin Instance du plugin principal
     */
    public static void init(JavaPlugin plugin)
    {
        TitleUtils.plugin = plugin;
    }

    /**
     * Envoie un Title et un sous-titre à un joueur.
     *
     * @param player Joueur ciblé
     * @param title Titre principal
     * @param subtitle Sous-titre affiché sous le titre principal
     * @param fadeIn Temps d'apparition (en ticks)
     * @param stay Durée d'affichage (en ticks)
     * @param fadeOut Temps de disparition (en ticks)
     */
    public static void sendTitleToPlayer(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        if (player == null || !player.isOnline()) return;
        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    /**
     * Envoie un Title simple (sans sous-titre) avec des durées par défaut.
     * <ul>
     *     <li>fadeIn = 10 ticks</li>
     *     <li>stay = 70 ticks</li>
     *     <li>fadeOut = 20 ticks</li>
     * </ul>
     *
     * @param player Joueur ciblé
     * @param title Titre principal
     */
    public static void sendTitleToPlayer(Player player, String title)
    {
        sendTitleToPlayer(player, title, "", 10, 70, 20);
    }

    /**
     * Envoie un message ActionBar simple à un joueur.
     *
     * @param player Joueur ciblé
     * @param message Message à afficher
     */
    public static void sendActionBarToPlayer(Player player, String message)
    {
        if (player == null || !player.isOnline()) return;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    /**
     * Envoie un message ActionBar à un joueur pendant une durée spécifique.
     * <p>
     * La méthode s'assure que le joueur est toujours connecté avant chaque envoi.
     *
     * @param player Joueur ciblé
     * @param message Message à afficher
     * @param durationTicks Durée d'affichage en ticks (1 seconde = 20 ticks)
     */
    public static void sendActionBarToPlayer(Player player, String message, int durationTicks)
    {
        if (player == null || plugin == null)
        {
            Bukkit.getLogger().warning("[TitleUtils] Plugin non initialisé ou joueur null !");
            return;
        }

        new BukkitRunnable()
        {
            int elapsed = 0;

            @Override
            public void run()
            {
                if (!player.isOnline())
                {
                    cancel();
                    return;
                }

                // Affiche le message ActionBar tant que le joueur est connecté et que la durée n'est pas écoulée
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

                elapsed += 20; // rafraîchissement toutes les secondes
                if (elapsed >= durationTicks)
                {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
