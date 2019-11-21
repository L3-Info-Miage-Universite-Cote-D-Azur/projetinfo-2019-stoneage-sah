package game;

public class ZoneCarteCivilisation implements Zone{
	private int availableSpace;//Les places disponible dans la zone actuellement.
	private Player occupated; // le joueur occupant la zone
	private int figurineInZone; // des figurines dans la zone?
	private String name; //Le nom de la zone
	private CarteCivilisation carteCivilisation;//La carte Civilisation. 
	private int numberRessourceNeed;//Le nombre de ressource pour la carte civilisation


	//CONSTRUCTOR
	ZoneCarteCivilisation(String name,int ressourceNeed){
		this.name=name;
		this.numberRessourceNeed=ressourceNeed;
		this.availableSpace=1;
	}

	//GETTERS
	public int getAvailableSpace(){return availableSpace;}
	public String getName(){return name;}
	public int getRessourceNeed(){return numberRessourceNeed;}
	public int figurineInZone() {return figurineInZone;}
	public Player occupated() {return occupated;}
	public CarteCivilisation getCard() { return carteCivilisation; }

	//SETTERS
	public void setCard(CarteCivilisation newCard) { this.carteCivilisation = newCard; }


	//Figurine
	/**
	 * placeFigurine(int, Player) place 1 figurine appartenant a player dans la zone. 
	 * @param number : le nombre de figurine a mettre dans la zone, player : le joueur qui les mets. 
	 */ 
	public void placeFigurine(int number,Player player){
		availableSpace-=number;
		figurineInZone += number;
		occupated = player;
	}

	/**
	 * ableToChooseZone(int) renvoie true si il reste de la place dans zone
	 */
	public boolean ableToChooseZone(int currentPlayerFigurine){
		return (availableSpace>0);
	}

	/**
	 * Enleve les figurines du joueur player dans la zone. 
	 * @param player : le joueur a qui on retire les figurines de la zone. 
	 */ 
	public void removePlayerFigurine(Player player){
		availableSpace += howManyPlayerFigurine(player);
		figurineInZone -= howManyPlayerFigurine(player);
	}

	/**
	 * Retourne le nombre de figurines que player a dans la zone, 0 si il n'occupe pas la zone.
	 * @param player: le joueur dont on veut savoir le nombre de figurines dans la zone. 
	 */ 
	public int howManyPlayerFigurine(Player player){
		if(figurineInZone == 0 || occupated != player){
			return 0;
		}
		return figurineInZone;
	} 
	/**
	 * @param player 
	 * enl�ve les figurines du joueur de la zone
	 * rends les figurines au joueur
	 * ajoute une ressource au joueur
	 * affiche un message pour confirmer que le joueur a re�u la ressource, puis affiche le nombre qu'il poss�de
	 * @return id de l'effet sur la parti (il en existe que 1 lancer de d�s pour que chacun selectionne ca ressource)
	 * 0 : aucune action suplementaire necessaire
	 */
	public void playerRecoveryFigurine(Player player) {
		//recuperation des figurines
		int number = howManyPlayerFigurine(player);
		removePlayerFigurine(player);
		player.recoveryFigurine(number);
		
		boolean pickCard = chooseRecoveryCard(player,numberRessourceNeed);//choisi si il veux recuperer la carte et quelle ressource il utilise pour ce faire
		if(!pickCard) {
			System.out.println("Le joueur "+player.getName()+" ne prend pas la carte de "+this.getName()+".");
			return;
		}
		
		switch(carteCivilisation.getTypeUpPart()){

		case 0 : //cas ou c'est un gain de ressource direct
			int total = ressourceEffect(carteCivilisation);
			player.getInventory().addRessource(carteCivilisation.getRessource(),total);
			System.out.println("Le joueur "+player.getName()+" obtient la carte civilisation de "+this.getName()+".");
			break;

		case 1 : //cas ou le joueur gagne des point de victoire
			player.addScore(carteCivilisation.getNumberEffect());
			System.out.println("Le joueur "+player.getName()+" obtient "+carteCivilisation.getNumberEffect()+" point de victoire.");
			break;
			
		default :
			System.out.println("Erreur Carte Civilisation");
		}
		carteCivilisation=null; //suppresion de la carte civilisation de la zone.
	}

	/**
	 * ressourceEffect renvoie le nombre de ressource donne par la carte Civilisation.
	 * Si le nombre de ressource se fait par lancer de de, renvoie le nombre de ressource gagne selon 
	 * le resultat des des.
	 * @param carteCivilisation, la carte concernee.
	 * @return : int, le nombre de ressource que donne la carte.
	 */
	private int ressourceEffect(CarteCivilisation carteCivilisation){
		int total;
		Ressource ressource = carteCivilisation.getRessource();

		if(carteCivilisation.getNumberEffect()==0){
			int [] dice =Dice.rollDice(2);
			total = 0;

			for(int x : dice){
				total+=x;
			}

			total = (int) (total/ressource.getDivisor());
		}
		else{total = carteCivilisation.getNumberEffect();}

		return total;
	}


	/**
	 * retourne le nombre de figurine minimum n�cessaire pour occuper la zone	
	 */
	public int getMinimalFigurineRequierement() {
		return 1;
	}


	/**
	 * S'occupe de demander au joueur si il veux recuperer ca carte et par quel ressource
	 * @param player le joueur concernet
	 * @param ressourceRequire les ressource necessaire pour obtenir la carte
	 * @return true il recupere et choisie les ressource qu'il utilise, false il ne touche pas a la carte
	 */
	public boolean chooseRecoveryCard(Player player,int ressourceRequire) {
		int sum;
		boolean verif;

		//verification ressource suffisant
		sum=0;
		int[] copie = player.getInventory().getCopyRessources();
		for(int i = 0; i<4; i++) {
			sum += copie[i];
		}
		if(sum<ressourceRequire) {
			return false;
		}

		//on demande au joueur
		while(true) {
			sum=0;
			verif = true;
			int[] choix = player.getIA().pickCard(player.getInventory().getCopyRessources(), ressourceRequire);//AJOUTER AU FUTUR LA CARTE CONCERNEE

			for(int i = 0; i<4 && verif; i++) {
				if(choix[i] > player.getInventory().getRessource(Ressource.indexToRessource(i)) || choix[i] < 0) { 
					verif=false;
				}
				sum+=choix[i];
			}

			if (sum==0 && verif) {return false; } //cas ou il ne recupere pas la carte
			if(verif && sum==ressourceRequire) {
				for(int i = 0; i<4 && verif; i++) {
					player.getInventory().subRessource(Ressource.indexToRessource(i), choix[i]);
					if(choix[i] != 0) {
						System.out.println("Le joueur "+player.getName()+" depense "+ choix[i]
								+" "+ Ressource.indexToRessource(i)+ " pour la carte civilisation de "+this.getName()+".");
					}
				}
				return true;
			}
		}
	}
}
