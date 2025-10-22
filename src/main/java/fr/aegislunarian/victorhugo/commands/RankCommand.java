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
import fr.aegislunarian.victorhugo.core.player.Rank;
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
import java.util.Arrays;
import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter
{
    AccountManager accountManager = Main.get().getAccountManager();
    /**
     * Commande relative aux rangs.
     * Usage /rank <set|get|list> <set/get : nom du joueur> <set : rang>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if(sender instanceof Player player)
        {
            Account playerAccount = accountManager.getAccount(player.getUniqueId());

            if(args.length == 0)
            {
                player.sendMessage(
                        """
                        §e======== §c[§fRANG§c] §e========
                        §eNom du joueur : §6%s
                        §eUUID du joueur : §6%s
                        §eRang du joueur : %s%s
                        §e======== §c[§fRANG§c] §e========
                        """.formatted(
                                player.getName(),
                                player.getUniqueId(),
                                playerAccount.getRank().getPrefix(),
                                playerAccount.isModerator() ? " §c§l[MODÉRATEUR]" : ""
                        ));
                return true;
            } else if(!playerAccount.isModerator())
            {
                player.sendMessage(MessageTemplate.adminErrorMessage("Vous n'avez pas la permission d'utiliser cette commande."));
                return true;
            }
        }

        if(args.length == 0) return false;

        switch (args[0].toLowerCase())
        {
            case "list":
                String roles = Arrays.stream(Rank.values())
                        .map(rank -> rank.getPrefixColor() + rank.name()) // ajoute la couleur devant le nom
                        .reduce((a, b) -> a + "§e, " + b)             // sépare par une virgule blanche
                        .orElse("Aucun rang disponible");
                sender.sendMessage(MessageTemplate.adminMessage("Les rôles sont : " + roles + "§e.")); // point final blanc
                break;
            case "set":
                if(args.length > 2)
                {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (!target.hasPlayedBefore() && !target.isOnline())
                    {
                        sender.sendMessage(MessageTemplate.adminErrorMessage("Le joueur '" + args[1] + "' n'existe pas."));
                        return false;
                    }

                    if(accountManager.hasAccount(target))
                    {
                        Account targetAccount = accountManager.getAccount(target.getUniqueId());

                        if(Rank.exists(args[2]))
                        {
                            targetAccount.setRank(Rank.valueOf(args[2].toUpperCase()));

                            sender.sendMessage(MessageTemplate.adminMessage("Le joueur §6" + target.getName() + " hérite du rang " + targetAccount.getRank().getPrefix() + "§e!"));
                            if(target.isOnline())
                            {
                                Bukkit.getPlayer(target.getName()).sendMessage(MessageTemplate.adminMessage("§aVotre rang est désormais " + targetAccount.getRank().getPrefix() + "§a!"));
                                Main.get().getRankDisplayManager().updatePlayer((Player) target);
                            }
                            return true;
                        }

                        sender.sendMessage(MessageTemplate.adminErrorMessage("Le rang '" + args[2].toUpperCase() + "' n'existe pas."));
                        return false;
                    }

                    sender.sendMessage(MessageTemplate.adminErrorMessage("Le joueur '" + args[1] + "' n'existe pas."));
                    return false;
                }

                int required = 3 - args.length;
                sender.sendMessage(MessageTemplate.adminErrorMessage("Il manque " + required + " argument" + (required > 1 ? "s" : "") + "."));

                return false;
            case "get":
                if (args.length > 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

                    if (!target.hasPlayedBefore() && !target.isOnline()) {
                        sender.sendMessage(MessageTemplate.adminErrorMessage("Le joueur '" + args[1] + "' n'existe pas."));
                        return false;
                    }

                    if (accountManager.hasAccount(target)) {
                        Account targetAccount = accountManager.getAccount(target.getUniqueId());
                        sender.sendMessage(
                                """
                                §e======== §c[§fRANG§c] §e========
                                §eNom du joueur : §6%s
                                §eUUID du joueur : §6%s
                                §eRang du joueur : %s%s
                                §e======== §c[§fRANG§c] §e========
                                """.formatted(
                                        target.getName(),
                                        target.getUniqueId(),
                                        targetAccount.getRank().getPrefix(),
                                        targetAccount.isModerator() ? " §c§l[MODÉRATEUR]" : ""
                                ));
                        return true;
                    }

                    sender.sendMessage(MessageTemplate.adminErrorMessage("Le joueur '" + args[1] + "' n'existe pas."));
                    return false;
                }

                sender.sendMessage(MessageTemplate.adminErrorMessage("Utilisation : /rank get <joueur>"));
                return false;
            default:
                sender.sendMessage(MessageTemplate.adminErrorMessage("La sous commande '" + args[0].toUpperCase() + "' n'existe pas."));
                break;
        }
        return true;
    }

    /**
     * Pour gérer la complétion automatique.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player player)
        {
            if(!accountManager.getAccount(player.getUniqueId()).isModerator()) return List.of();
        }

        List<String> completions = new ArrayList<>();

        if(args.length == 1)
        {
            List<String> subCommands = Arrays.asList("set", "get", "list");
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }
        else if(args.length == 2 && !args[0].equalsIgnoreCase("list"))
        {
            List<String> playersNames = Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .toList();

            StringUtil.copyPartialMatches(args[1], playersNames, completions);
        }
        else if(args.length == 3 && args[0].equalsIgnoreCase("set"))
        {
            List<String> ranksNames = Arrays.stream(Rank.values()).map(Enum::name).toList();
            StringUtil.copyPartialMatches(args[2], ranksNames, completions);
        }

        return completions;
    }
}
