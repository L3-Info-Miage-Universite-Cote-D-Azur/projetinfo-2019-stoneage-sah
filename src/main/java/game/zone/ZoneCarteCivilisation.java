package game.zone;

import game.CarteCivilisation;
import game.Dice;
import game.Ressource;
import game.Settings;
import inventory.Inventory;
import player.Player;
import printer.Printer;

/**
 * ZoneCarteCivilisation represente les 4 zones ou se trouvent les cartes civilisation dans le jeu.
 * @author Groupe SAH
 */
public class ZoneCarteCivilisation extends ZoneOnePlayer{

	private CarteCivilisation carteCivilisation;//La carte Civilisation. 
	private int numberRessourceNeed;//Le nombre de ressource pour la carte civilisation
	private final Dice dice;

	/* CONSTRUCTOR */
	public ZoneCarteCivilisation(String name, int numberRessourceNeed, Dice dice){
		super(name,1); //Le nombre de figurine minimal requis est de 1.
		this.numberRessourceNeed = numberRessourceNeed;
		this.dice = dice;
	}
	
	public ZoneCarteCivilisation (ZoneCarteCivilisation z)
	{
		super(String.valueOf(z.getName()), z.getAvailableSpace());
		this.numberRessourceNeed = z.getNumberRessourceNeed();
		this.dice = new Dice(z.getDice());
	}

	/* GETTERS */
	public int getNumberRessourceNeed() {return this.numberRessourceNeed;}
	public CarteCivilisation getCard(){return this.carteCivilisation;}
	public Dice getDice(){return this.dice;}

	/* SETTERS */
	public void setCard(CarteCivilisation cv){this.carteCivilisation = cv;}



	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 * @param inventory : l'inventaire du joueur concerne.
	 * @return nombre qui represente les action suplementaire a effectuer 
	 * (pour les carte ou tout les joueur sont affecter ou piocher une carte
	 *  supplementaire pour la donner au joueur)
	 * 
	 */ 
	public int playerRecoveryFigurine(Player player, Inventory inventory) {
		//recuperation des figurines
		int number = super.howManyPlayerFigurine(player);

		if (number > 0){//Si il n'y a pas de probleme
			super.removeFigurine(player);

			//choisi si il veux recuperer la carte et quelle ressource il utilise pour ce faire
			boolean pickCard = chooseRecoveryCard(player,inventory,numberRessourceNeed);

			if(!pickCard) {//Le joueur ne prend pas la carte
				Printer.getPrinter().println("Le joueur "+player.getName()+" ne prend pas la carte de "+this.getName()+".");
				return 0;
			}
			
			//Ajout de la carte a l'inventaire + suppression de la carte de la zone
			inventory.addCardCivilisation(carteCivilisation);
			CarteCivilisation copieLinkCard = carteCivilisation;
			carteCivilisation=null; //suppresion de la carte civilisation de la zone.
			Printer.getPrinter().println("Le joueur "+player.getName()+" obtient la carte civilisation de "+this.getName()+".");

			switch(copieLinkCard.getTypeUpPart()){
			case 0 : //cas ou c'est un gain de ressource direct
				int total = ressourceEffect(copieLinkCard);
				inventory.addRessource(copieLinkCard.getRessource(),total);
				Printer.getPrinter().println("Le joueur "+player.getName()+" obtient "+total
						+" "+copieLinkCard.getRessource().toString()+ " (carte).");
				return 0;

			case 1 : //cas ou le joueur gagne des point de victoire
				player.addScore(copieLinkCard.getNumberEffect());
				Printer.getPrinter().println("Le joueur "+player.getName()+" obtient "+copieLinkCard.getNumberEffect()+" point de victoire (carte).");
				return 0;
				
			case 2 : //cas ou le joueur pioche une carte civilisation suplementaire
				//aucun traitement suplementaire necesaire sur le joueur en question les action seront fait par la classe qui traite le retour
				return 1;
				
			case 3 : //cas ou le joueur pioche les carte ou tout le monde dois selectionner son bonnus
				//aucune action suplementaire necesaire sur le joueur les action seront fait par la classe qui traite le retour
				return 2;
				
			case 4 : //outils temporaire
				inventory.getTools().addUniqueTool(copieLinkCard.getNumberEffect());
				Printer.getPrinter().println("Le joueur "+player.getName()+" obtient un outils temporaire de valeur : "+copieLinkCard.getNumberEffect());
				return 0;
			case 5:
				inventory.incrementCardRessource();
				Printer.getPrinter().println("Le joueur "+player.getName()+" obtient "+copieLinkCard.getNumberEffect()+" ressources au choix.");
				return 0;
			default :
				Printer.getPrinter().println("Erreur Carte Civilisation");
			}
			carteCivilisation=null; //suppresion de la carte civilisation de la zone.
		}
		return 0;
	}

	/**
	 * ressourceEffect concerne les cartes civilisation donnant des ressources. 
	 * La fonction renvoie les ressources rapportees.
	 * @param carteCivilisation: la carte concernee. 
	 */
	private int ressourceEffect(CarteCivilisation carteCivilisation){
		int total;
		Ressource ressource = carteCivilisation.getRessource();//La ressource de la carte.

		if(carteCivilisation.getNumberEffect()==0){//Si le nombre de ressource se recupere par lancer de de. 
			int [] diceValue = dice.rollDice(Settings.RAND,2);
			total = 0;

			for(int x : diceValue){
				total+=x;
			}

			total = (int) (total/ressource.getDivisor());
		}
		else{total = carteCivilisation.getNumberEffect();}//Si la carte a un nombre de ressource fixe. 

		return total;
	}

	/**
	 * chooseRecoveryCard renvoie true ou false selon que l'IA decide de prendre la carte ou non.
	 * @param player : le joueur concerne
	 * @param inventory : l'inventaire du joueur concerne.
	 * @param ressourceRequire : le nombre de ressource pour la carte.
	 * @return boolean : true si le joueur prend la carte, false sinon. 
	 */
	private boolean chooseRecoveryCard(Player player,Inventory inventory, int ressourceRequire) {
		int sum;
		boolean verif;

		//verification ressource suffisant
		sum = 0;
		int[] copie = inventory.getCopyRessources();
		for(int i = 0; i<4; i++) {
			sum += copie[i];
		}
		if(sum<ressourceRequire) {//Si le joueur n'a pas assez de ressource pour acheter la carte. 
			return false;
		}

		//on demande au joueur
		while(true) {
			sum=0;
			verif = true;
			//On demande a l'IA
			int[] choix = player.getIA().pickCard(ressourceRequire);//AJOUTER AU FUTUR LA CARTE CONCERNEE

			for(int i = 0; i<4 && verif; i++) {
				if(choix[i] > inventory.getRessource(Ressource.indexToRessource(i)) || choix[i] < 0) 
				{ 
					verif=false; //Le retour de l'IA a un probleme. 
				}
				sum+=choix[i];
			}

			if (sum==0 && verif) {return false; } //cas ou il ne recupere pas la carte

			if(verif && sum==ressourceRequire) //Cas ou l'IA prend la carte. 
			{
				for(int i = 0; i<4 && verif; i++) {
					inventory.subRessource(Ressource.indexToRessource(i), choix[i]);//On retire les ressources.

					if(choix[i] != 0) {//On affiche.
						Printer.getPrinter().println("Le joueur "+player.getName()+" depense "+ choix[i]
								+" "+ Ressource.indexToRessource(i)+ " pour la carte civilisation de "+this.getName()+".");
					}
				}
				return true;
			}
		}
	}
}
