package fr.aegislunarian.victorhugo.manager;

import com.google.gson.JsonSyntaxException;
import fr.aegislunarian.victorhugo.core.player.Account;
import fr.aegislunarian.victorhugo.utils.JsonHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountManager
{
    final Path accountsFolder;
    final JavaPlugin plugin;

    final Map<UUID, Account> accounts = new HashMap<>();

    /**
     * Mise en instance du gestionnaire de comptes.
     * @param plugin La classe main.
     */
    public AccountManager(JavaPlugin plugin)
    {
        this.accountsFolder = plugin.getDataFolder().toPath().resolve("accounts");
        this.plugin = plugin;

        try
        {
            Files.createDirectories(accountsFolder);
        } catch (IOException exception)
        {
            plugin.getLogger().severe("Impossible de créer le dossier accounts !");
            throw new RuntimeException(exception);
        }

        loadAllAccounts();
    }

    /**
     * Crée un compte pour le joueur ou charge le compte existant depuis JSON.
     * @param uniqueID L'UUID du joueur.
     * @return Le compte créé ou chargé.
     */
    public Account createOrLoadAccount(UUID uniqueID)
    {
        if(accounts.containsKey(uniqueID)) return accounts.get(uniqueID);

        Account account = new Account(uniqueID);

        accounts.put(uniqueID, account);

        Path file = accountsFolder.resolve(uniqueID + ".json");
        try
        {
            JsonHandler.saveObject(file, account);
        } catch (IOException exception)
        {
            plugin.getLogger().severe("Impossible de sauvegarder le compte de " + uniqueID + " !");
            exception.printStackTrace();
        }

        return account;
    }

    /**
     * Charge tous les comptes.
     */
    private void loadAllAccounts()
    {
        if(!Files.exists(accountsFolder)) return;

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(accountsFolder, "*.json"))
        {
            for(Path file : stream)
            {
                try
                {
                    Account account = JsonHandler.loadObject(file, Account.class);
                    if(account != null)
                    {
                        accounts.put(account.getUniqueId(), account);
                    }
                } catch (JsonSyntaxException exception)
                {
                    plugin.getLogger().severe("Impossible de charger le compte depuis " + file.getFileName() + ".");
                    exception.printStackTrace();
                }
            }
        } catch (IOException exception)
        {
            plugin.getLogger().severe("Impossible de parcourir le dossier accounts !");
            exception.printStackTrace();
        }
    }

    /**
     * Sauvegarde tous les comptes joueurs.
     */
    public void saveAllAccounts()
    {
        for(Account account : accounts.values())
        {
            Path file = accountsFolder.resolve(account.getUniqueId() + ".json");
            try
            {
                JsonHandler.saveObject(file, account);
            } catch (IOException exception)
            {
                plugin.getLogger().severe("Impossible de sauvegarder le compte de " + account.getUniqueId());
                exception.printStackTrace();
            }
        }
    }

    /**
     * Permet de récupérer le compte du joueur.
     * @param uniqueID L"UUID du joueur.
     * @return Le compte du joueur.
     */
    public Account getAccount(UUID uniqueID)
    {
        return accounts.get(uniqueID);
    }

    public boolean hasAccount(OfflinePlayer player)
    {
        return accounts.containsKey(player.getUniqueId());
    }
}
