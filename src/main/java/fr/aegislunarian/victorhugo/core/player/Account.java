package fr.aegislunarian.victorhugo.core.player;

import fr.aegislunarian.victorhugo.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.UUID;

public class Account
{
    transient FileConfiguration config = Main.get().getConfig();

    final UUID uniqueID;
    Rank rank;

    private double lastX;
    private double lastY;
    private double lastZ;

    private transient Location cachedLocation;

    /**
     * Classe 'Account' : permet de gérer le compte des joueurs.
     * @param uniqueID L"UUID du joueur en question.
     */
    public Account(UUID uniqueID)
    {
        this.uniqueID = uniqueID;

        rank = Rank.DEFAULT;

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
        return uniqueID;
    }

    /**
     * Récupère la dernière position connue du joueur.
     * @return La dernière position connue du joueur.
     */
    public Location getLastKnownLocation() {
        if (cachedLocation == null) {
            cachedLocation = new Location(
                    Bukkit.getWorld("world"),
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

        this.lastX = location.getX();
        this.lastY = location.getY();
        this.lastZ = location.getZ();

        this.cachedLocation = location;
    }
}
