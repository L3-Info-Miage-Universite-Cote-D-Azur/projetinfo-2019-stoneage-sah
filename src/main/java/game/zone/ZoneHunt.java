package game.zone;

import game.Dice;
import game.Ressource;
import player.Player;

public class ZoneHunt extends ZoneRessource {

	public ZoneHunt(String name, Ressource ressource, int availableSpace, Dice dice) {
		super(name, ressource, availableSpace, dice);
	}

	@Override
	public boolean ableToChooseZone(Player player){
		//Les joueurs peuvent toujours placer sur la chasse on enleve donc la condition
		return true; 
	}

}
