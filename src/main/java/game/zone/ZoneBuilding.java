package game.zone;
import java.util.ArrayList;

import game.Ressource;
import game.Settings;
import game.building.Building;
import game.building.BuildingRessourceChoosed;
import game.building.BuildingRessourceImposed;
import game.building.BuildingRessourceNotImposed;
import game.building.BuildingSpecial;
import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * Cette class defini une zone pour un paquet de Settings.NB_BUILDING_CARD_IN_DECK(7) batiments
 */
public class ZoneBuilding extends ZoneOnePlayer
{
	private ArrayList<Building> buildings;
	
	/* CONSTRUCTOR */
	public ZoneBuilding (String name){
		super(name,1);
		this.buildings = Settings.getRandomDeck();
	}
	
	public ZoneBuilding (ZoneBuilding z)
	{
		super(String.valueOf(z.getName()), z.getAvailableSpace());
		this.buildings = new ArrayList<Building>();
		for (int i = 0; i < z.getBuildings().size(); i++)
		{
			this.buildings.add(z.getBuildings().get(i));
		}
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
			if (((BuildingRessourceNotImposed)this.buildings.get(0)).checkNeededRessource(inventory.getCopyRessources()) == true)
			{
				// SI IL VEUT RETIRER SES POINTS DE VICTOIRE
				return this.occupated.getIA().pickBuilding();
			}
		}
		else if (type == 2)
		{
			// CHECKER SI LE JOUEUR PEUT CHOISIR
			if (((BuildingRessourceChoosed)this.buildings.get(0)).checkNeededRessource(inventory.getCopyRessources()) == true)
			{
				// SI IL VEUT RETIRER SES POINTS DE VICTOIRE
				return this.occupated.getIA().pickBuilding();
			}

		}

		// LE JOUEUR EST CONTRAINT DE REFUSER 
		return false;
	}

	/**
	 * Gere le systeme pour retirer la figurine de la zone.
	 * @param player : le joueur concerne.
	 * @param inventory inventaire du joueur concerner.
	 * @return Renvoie l'id de l'action suplementaire (si necesaire sinon 0).
	 */
	public int playerRecoveryFigurine(Player player,Inventory inventory){
		int number = super.howManyPlayerFigurine(player);

		if (number > 0){
			boolean wantToPickBuilding = true;
			wantToPickBuilding = chooseTakeBuilding(buildings.get(0).getNeededRessource(),inventory);
			// SI LE JOUEUR VEUT LA TUILE BATIMENT
			if (wantToPickBuilding == true)
			{
				// SI C'EST UN BATIMENT SPECIAL
				if (this.buildings.get(0).getType() > 0)
				{
					// BUIDLING NOT IMPOSED
					if (this.buildings.get(0).getType() == 1)
					{
						BuildingRessourceNotImposed b = (BuildingRessourceNotImposed) this.buildings.get(0);
						// public Ressource[] chooseRessourceBuildingNotImposed (int nombreRessource, int combienDeRessourcesDifferentes)
						while (b.checkRessourceNotImposed(player.getIA().chooseRessourceBuildingNotImposed(b.getNeededRessource().length, b.getHowManyDifferentRessource()), inventory.getCopyRessources()) == false);
					}
					else
					{
						BuildingRessourceChoosed b = (BuildingRessourceChoosed) this.buildings.get(0);
						// public Ressource[] chooseRessourceBuildingChoosed ()
						while (b.checkRessourceChoosed(player.getIA().chooseRessourceBuildingChoosed(), inventory.getCopyRessources()) == false);
					}
				}
				
				// ON SUPPRIME LES RESSOURCES DU JOUEUR UNE PAR UNE
				for (int i = 0; i < this.buildings.get(0).getNeededRessource().length; i++)
				{
					Printer.getPrinter().println("Le joueur "+occupated.getName()+" depense 1 "
							+this.buildings.get(0).getNeededRessource()[i]+" pour la "+ this.getName()+".");
					inventory.subRessource(this.buildings.get(0).getNeededRessource()[i], 1);
				}
				
				// LE JOUEUR OBTIENT UNE CARTE BATIMENT EN PLUS
				inventory.incrementBuilding();
				Printer.getPrinter().println("Le joueur "+player.getName()+" obtient la "+this.getName()+".");
				
				// ON DONNE LE SCORE ASSOCIE AU JOUEUR
				if (this.buildings.get(0).getType() == 0) {
					super.occupated.addScore(this.buildings.get(0).getVP());
					Printer.getPrinter().println("Le joueur "+ occupated.getName()+ " obtient "
							+this.buildings.get(0).getVP()+ " points de victoire (tuile).");
				}
				else
				{
					super.occupated.addScore(((BuildingSpecial)this.buildings.get(0)).computeVP());
					Printer.getPrinter().println("Le joueur "+ occupated.getName()+ " obtient "
							+((BuildingSpecial)this.buildings.get(0)).computeVP()+ " points de victoire (tuile).");
				}
			
				// ON SUPPRIME LA TUILE BATIMENT DE LA PILE
				this.buildings.remove(0);
				// ON REND AU JOUEUR SA FIGURINE
			}
			else {
				Printer.getPrinter().println("Le joueur "+player.getName()+" ne prend pas la "+this.getName()+".");
			}
			super.removeFigurine(player);
		}
		return 0;

	}


	/**
	 * Permet de savoir si le deck de la zone est vide ou non
	 * @return true s'il est vide, false sinon
	 */
	public boolean isDeckEmpty () {return this.buildings.isEmpty();}
	
	/**
	 * Retourne le building en haut de la liste
	 * @return Building type
	 */
	public Building getBuilding () {return this.buildings.get(0);}
	
	/**
	 * Retourne les batiments
	 * @return ArrayList type
	 */
	public ArrayList<Building> getBuildings () {return this.buildings;}
}
