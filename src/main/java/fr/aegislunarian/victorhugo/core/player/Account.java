//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.core.player;

import fr.aegislunarian.victorhugo.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.UUID;

public class Account
{
    transient FileConfiguration configuration = Main.get().getConfig();

    final UUID uniqueId;
    private String hashedPassword;

    Rank rank;
    boolean moderator;

    private String worldName;
    private double lastX;
    private double lastY;
    private double lastZ;

    private transient Location cachedLocation;

    private transient String lastMessenger;

    /**
     * Classe 'Account' : permet de gérer le compte des joueurs.
     * @param uniqueId L"UUID du joueur en question.
     */
    public Account(UUID uniqueId)
    {
        this.uniqueId = uniqueId;
        hashedPassword = "unset";

        rank = Rank.DEFAULT;
        moderator = false;

        worldName = configuration.getString("playWorld");
        lastX = Bukkit.getWorld(worldName).getSpawnLocation().getX();
        lastY = Bukkit.getWorld(worldName).getSpawnLocation().getY();
        lastZ = Bukkit.getWorld(worldName).getSpawnLocation().getZ();

        setLastKnownLocation(new Location(
                Bukkit.getWorld(worldName),
                lastX, lastY, lastZ
        ));

        lastMessenger = null;
    }

    /**
     * Cette fonction permet de récupérer le rang du joueur.
     * @return Le rang du joueur.
     */
    public Rank getRank()
    {
        return rank;
    }

    /**
     * Définit le rang du joueur.
     * @param rank Le rang du joueur.
     */
    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    /**
     * Récupérer l'UUID du joueur.
     * @return L'UUID du joueur.
     */
    public UUID getUniqueId()
    {
        return uniqueId;
    }

    /**
     * Récupère la dernière position connue du joueur.
     * @return La dernière position connue du joueur.
     */
    public Location getLastKnownLocation()
    {
        if (cachedLocation == null)
        {
            cachedLocation = new Location(
                    Bukkit.getWorld(worldName),
                    lastX, lastY, lastZ
            );
        }
        return cachedLocation;
    }

    /**
     * Définit la dernière position connue du joueur.
     * @param location La dernière position connue du joueur.
     */
    public void setLastKnownLocation(Location location)
    {
        if (location == null) return;

        this.worldName = location.getWorld().getName();
        this.lastX = location.getX();
        this.lastY = location.getY();
        this.lastZ = location.getZ();

        this.cachedLocation = location;
    }

    /**
     * Permet de savoir si le joueur est un modérateur.
     * @return 'true' si le joueur est un modérateur, 'false' sinon.
     */
    public boolean isModerator()
    {
        return moderator;
    }

    /**
     * Définit si le joueur est un modérateur.
     * @param moderator 'true' si le joueur doit être modérateur,'false' sinon.
     */
    public void setModerator(boolean moderator)
    {
        this.moderator = moderator;
    }

    /**
     * Permet de récupérer le pseudo du dernier joueur qui a envoyé un message au joueur.
     * @return Le pseudo du dernier joueur qui a enovyé un message au joueur.
     */
    public String getLastMessenger()
    {
        return lastMessenger;
    }

    /**
     * Définit le pseudo du dernier joueur qui a envoyé un message au joueur.
     * @param lastMessenger Le pseudo du dernier joueur.
     */
    public void setLastMessenger(String lastMessenger)
    {
        this.lastMessenger = lastMessenger;
    }

    /**
     * Permet de récupérer le mot de passe hashé du joueur.
     * @return Le mot de passe hashé du joueur.
     */
    public String getHashedPassword()
    {
        return hashedPassword;
    }

    /**
     * Définit le mot de passe hashé du joueur.
     * @param hashedPassword Le mot de passe hashé du joueur.
     */
    public void setHashedPassword(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }
}
