package game.zone;

import java.util.Hashtable;

import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * Cette classe abstract represente les zones ou plusieurs joueurs peuvent poser leurs figurines.
 * @author Groupe SAH
 */


public abstract class  ZoneManyPlayer extends Zone{

	//Le dictionnaire qui memorise pour un joueur, le nombre de figurines placees dans la zone. 
	private Hashtable<Player,Integer> figurineInZone;
	private final int numberPlayer;
	private int differentPlayerInZone;

	/* CONSTRUCTOR */
	protected ZoneManyPlayer(String name, int availableSpace,int numberPlayer){
		super(name, availableSpace, 1);
		figurineInZone = new Hashtable<Player,Integer>();
		this.numberPlayer = numberPlayer;
	}

	/**
	 * placeFigurine(int, Player) place number figurine appartenant a player dans la zone. 
	 * @param number : le nombre de figurine a mettre dans la zone.
	 * @param player : le joueur qui les mets. 
	 */
	public void placeFigurine(int number,Player player){
		differentPlayerInZone += 1;
		Printer.getPrinter().println(super.stringPlaceFigurine(number,player));
		super.availableSpace -= number;
		player.placeFigurine(number);
		if(figurineInZone.get(player) == null) figurineInZone.put(player,number);
		else figurineInZone.put(player,number + figurineInZone.get(player)); 
	}


	/**
	 * Retourne le nombre de figurines que player a dans la zone, 0 si il n'occupe pas la zone.
	 * @param player: le joueur dont on veut savoir le nombre de figurines dans la zone. 
	 * @return int : le nombre de figurine que player a dans la zone. 
	 */
	public int howManyPlayerFigurine(Player player){
		if(figurineInZone.get(player)==null){
			return 0;
		}
		return figurineInZone.get(player);
	} 

	/**
	 * Enleve les figurines du joueur player dans la zone. 
	 * @param player : le joueur a qui on retire les figurines de la zone. 
	 */ 
	public void removeFigurine(Player player){
		int number = howManyPlayerFigurine(player);
		if(number > 0)
		{
			differentPlayerInZone -= 1;
			figurineInZone.remove(player);
			super.availableSpace += number;
			player.recoveryFigurine(number);
		}
	}
	
	@Override
	public boolean ableToChooseZone(Player player) {
		boolean numberPlayerRule = true;
		if(numberPlayer == 3 && differentPlayerInZone == 2) numberPlayerRule=false;
		if(numberPlayer == 2 && differentPlayerInZone == 1) numberPlayerRule=false;
		return numberPlayerRule && super.ableToChooseZone(player);
		
	}
	/* ABSTRACT METHOD */
	public abstract int playerRecoveryFigurine(Player player, Inventory inventory);
}