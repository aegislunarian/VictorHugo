//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.commands;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.manager.AuthManager;
import fr.aegislunarian.victorhugo.utils.MessageTemplate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class AuthCommand implements CommandExecutor, TabCompleter
{

    private final AuthManager authManager = Main.get().getAuthManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player player)) return true;

        switch (label.toLowerCase())
        {
            case "register" -> {
                if (args.length != 2)
                {
                    player.sendMessage(MessageTemplate.adminErrorMessage("Usage : /register <motDePasse> <verifierMotDePasse>."));
                    return true;
                }

                String password = args[0];
                String verifyPassword = args[1];

                if(password.length() <= 6)
                {
                    sender.sendMessage(MessageTemplate.adminErrorMessage("Le mot de passe doit contenir au moins 6 caractères."));
                    return true;
                }

                if (!password.equals(verifyPassword))
                {
                    player.sendMessage(MessageTemplate.adminErrorMessage("Les mots de passe ne correspondent pas."));
                    return true;
                }

                if (authManager.register(player, password))
                {
                    player.sendMessage(MessageTemplate.adminMessage("Inscription réussie ! Vous êtes connecté."));
                    authManager.teleportOnLogin(player);
                } else
                {
                    player.sendMessage(MessageTemplate.adminErrorMessage("Vous êtes déjà enregistré. Utilisez /login."));
                }
            }

            case "login" -> {
                if (args.length != 1)
                {
                    player.sendMessage(MessageTemplate.adminErrorMessage("Usage : /login <motDePasse>."));
                    return true;
                }

                if (authManager.isLoggedIn(player))
                {
                    player.sendMessage(MessageTemplate.adminErrorMessage("Vous êtes déjà connecté !"));
                    return true;
                }

                String password = args[0];
                if (authManager.login(player, password))
                {
                    player.sendMessage(MessageTemplate.adminMessage("Connexion réussie !"));
                    authManager.teleportOnLogin(player);
                } else
                {
                    player.sendMessage(MessageTemplate.adminErrorMessage("Mot de passe incorrect ou compte non enregistré."));
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        return List.of();
    }
}
