//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.commands;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.core.player.Account;
import fr.aegislunarian.victorhugo.manager.AccountManager;
import fr.aegislunarian.victorhugo.utils.MessageTemplate;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ModCommand implements CommandExecutor, TabCompleter
{
    AccountManager accountManager = Main.get().getAccountManager();
    /**
     * Commande relative aux modérateurs.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player player && !accountManager.getAccount(player.getUniqueId()).isModerator())
        {
            player.sendMessage(MessageTemplate.adminErrorMessage("Vous n'avez pas la permission d'utiliser cette commande."));
            return true;
        }

        if (args.length < 1)
        {
            sender.sendMessage(MessageTemplate.adminErrorMessage("Utilisation : /mod <joueur>."));
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!accountManager.hasAccount(target))
        {
            sender.sendMessage(MessageTemplate.adminErrorMessage("Le joueur '" + args[0] + "' n'existe pas."));
            return false;
        }

        Account targetAccount = accountManager.getAccount(target.getUniqueId());
        boolean isNowModerator = !targetAccount.isModerator();
        targetAccount.setModerator(isNowModerator);

        String statusMessage = isNowModerator ? "§apromu" : "§cplus";
        sender.sendMessage(MessageTemplate.adminMessage(
                "Le joueur §6" + target.getName() + " §e" + (isNowModerator ? "" : "n'") + "est " + statusMessage + " §emodérateur."
        ));

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player player)
        {
            if(!accountManager.getAccount(player.getUniqueId()).isModerator()) return List.of();
        }

        return StringUtil.copyPartialMatches(args[0],
                Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .toList(),
                new ArrayList<>()
        );
    }
}
