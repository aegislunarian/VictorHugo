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
}
