package fr.aegislunarian.victorhugo;

import fr.aegislunarian.victorhugo.listeners.ChatEvents;
import fr.aegislunarian.victorhugo.manager.AccountManager;
import fr.aegislunarian.victorhugo.manager.ListenerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
    private static Main INSTANCE;
    AccountManager accountManager;
    ListenerManager listenerManager;

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

        this.saveDefaultConfig();
        this.reloadConfig();

        accountManager = new AccountManager(INSTANCE);
        listenerManager = new ListenerManager(INSTANCE);

        listenerManager.registerListener(ChatEvents.class);
    }

    /**
     * Logique de désactivation du plugin.
     */
    void disable()
    {
        accountManager.saveAllAccounts();
    }

    /**
     * Récupère l’instance du plugin.
     * @return L’instance Main
     */
    public static Main get()
    {
        return INSTANCE;
    }

    /**
     * Accéder à la classe AccountManager.
     * @return La classe AccountManager.
     */
    public AccountManager getAccountManager()
    {
        return accountManager;
    }

    /**
     * Accéder à la classe ListenerManager.
     * @return La classe Listenermanager.
     */
    public ListenerManager getListenerManager()
    {
        return listenerManager;
    }
}
