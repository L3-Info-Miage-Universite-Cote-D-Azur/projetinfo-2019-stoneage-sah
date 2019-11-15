package game;

/**
 * Interface commune a toutes les zone
 * @author Yohann
 *
 */
public interface Zone {
	/**
	 * Possible de choissir
	 * @param currentPlayerFigurine
	 * @return
	 */
	public boolean ableToChooseZone(int currentPlayerFigurine);
	
	/**
	 * Nombre de figurine que le joueur possede dans la zone
	 * @param player le joueur en question.
	 * @return le nombre de figurine du joueur player dans la zone.
	 */
	public int howManyPlayerFigurine(Player player);
	
	/**
	 * Place les figurine du joueur dans la zone
	 * @param number nombre de figurine que le joueur place
	 * @param player joueur qui est concerne. 
	 */
	public void placeFigurine(int number,Player player);
	
	/**
	 * nom de la zone
	 * @return nom de la zone
	 */
	public String getName();
	
	/**
	 * Le joueur recupere son du et recupere ses figurine
	 * @param player le joueur qui recupere son du.
	 */
	public void playerRecoveryFigurine(Player player);
	
	/**
	 * Retourn la place disponible dans la zone
	 * @return le nombre de place disponible
	 */
	public int getAvailableSpace();
	
	/**
	 * le nombre minimal a placer dans la zone
	 * @return le nombre minimal que le joueur dois posser dans la zone
	 */
	public int getMinimalFigurineRequierement();
}
