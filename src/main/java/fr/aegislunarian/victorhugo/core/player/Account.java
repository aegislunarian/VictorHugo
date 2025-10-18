package fr.aegislunarian.victorhugo.core.player;

import org.bukkit.entity.Player;

public class Account
{
    final Player player;
    boolean online;

    Rank rank;

    /**
     * Classe 'Account' : permet de gérer le joueurs.
     * @param player Le joueur en question.
     */
    public Account(Player player)
    {
        this.player = player;

        rank = Rank.DEFAULT;
    }

    /**
     * Cette fonction permet de récupérer le rang du joueur.
     * @return Le rang du joueur.
     */
    public Rank getRank()
    {
        return rank;
    }
}
