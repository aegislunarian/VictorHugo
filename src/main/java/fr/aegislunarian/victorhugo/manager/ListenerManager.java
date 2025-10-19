package fr.aegislunarian.victorhugo.manager;

import fr.aegislunarian.victorhugo.listeners.NetworkEvents;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager
{
    final JavaPlugin plugin;
    final PluginManager pluginManager;

    public ListenerManager(JavaPlugin plugin)
    {
        this.plugin = plugin;

        pluginManager = plugin.getServer().getPluginManager();

        registerEssentialsListeners();
    }

    /**
     * Enregistrer toutes les classes essentielles au fonctionnement du plugin.
     */
    public void registerEssentialsListeners()
    {
        registerListener(NetworkEvents.class);
    }

    /**
     * Enregistrer un listener rapidement en spécifiant une classe.
     * @param listenerClass La classe du listener. À noter : une instance va être générée, inutile de l'initialiser.
     */
    public void registerListener(Class<? extends Listener> listenerClass) {
        try {
            Listener listener = listenerClass.getDeclaredConstructor().newInstance();
            pluginManager.registerEvents(listener, plugin);
        } catch (Exception e) {
            plugin.getLogger().severe("Impossible de charger le listener : " + listenerClass.getName());
            e.printStackTrace();
        }
    }
}
