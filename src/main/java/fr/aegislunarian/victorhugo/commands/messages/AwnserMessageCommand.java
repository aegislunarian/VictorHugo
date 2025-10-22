//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.commands.messages;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.utils.MessageTemplate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AwnserMessageCommand implements CommandExecutor
{

    /**
     * Commande permettant de répondre au dernier message privé reçu.
     * Usage : /r <message>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (!(sender instanceof Player player))
        {
            sender.sendMessage(MessageTemplate.adminErrorMessage("Seul un joueur peut utiliser cette commande."));
            return false;
        }

        if (args.length < 1)
        {
            sender.sendMessage(MessageTemplate.adminErrorMessage("Utilisation : /r <message>."));
            return false;
        }

        var account = Main.get().getAccountManager().getAccount(player.getUniqueId());
        String lastMessengerName = account.getLastMessenger();

        if (lastMessengerName == null)
        {
            player.sendMessage(MessageTemplate.adminErrorMessage("Tu n'as personne à qui répondre."));
            return false;
        }

        String message = String.join(" ", args);

        if (lastMessengerName.equalsIgnoreCase("console"))
        {
            player.sendMessage(MessageTemplate.emitterPrivateMessage("Serveur", message));
            Bukkit.getConsoleSender().sendMessage(MessageTemplate.receiverPrivateMessage(player.getName(), message));
            return true;
        }

        Player target = Bukkit.getPlayerExact(lastMessengerName);
        if (target == null)
        {
            player.sendMessage(MessageTemplate.adminErrorMessage("Le joueur " + lastMessengerName + " n'est plus connecté."));
            return false;
        }

        target.sendMessage(MessageTemplate.receiverPrivateMessage(player.getName(), message));
        player.sendMessage(MessageTemplate.emitterPrivateMessage(target.getName(), message));

        Main.get().getAccountManager()
                .getAccount(target.getUniqueId())
                .setLastMessenger(player.getName());

        return true;
    }
}
