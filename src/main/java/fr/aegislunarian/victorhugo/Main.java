package fr.aegislunarian.victorhugo;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
    static Main INSTANCE;

    @Override
    public void onEnable() { enable(); }


    @Override
    public void onDisable() { disable(); }

    /**
     * Logique d'activation du plugin.
     */
    void enable()
    {
        INSTANCE = this;
    }

    /**
     * Logique de désactivation du plugin.
     */
    void disable()
    {

    }

    /**
     * Récupérer Cette fonction permet de récuperer l'instance de la classe.
     * @return Main.
     */
    public static Main get()
    {
        return INSTANCE;
    }
}
