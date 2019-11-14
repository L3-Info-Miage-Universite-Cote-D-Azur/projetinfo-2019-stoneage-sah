package game;



/**
 * La classe represente la zone du champ. 
 */

class ZoneField implements Zone{
    /* FIELDS */
	private int availableSpace;//Les places disponible dans la zone actuellement.
	private Player occupated; // le joueur occupant la zone
	private int figurineInZone; // des figurines dans la zone?
	private String name; //Le nom de la zone
	private Ressource ressource; // la ressource
    
	//CONSTRUCTOR
    ZoneField(String name,Ressource ressource){
        this.name=name;
        this.availableSpace=Settings.MAX_ZONEFIELD_SPACE;
        this.ressource= ressource;
    }
    
    //GETTERS
    public int getAvailableSpace(){return availableSpace;}
    public String getName(){return name;}
    public Ressource getRessource(){return ressource;}
    public int figurineInZone() {return figurineInZone;}
    public Player occupated() {return occupated;}
  
    
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
     * enlève les figurines du joueur de la zone
     * rends les figurines au joueur
     * ajoute une ressource au joueur
     * affiche un message pour confirmer que le joueur a reçu la ressource, puis affiche le nombre qu'il possède
     */
    public void playerRecoveryFigurine(Player player) {
    	int number = howManyPlayerFigurine(player);
        removePlayerFigurine(player);
        player.recoveryFigurine(number);
        player.getInventory().addRessource(getRessource(),1);
        System.out.println("Le joueur "+ player.getName() +" a maintenant "+ player.getInventory().getRessource(getRessource())+ " "+ getRessource().toString()+".");
    }
    /**
     * retourne le nombre de figurine minimum nécessaire pour occuper la zone	
     */
    public int getMinimalFigurineRequierement() {
    	return 1;
    }
}
