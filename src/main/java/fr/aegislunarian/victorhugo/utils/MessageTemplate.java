package fr.aegislunarian.victorhugo.utils;

public class MessageTemplate
{

    public static String adminMessage(String message)
    {
        return "§dVictor Hugo §8» §e" + message;
    }

    public static String adminErrorMessage(String message)
    {
        return "§dVictor Hugo §8» §c" + message;
    }

    public static String receiverPrivateMessage(String senderName, String message)
    {
        return "§e[§6" + senderName + " §f§l-> §6" + "Toi§e] §7§o" + message;
    }

    public static String emitterPrivateMessage(String receiverName, String message)
    {
        return "§e[§6Toi §f§l-> §6" + receiverName + "§e] §7§o" + message;
    }
}
