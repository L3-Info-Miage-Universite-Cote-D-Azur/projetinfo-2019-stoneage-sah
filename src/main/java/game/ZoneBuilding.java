package game;

import java.util.ArrayList;

/**
 * Cette abstract class defini une zone pour un paquet de Settings.NB_BUILDING_CARD_IN_DECK(7) batiments
 */

public class ZoneBuilding implements Zone 
{
	private ArrayList<Building> buildings;
	private int availableSpace;
	private Player occupated;	
	
	public ZoneBuilding ()
	{
		this.buildings = new ArrayList<Building>(Settings.NB_BUILDING_CARD_IN_DECK); // TO-DO: getRandomDeck();
		this.availableSpace = 1;
		this.occupated = null;
	}
	
	@Override
	public boolean ableToChooseZone (int currentPlayerFigurine) 
	{
		return this.availableSpace != 0;
	}

	@Override
	public int howManyPlayerFigurine (Player player)
	{
		return (this.availableSpace != 0) ? 0: 1;
	}

	@Override
	public void placeFigurine(int number, Player player)
	{
		this.availableSpace -= number;
		this.occupated = player;
	}

	@Override
	public String getName() {return new String("Tuile Bâtiment");}

	@Override
	public void playerRecoveryFigurine(Player player)
	{
		boolean wantToPickBuilding = true;
		this.availableSpace = 1;
		
		wantToPickBuilding = chooseTakeBuilding(buildings.get(0).getNeededRessource());
		// SI LE JOUEUR VEUT LA TUILE BATIMENT
		if (wantToPickBuilding == true)
		{
			// ON DONNE LE SCORE ASSOCIE AU JOUEUR
			if (this.buildings.get(0).getType() == 0)
				this.occupated.addScore(this.buildings.get(0).getVP());
			else
			{
				this.occupated.addScore(((BuildingSpecial)this.buildings.get(0)).computeVP());
			}
			// LE JOUEUR OBTIENT UNE CARTE BATIMENT EN PLUS
			this.occupated.getInventory().incrementBuilding();
			
			// ON SUPPRIME LES RESSOURCES DU JOUEUR UNE PAR UNE
			for (int i = 0; i < this.buildings.get(0).getNeededRessource().length; i++)
			{
				this.occupated.getInventory().subRessource(this.buildings.get(0).getNeededRessource()[i], 1);
			}
			
			// ON SUPPRIME LA TUILE BATIMENT DE LA PILE
			this.buildings.remove(0);
		}
		this.occupated = null;
	}
	
	public boolean chooseTakeBuilding (Ressource[] neededRessource)
	{
		int type = this.buildings.get(0).getType();
		if (type == 0)
		{
			// CHECKER SI LE JOUEUR PEUT CHOISIR
			if (((BuildingRessourceImposed)this.buildings.get(0)).checkNeededRessource(this.occupated.getInventory().getCopyRessources()) == true)
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

	@Override
	public int getAvailableSpace () {return this.availableSpace;}

	@Override
	public int getMinimalFigurineRequierement () {return 1;}

}
