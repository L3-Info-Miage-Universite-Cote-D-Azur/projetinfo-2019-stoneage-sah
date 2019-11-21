package game;

import java.util.ArrayList;

/**
 * CarteCivilisationManager s'occupe de tout ce qui concerne les cartes civilisation sur le plateau de jeu. 
 * @author Mentra20
 *
 */
public class CarteCivilisationManager {
	
	private ZoneCarteCivilisation[] cardZone;
	private ArrayList<CarteCivilisation> deck;
	
	/* CONSTRUCTOR */
	public CarteCivilisationManager(ZoneCarteCivilisation[] cardZone) {
		this.cardZone = cardZone;
		initDeck();
		initCardInZone();
	}
	
	/**
	 * initCardInZone initialise les cartes dans les ZoneCarteCivilisation.
	 */
	private void initCardInZone(){
        for(int i=0; i < cardZone.length;i++){ 
        	cardZone[i].setCard(getRandomCivilisationCard()); 
        }
    }
	
	/**
	 * InitDeck initialise le deck de cartes civilisations.
	 */
	public void initDeck() {
		deck = new ArrayList<CarteCivilisation>();
		//Cartes vertes : 
		deck.add(new CarteCivilisation(0, 4, 0, Ressource.FIELD, 1));
		deck.add(new CarteCivilisation(1, 6, 0, null, 3));
		deck.add(new CarteCivilisation(1, 6, 0, null, 3));
		deck.add(new CarteCivilisation(0, 5, 0, Ressource.STONE, 2));
		deck.add(new CarteCivilisation(0, 0, 0, Ressource.FOOD, 5));
		deck.add(new CarteCivilisation(0, 3, 0, Ressource.FOOD, 7));
		deck.add(new CarteCivilisation(0, 7, 0, Ressource.FOOD, 3));
		deck.add(new CarteCivilisation(0, 7, 0, Ressource.FOOD, 1));
		deck.add(new CarteCivilisation(0, 1, 0, Ressource.GOLD, 0));
		//deck.add(new CarteCivilisation(0, 1, 0, Ressource.TOOL, 1)); POUR PLUS TARD. 
		
		//Cartes jaunes : 
		deck.add(new CarteCivilisation(0, 10, 1, Ressource.FOOD, 4));
		deck.add(new CarteCivilisation(0, 10, 2, Ressource.FOOD, 2));
		deck.add(new CarteCivilisation(1, 10, 3, null, 3));
		deck.add(new CarteCivilisation(0, 8, 2, Ressource.FOOD, 3));
		deck.add(new CarteCivilisation(0, 8, 1, Ressource.STONE, 1));
		deck.add(new CarteCivilisation(0, 8, 1, Ressource.FIELD, 1));
		deck.add(new CarteCivilisation(0, 11, 2, Ressource.CLAY, 1));
		deck.add(new CarteCivilisation(0, 11, 2, Ressource.WOOD, 0));
		deck.add(new CarteCivilisation(0, 11, 1, Ressource.STONE, 0));
		deck.add(new CarteCivilisation(0, 11, 1, Ressource.STONE, 1));
		deck.add(new CarteCivilisation(0, 11, 1, Ressource.GOLD, 1));
	}
	
	/**
	 * getRandomCivilisationCard renvoie une carte civilisation au hasard du deck, et la retire du deck. 
	 * @return CarteCivilisation : la carte tiree au hasard. 
	 */
	public CarteCivilisation getRandomCivilisationCard() {
		if(deck.size()==0) return null; //plus de carte a piocher
		int index = Settings.RAND.nextInt(deck.size());
		CarteCivilisation card = deck.get(index);
		
		deck.remove(index);
		return card; 
	}
	
	/**
	 * organizeCard replace les cartes comme il faut a la fin du tour.
	 * @return true -> operation effectuer; false -> plus de carte disponnible fin de la parti
	 */
	public boolean organizeCard() {
		int n = cardZone.length-1;
		while(n >= 0) {
			if(null == cardZone[n].getCard()) {
				int m = n;
				
				while(m >= 0) {
					if(cardZone[m].getCard() != null) {
						cardZone[n].setCard(cardZone[m].getCard());
						cardZone[m].setCard(null);
						break;
					}
					m--;
				}
				if(m < 0) {
					cardZone[n].setCard(getRandomCivilisationCard());
					if(cardZone[n].getCard()==null) return false; //si aucune carte reste a piocher
				}
			}
			n--;
		}
		return true;
		
	}
}
