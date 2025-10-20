//   ___      ___ ___  ________ _________  ________  ________          ___  ___  ___  ___  ________  ________
//  |\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \        |\  \|\  \|\  \|\  \|\   ____\|\   __  \
//  \ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \       \ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \
//   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\       \ \   __  \ \  \\\  \ \  \  __\ \  \\\  \
//    \ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \|       \ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \
//     \ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\        \ \__\ \__\ \_______\ \_______\ \_______\
//      \|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|        \|__|\|__|\|_______|\|_______|\|_______|

package fr.aegislunarian.victorhugo.core.player;

import fr.aegislunarian.victorhugo.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.UUID;

public class Account
{
    transient FileConfiguration config = Main.get().getConfig();

    final UUID uniqueId;

    Rank rank;
    boolean moderator;

    private String worldName;
    private double lastX;
    private double lastY;
    private double lastZ;

    private transient Location cachedLocation;

    private transient boolean moderatorStatus;
    private transient String lastMessenger;

    /**
     * Classe 'Account' : permet de gérer le compte des joueurs.
     * @param uniqueId L"UUID du joueur en question.
     */
    public Account(UUID uniqueId)
    {
        this.uniqueId = uniqueId;

        rank = Rank.DEFAULT;
        moderator = false;

        worldName = "world";
        lastX = config.getDouble("spawn.x");
        lastY = config.getDouble("spawn.y");
        lastZ = config.getDouble("spawn.z");

        setLastKnownLocation(new Location(
                Bukkit.getWorld("world"),
                lastX, lastY, lastZ
        ));

        moderatorStatus = false;
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
    public Location getLastKnownLocation() {
        if (cachedLocation == null) {
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
    public void setLastKnownLocation(Location location) {
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
     * Définit si le joueur doit être en mode modération.
     * @param moderatorStatus 'true' si le joueur doit être en mode modération,'false' sinon.
     */
    public void setModeratorStatus(boolean moderatorStatus)
    {
        this.moderatorStatus = moderatorStatus;
    }

    /**
     * Permet de savoir si le joueur est en mode modération.
     * @return 'true' si le joueur est en mode modération, 'false' sinon.
     */
    public boolean isModeratorStatus()
    {
        return moderatorStatus;
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
     * @param lastMessenger le pseudo du dernier joueur.
     */
    public void setLastMessenger(String lastMessenger)
    {
        this.lastMessenger = lastMessenger;
    }
}
