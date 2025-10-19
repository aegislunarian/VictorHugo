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

    public boolean isModerator()
    {
        return moderator;
    }

    public void setModerator(boolean moderator)
    {
        this.moderator = moderator;
    }
}
