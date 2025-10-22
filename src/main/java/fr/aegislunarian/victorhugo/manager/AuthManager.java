//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.manager;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.core.player.Account;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthManager
{

    private final AccountManager accountManager;
    private final Map<UUID, Boolean> loggedIn = new HashMap<>();

    public AuthManager(AccountManager accountManager)
    {
        this.accountManager = accountManager;
    }

    // ---------------- Password Hashing Utils ----------------

    /**
     * Permet de hashé le mot de passe via l'algorithme SHA-256.
     * @param password Le mot de passe brut.
     * @return Le mot de passe hashé.
     */
    private String hashPassword(String password)
    {
        if (password == null || password.isEmpty()) return "unset";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes)
            {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return "unset";
        }
    }

    /**
     * Permet de vérifier si le mot de passe inséré correspond au mot de passe hashé.
     * @param password Le mot de passe fournit.
     * @param hash Le mot de passe de référence.
     * @return 'true' si les deux mots de passes sont égaux, 'false' sinon.
     */
    private boolean verifyPassword(String password, String hash)
    {
        return hash.equals(hashPassword(password));
    }

    // ---------------- Auth Methods ----------------

    /**
     * Permet d'essayer d'enregistrer le joueur.
     * @param player Le joueur.
     * @param password Le mot de passe entré.
     * @return 'true' si l'enregistrement a abouti, 'false' sinon.
     */
    public boolean register(Player player, String password)
    {
        Account account = accountManager.getAccount(player.getUniqueId());
        if (account == null) {
            account = accountManager.createOrLoadAccount(player.getUniqueId());
        }

        if (!"unset".equals(account.getHashedPassword()))
        {
            return false; // déjà enregistré
        }

        account.setHashedPassword(hashPassword(password));
        loggedIn.put(player.getUniqueId(), true);
        return true;
    }

    /**
     * Permet de faire une tentative de connexion.
     * @param player Le joueur.
     * @param password Le mot de passe entrée.
     * @return 'true' si la connexion a abouti, 'false' sinon.
     */
    public boolean login(Player player, String password)
    {
        Account account = accountManager.getAccount(player.getUniqueId());
        if (account == null) return false;

        String hashed = account.getHashedPassword();
        if (hashed == null || "unset".equals(hashed)) return false;

        if (verifyPassword(password, hashed))
        {
            loggedIn.put(player.getUniqueId(), true);
            return true;
        }

        return false;
    }

    /**
     * Vérifier si le joueur est connecté (dans le sens du système Auth).
     * @param player Le joueur.
     * @return 'true' si le joueur est connecté, 'false' sinon.
     */
    public boolean isLoggedIn(Player player)
    {
        return loggedIn.getOrDefault(player.getUniqueId(), false);
    }

    /**
     * Téléporter le joueur après la connexion.
     * @param player Le joueur.
     */
    public void teleportOnLogin(Player player)
    {
        Account account = accountManager.getAccount(player.getUniqueId());
        if (account == null) return;

        Location location = account.getLastKnownLocation();
        if (location != null) {
            player.teleport(location);
        }
        player.setGameMode(GameMode.SURVIVAL);
    }

    /**
     * Réinitialise le mot de passe d'un compte.
     * @param uniqueId l'UUID du joueur.
     */
    public void resetPassword(UUID uniqueId)
    {
        Account account = accountManager.getAccount(uniqueId);
        if (account != null)
        {
            account.setHashedPassword("unset");
        }
    }

    /**
     * Déconnecter le joueur.
     * @param player Le joueur.
     */
    public void logOut(Player player)
    {
        loggedIn.remove(player.getUniqueId());
    }
}
