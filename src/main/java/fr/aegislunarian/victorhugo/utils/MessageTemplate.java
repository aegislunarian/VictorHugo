//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.utils;

/**
 * Classe utilitaire pour générer des messages formatés dans le chat.
 * <p>
 * Elle fournit des méthodes pour formater des messages administratifs,
 * des messages d'erreur et des messages privés entre joueurs.
 * </p>
 */
public class MessageTemplate
{

    /**
     * Génère un message destiné aux administrateurs ou avec un style administrateur.
     *
     * @param message Le texte du message à afficher.
     * @return Le message formaté avec les couleurs et le préfixe "Victor Hugo »".
     */
    public static String adminMessage(String message)
    {
        return "§dVictor Hugo §8» §e" + message;
    }

    /**
     * Génère un message d'erreur destiné aux administrateurs.
     *
     * @param message Le texte du message d'erreur.
     * @return Le message formaté avec les couleurs et le préfixe "Victor Hugo »" suivi d'une couleur rouge.
     */
    public static String adminErrorMessage(String message)
    {
        return "§dVictor Hugo §8» §c" + message;
    }

    /**
     * Génère un message privé reçu par un joueur.
     *
     * @param senderName Le nom du joueur qui envoie le message.
     * @param message Le texte du message envoyé.
     * @return Le message formaté indiquant l'expéditeur et le contenu du message.
     */
    public static String receiverPrivateMessage(String senderName, String message)
    {
        return "§e[§6" + senderName + " §f§l-> §6" + "Toi§e] §7§o" + message;
    }

    /**
     * Génère un message privé envoyé par le joueur actuel.
     *
     * @param receiverName Le nom du joueur qui reçoit le message.
     * @param message Le texte du message envoyé.
     * @return Le message formaté indiquant le destinataire et le contenu du message.
     */
    public static String emitterPrivateMessage(String receiverName, String message)
    {
        return "§e[§6Toi §f§l-> §6" + receiverName + "§e] §7§o" + message;
    }
}
