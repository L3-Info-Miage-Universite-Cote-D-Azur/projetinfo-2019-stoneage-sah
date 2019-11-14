package game;

import java.util.Arrays;
import java.util.Hashtable;

/**
 * La classe represente les zones de ressource du jeu. 
 * Chasse, Foret, Glaisiere et carriere.
 */
class ZoneRessource implements Zone{
    /* FIELDS */
	private int divisor; //Le diviseur de la zone 
	private int availableSpace;//Les places disponible dans la zone actuellement.
	//Dictionnaire qui contient pour un joueur, le nombre de figurine qu'il a dans la zone.
	private Hashtable<Player,Integer> figurineInZone; 
	private String name; //Le nom de la zone
	private Ressource ressource;//Le nom de la ressource recoltee dans la zone.
	
	/**
	 * Construit la zone de ressource.
	 * @param name nom de la zone.
	 * @param ressource Ressource que la zone produit.
	 * @param divisor diviseur des dÃ©e lors de la recolte.
	 * @param availableSpace place maximum disponible.
	 */
    ZoneRessource(String name,Ressource ressource,int divisor, int availableSpace){
        this.name=name;
        this.divisor=divisor;
        this.availableSpace=availableSpace;
        this.figurineInZone=new Hashtable<Player,Integer>();
        this.ressource = ressource;
    }
    
    //GETTERS
    public int getAvailableSpace(){return availableSpace;}
    public int getDivisor(){ return divisor;}
    public String getName(){return name;}
    public Ressource getRessource(){return ressource;}
    
    
    //Figurine
  
    /**
     * placeFigurine(int, Player) place number figurine appartenant a player dans la zone. 
     * @param number : le nombre de figurine a mettre dans la zone, player : le joueur qui les mets.
     * @param player : le joueur qui depose les figurine dans la zone;  
     */
    public void placeFigurine(int number,Player player){
        availableSpace-=number;
        if(figurineInZone.get(player) == null) {figurineInZone.put(player,number);}
        else{ figurineInZone.put(player,number+figurineInZone.get(player)); }
    }
    
    
    /**
     * La zone peut etre choisie pour placer des joueur a l'interrieur?
     * @return true : on peut selectionner la zone
     * false : on peut pas.
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
        figurineInZone.put(player,0);
    }
    
    /**
     * Retourne le nombre de figurines que player a dans la zone. 
     * @param player: le joueur dont on veut savoir le nombre de figurines dans la zone. 
     */ 
    public int howManyPlayerFigurine(Player player){
        if(figurineInZone.get(player)==null){
            return 0;
        }
        return figurineInZone.get(player);
    } 
    
    
    /**
     * Le joueur recupere son due dans la zone
     * @param player : Le player qui recupere ces figurine dans la zone
     */
    public void playerRecoveryFigurine(Player player) {
    	int number = howManyPlayerFigurine(player);
    	int[] dice = Dice.rollDice(number);
        int sum=0;
        
        for (int value : dice) { sum += value; }
        int total=(int) (sum/getDivisor());
        
        removePlayerFigurine(player);
        player.recoveryFigurine(number);
        player.getInventory().addRessource(getRessource(), total);
        System.out.println("Le joueur "+player.getName()+" a eu comme lancer de de: "+Arrays.toString(dice));
        System.out.println("Il recolte donc: "+total+" "+getRessource());
    }
    
    /**
     * le nombre minimal que le joueur dois placer quand il a choisi la zone.
     * @return renvoie le nombre minimum a placer dans la zone selectionner soit 1 pour les zone de ressource
     */
    public int getMinimalFigurineRequierement() {
    	return 1;
    }
}
