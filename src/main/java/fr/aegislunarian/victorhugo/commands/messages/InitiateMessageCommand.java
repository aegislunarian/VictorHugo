//   ___      ___ ___  ________ _________  ________  ________          ___  ___  ___  ___  ________  ________
//  |\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \        |\  \|\  \|\  \|\  \|\   ____\|\   __  \
//  \ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \       \ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \
//   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\       \ \   __  \ \  \\\  \ \  \  __\ \  \\\  \
//    \ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \|       \ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \
//     \ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\        \ \__\ \__\ \_______\ \_______\ \_______\
//      \|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|        \|__|\|__|\|_______|\|_______|\|_______|

package fr.aegislunarian.victorhugo.commands.messages;

import fr.aegislunarian.victorhugo.Main;
import fr.aegislunarian.victorhugo.utils.MessageTemplate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InitiateMessageCommand implements CommandExecutor {

    /**
     * Commande relative à l'envoi de messages privés.
     * Usage : /msg <joueur|console> <message>
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(MessageTemplate.adminErrorMessage("Utilisation : /msg <joueur|console> <message>"));
            return false;
        }

        String targetName = args[0];
        String message = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        if (targetName.equalsIgnoreCase("console")) {
            sender.sendMessage(MessageTemplate.emitterPrivateMessage("Serveur", message));
            Bukkit.getConsoleSender().sendMessage(MessageTemplate.receiverPrivateMessage(sender.getName(), message));
            return true;
        }

        Player target = Bukkit.getPlayerExact(targetName);
        if (target == null) {
            sender.sendMessage(MessageTemplate.adminErrorMessage("Le joueur " + targetName + " n'existe pas ou n'est pas connecté !"));
            return false;
        }

        if (sender instanceof Player player && player.equals(target)) {
            sender.sendMessage(MessageTemplate.adminMessage("Tu ne peux pas t'envoyer de message à toi-même, abruti..."));
            return false;
        }

        target.sendMessage(MessageTemplate.receiverPrivateMessage(sender.getName().equalsIgnoreCase("CONSOLE") ? "Serveur" : sender.getName(), message));
        sender.sendMessage(MessageTemplate.emitterPrivateMessage(target.getName(), message));

        Main.get().getAccountManager()
                .getAccount(target.getUniqueId())
                .setLastMessenger(sender.getName());

        return true;
    }
}
