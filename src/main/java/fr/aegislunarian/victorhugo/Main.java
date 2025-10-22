//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo;

import fr.aegislunarian.victorhugo.commands.AuthCommand;
import fr.aegislunarian.victorhugo.commands.ModCommand;
import fr.aegislunarian.victorhugo.commands.RankCommand;
import fr.aegislunarian.victorhugo.commands.messages.AwnserMessageCommand;
import fr.aegislunarian.victorhugo.commands.messages.InitiateMessageCommand;
import fr.aegislunarian.victorhugo.listeners.ChatEvents;
import fr.aegislunarian.victorhugo.listeners.NetworkEvents;
import fr.aegislunarian.victorhugo.manager.AccountManager;
import fr.aegislunarian.victorhugo.manager.AuthManager;
import fr.aegislunarian.victorhugo.manager.ListenerManager;
import fr.aegislunarian.victorhugo.manager.RankDisplayManager;
import fr.aegislunarian.victorhugo.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
    private static Main INSTANCE;
    AccountManager accountManager;
    ListenerManager listenerManager;
    RankDisplayManager rankDisplayManager;
    AuthManager authManager;

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

        TitleUtils.init(INSTANCE);

        accountManager = new AccountManager(INSTANCE);
        listenerManager = new ListenerManager(INSTANCE);

        Bukkit.getScheduler().runTask(this, () -> {
            RankDisplayManager rankDisplayManager = new RankDisplayManager(accountManager);
            this.rankDisplayManager = rankDisplayManager;

            String logginWorldName = getConfig().getString("spawn.world");

            World logginWorld = Bukkit.getWorld(logginWorldName);
            if (logginWorld == null) {
                logginWorld = new WorldCreator(logginWorldName).createWorld();
            }
        });

        authManager = new AuthManager(accountManager);

        listenerManager.registerListener(ChatEvents.class);

        getCommand("rank").setExecutor(new RankCommand());
        getCommand("msg").setExecutor(new InitiateMessageCommand());
        getCommand("r").setExecutor(new AwnserMessageCommand());
        getCommand("mod").setExecutor(new ModCommand());
        getCommand("login").setExecutor(new AuthCommand());
        getCommand("register").setExecutor(new AuthCommand());
    }

    /**
     * Logique de désactivation du plugin.
     */
    void disable()
    {
        Bukkit.getOnlinePlayers().forEach(NetworkEvents::saveLocation);
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
     * @return La classe ListenerManager.
     */
    public ListenerManager getListenerManager()
    {
        return listenerManager;
    }

    /**
     * Accéder à la classe RankDisplayManager
     * @return La classe RankDisplayManager.
     */
    public RankDisplayManager getRankDisplayManager()
    {
        return rankDisplayManager;
    }

    /**
     * Accéder à la classe AuthManager.
     * @return La classe AuthManager.
     */
    public AuthManager getAuthManager()
    {
        return authManager;
    }
}
