//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

package fr.aegislunarian.victorhugo.manager;

import fr.aegislunarian.victorhugo.core.player.Account;
import fr.aegislunarian.victorhugo.core.player.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire de l'affichage des rangs dans le scoreboard.
 * <p>
 * Cette classe initialise des équipes par rang et applique les préfixes et suffixes
 * pour les joueurs en fonction de leur rang et statut (ex : modérateur).
 * </p>
 */
public class RankDisplayManager
{

    private final AccountManager accountManager;
    private final Scoreboard board;
    private final Map<Rank, Team> rankTeams = new HashMap<>();

    /**
     * Constructeur du RankDisplayManager.
     *
     * @param accountManager Le gestionnaire de comptes pour récupérer les informations des joueurs.
     */
    public RankDisplayManager(AccountManager accountManager)
    {
        this.accountManager = accountManager;
        this.board = Bukkit.getScoreboardManager().getMainScoreboard();

        initRankTeams();

        for (Player player : Bukkit.getOnlinePlayers()) {
            applyTeam(player);
        }
    }

    /**
     * Initialise les équipes du scoreboard par rang.
     * Chaque rang possède un préfixe et un suffixe vide par défaut.
     */
    private void initRankTeams()
    {
        for (Rank rank : Rank.values())
        {
            Team team = board.getTeam(rank.name());
            if (team == null) {
                team = board.registerNewTeam(rank.name());
            }
            team.setPrefix(rank.getPrefix());
            team.setSuffix("");
            rankTeams.put(rank, team);
        }
    }

    /**
     * Applique le préfixe et suffixe d'un joueur dans le scoreboard.
     *
     * @param player Le joueur à mettre à jour.
     */
    public void applyTeam(Player player)
    {
        Account account = accountManager.getAccount(player.getUniqueId());
        if (account == null) return;

        Rank rank = account.getRank();
        Team baseTeam = rankTeams.get(rank);
        if (baseTeam == null) return;

        Team personalTeam = board.getTeam(player.getName());
        if (personalTeam == null)
        {
            personalTeam = board.registerNewTeam(player.getName());
        }

        personalTeam.setColor(rank.getPrefixColor());
        personalTeam.setPrefix(baseTeam.getPrefix());
        personalTeam.setSuffix(account.isModerator() ? "§c*" : "");

        for (Team t : board.getTeams())
        {
            t.removeEntry(player.getName());
        }

        personalTeam.addEntry(player.getName());
        player.setScoreboard(board);
    }

    /**
     * Met à jour le rang ou le statut modérateur d'un joueur.
     *
     * @param player Le joueur à mettre à jour.
     */
    public void updatePlayer(Player player)
    {
        applyTeam(player);
    }
}
