package fr.aegislunarian.victorhugo.core.player;

import java.util.UUID;

public class Account
{
    final UUID uniqueID;
    Rank rank;

    /**
     * Classe 'Account' : permet de gérer le compte des joueurs.
     * @param uniqueID L"UUID du joueur en question.
     */
    public Account(UUID uniqueID)
    {
        this.uniqueID = uniqueID;

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

    public void setRank(Rank rank)
    {
        this.rank = rank;
    }

    /**
     * Récupérer l'UUID du joueur.
     * @return L'UUID du joueur.
     */
    public UUID getUniqueId()
    {
        return uniqueID;
    }
}
