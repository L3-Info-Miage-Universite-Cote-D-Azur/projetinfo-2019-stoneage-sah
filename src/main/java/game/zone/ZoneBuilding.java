package game.zone;
import java.util.ArrayList;

import game.building.*;
import game.Inventory;
import game.Player;
import game.Ressource;
import game.Settings;

/**
 * Cette class defini une zone pour un paquet de Settings.NB_BUILDING_CARD_IN_DECK(7) batiments
 */
public class ZoneBuilding extends ZoneOnePlayer{

	private ArrayList<Building> buildings;
	/* CONSTRUCTOR */
	public ZoneBuilding (){
		super("Tuile Batiment",1);
		this.buildings = Settings.getRandomDeck();
	}

	/**
	 * La fonction regarde si le joueur peut choisir, et s'il peut, demande au joueur de choisir s'il veut prendre la carte ou non
	 * @param neededRessource une copie des ressources du joueur
	 * @param inventory inventaire du joueur concerner
	 * @return true si le joueur veut retirer la carte, false sinon
	 */
	public boolean chooseTakeBuilding (Ressource[] neededRessource,Inventory inventory)
	{
		int type = this.buildings.get(0).getType();
		if (type == 0)
		{
			// CHECKER SI LE JOUEUR PEUT CHOISIR
			if (((BuildingRessourceImposed)this.buildings.get(0)).checkNeededRessource(inventory.getCopyRessources()) == true)
			{
				// SI IL VEUT RETIRER SES POINTS DE VICTOIRE
				return this.occupated.getIA().pickBuilding();
			}
		}
		else if (type == 1)
		{
			// CHECKER SI LE JOUEUR PEUT CHOISIR
			if (((BuildingRessourceNotImposed)this.buildings.get(0)).checkNeededRessource() == true)
			{
				// SI IL VEUT RETIRER SES POINTS DE VICTOIRE
				return this.occupated.getIA().pickBuilding();
			}
		}
		else if (type == 2)
		{
			// CHECKER SI LE JOUEUR PEUT CHOISIR
			if (((BuildingRessourceChoosed)this.buildings.get(0)).checkNeededRessource() == true)
			{
				// SI IL VEUT RETIRER SES POINTS DE VICTOIRE
				return this.occupated.getIA().pickBuilding();
			}

		}

		// LE JOUEUR EST CONTRAINT DE REFUSER 
		return false;
	}

	/**
	 * Gere le systeme pour retirer la figurine de la zone
	 * @param player : le joueur concerne.
	 * @param inventory inventaire du joueur concerner
	 */
	public void playerRecoveryFigurine(Player player,Inventory inventory){
		int number = super.howManyPlayerFigurine(player);

		if (number > 0){
			boolean wantToPickBuilding = true;
			wantToPickBuilding = chooseTakeBuilding(buildings.get(0).getNeededRessource(),inventory);
			// SI LE JOUEUR VEUT LA TUILE BATIMENT
			if (wantToPickBuilding == true)
			{
				// ON DONNE LE SCORE ASSOCIE AU JOUEUR
				if (this.buildings.get(0).getType() == 0)
					super.occupated.addScore(this.buildings.get(0).getVP());
				else
				{
					super.occupated.addScore(((BuildingSpecial)this.buildings.get(0)).computeVP());
				}
				// LE JOUEUR OBTIENT UNE CARTE BATIMENT EN PLUS
				inventory.incrementBuilding();

				// ON SUPPRIME LES RESSOURCES DU JOUEUR UNE PAR UNE
				for (int i = 0; i < this.buildings.get(0).getNeededRessource().length; i++)
				{
					inventory.subRessource(this.buildings.get(0).getNeededRessource()[i], 1);
				}

				// ON SUPPRIME LA TUILE BATIMENT DE LA PILE
				this.buildings.remove(0);
				// ON REND AU JOUEUR SA FIGURINE
			}
			super.removeFigurine(player);
		}

	}


	/**
	 * Permet de savoir si le deck de la zone est vide ou non
	 * @return true s'il est vide, false sinon
	 */
	public boolean isDeckEmpty () {return this.buildings.isEmpty();}
}