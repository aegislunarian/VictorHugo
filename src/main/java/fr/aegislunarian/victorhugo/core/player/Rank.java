//  ______   ___      ___ ___  ________ _________  ________  ________                 ___  ___  ___  ___  ________  ________  ______
// |\   ___\|\  \    /  /|\  \|\   ____\\___   ___\\   __  \|\   __  \               |\  \|\  \|\  \|\  \|\   ____\|\   __  \|\___   \
// \ \  \__|\ \  \  /  / | \  \ \  \___\|___ \  \_\ \  \|\  \ \  \|\  \  ____________\ \  \\\  \ \  \\\  \ \  \___|\ \  \|\  \|___|\  \
//  \ \  \   \ \  \/  / / \ \  \ \  \       \ \  \ \ \  \\\  \ \   _  _\|\____________\ \   __  \ \  \\\  \ \  \  __\ \  \\\  \   \ \  \
//   \ \  \___\ \    / /   \ \  \ \  \____   \ \  \ \ \  \\\  \ \  \\  \\|____________|\ \  \ \  \ \  \\\  \ \  \|\  \ \  \\\  \  _\_\  \
//    \ \______\ \__/ /     \ \__\ \_______\  \ \__\ \ \_______\ \__\\ _\               \ \__\ \__\ \_______\ \_______\ \_______\|\______\
//     \|______|\|__|/       \|__|\|_______|   \|__|  \|_______|\|__|\|__|               \|__|\|__|\|_______|\|_______|\|_______|\|______|

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
    BCPST("BCPST", ChatColor.DARK_AQUA),
    LYCEEN("ENFANT", ChatColor.RED);

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
     * Cette fonction permet de connaitre le nombre de caractères dans le préfixe définitif.
     * @return le nombre de caractères du préfixe définitif.
     */
    public int getPrefixLength()
    {
        return getPrefix().length();
    }

    /**
     * Récupère la couleur du rôle.
     * @return La couleur du rôle.
     */
    public ChatColor getPrefixColor()
    {
        return prefixColor;
    }

    /**
     * Permet de vérifier si un rang existe.
     * @param name Le rang.
     * @return 'true' si le rang existe, 'false' sinon.
     */
    public static boolean exists(String name) {
        for (Rank rank : values()) {
            if (rank.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
