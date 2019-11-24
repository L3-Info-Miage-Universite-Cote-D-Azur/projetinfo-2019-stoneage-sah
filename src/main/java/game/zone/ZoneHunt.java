package game.zone;

import game.Player;
import game.Ressource;

public class ZoneHunt extends ZoneRessource {

	public ZoneHunt(String name, Ressource ressource, int availableSpace) {
		super(name, ressource, availableSpace);
	}
	
	@Override
	public boolean ableToChooseZone(Player player){
		//Les joueurs peuvent toujours placer sur la chasse on enleve donc la condition
		return true; 
	}

}
