//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Path;

public class JsonHandler
{
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Sauvegarde un objet en JSON dans un fichier.
     * @param file Le fichier visé.
     * @param object L'objet à enregistré.
     * @throws IOException Si le fichier est introuvable ou si l'écriture échoue.
     */
    public static void saveObject(Path file, Object object) throws IOException
    {
        try(Writer writer = new FileWriter(file.toFile()))
        {
            gson.toJson(object, writer);
        } catch (IOException exception)
        {
            throw new IOException(exception);
        }
    }

    /**
     * Charge un objet depuis un fichier JSON.
     * @param file Le fichier contenant le JSON.
     * @param clazz La classe de l'objet à instancier.
     * @param <T> Le type de l'objet à retourner.
     * @return L'objet chargé depuis le JSON.
     * @throws IOException Si le fichier est introuvable ou si la lecture échoue.
     */
    public static <T> T loadObject(Path file, Class<T> clazz) throws IOException
    {
        try(Reader reader = new FileReader(file.toFile()))
        {
            return gson.fromJson(reader, clazz);
        } catch (IOException exception)
        {
            throw new IOException(exception);
        }
    }

}
