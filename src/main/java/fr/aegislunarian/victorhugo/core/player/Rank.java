package fr.aegislunarian.victorhugo.core.player;

import org.bukkit.ChatColor;

public enum Rank
{
    DEFAULT("???", ChatColor.DARK_GRAY),
    MP("MP/MPI", ChatColor.YELLOW),
    MPE("MP*/MPI*", ChatColor.GOLD),
    MP2I("MP2I", ChatColor.DARK_GREEN),
    MPSI("MPSI", ChatColor.WHITE),
    PCSI("PCSI", ChatColor.LIGHT_PURPLE),
    BCPST("BCPST", ChatColor.DARK_AQUA);

    final String prefix;
    final ChatColor prefixColor;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ATTENTION : IL NE FAUT SURTOUT PAS DÉPASSER 11 CARACTÈRES DANS LE PRÉFIXE SANS QUOI LE SERVEUR NE MARCHERA PLUS. //
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Mise en place de l'énumération.
     */
    Rank(String prefix, ChatColor prefixColor)
    {
        this.prefix = prefix;
        this.prefixColor = prefixColor;
    }

    /**
     * Cette fonction retourne le préfixe.
     * @return Le préfixe mise en forme.
     */
    public String getPrefix()
    {
        return prefixColor + "[" + prefix + "] " ; // 11 + 5 caractères = 16, limite imposée.
    }

    /**
     * Cette fonction permet de connaitre le nombre de caractères dans le préfixe détintif.
     * @return le nombre de caractères du préfixe définitif.
     */
    public int getPrefixLength()
    {
        return getPrefix().length();
    }
}
