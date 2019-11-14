package game;



/**
 * La classe represente les zones de ressource du jeu. 
 */

class ZoneField implements Zone{
    /* FIELDS */
	private int availableSpace;//Les places disponible dans la zone actuellement.
	//Dictionnaire qui contient pour un joueur, le nombre de figurine qu'il a dans la zone.
	private Player occupated;
	private int figurineInZone;
	private String name; //Le nom de la zone
	private Ressource ressource;
	
	/* FINAL FIELDS */
    //Probablement inutile dans cette conception private final int totalAvailableSpace;//La place totale disponible dans la zone. 
    
    ZoneField(String name,Ressource ressource){
        this.name=name;
        this.availableSpace=SETTING.MAX_ZONEFIELD_SPACE;
        this.ressource= ressource;
        //this.totalAvailableSpace=this.availableSpace;
    }
    
    //GETTERS
    public int getAvailableSpace(){return availableSpace;}
    public String getName(){return name;}
    public Ressource getRessource(){return ressource;}
  
    
    //Figurine
    /**
     * placeFigurine(int, Player) place number figurine appartenant a player dans la zone. 
     * @param number : le nombre de figurine a mettre dans la zone, player : le joueur qui les mets. 
     */ 
    public void placeFigurine(int number,Player player){
        availableSpace-=number;
        figurineInZone += number;
        occupated = player;
    }
    
    /**
     * 
     * @return
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
     * Retourne le nombre de figurines que player a dans la zone. 
     * @param player: le joueur dont on veut savoir le nombre de figurines dans la zone. 
     */ 
    public int howManyPlayerFigurine(Player player){
        if(figurineInZone == 0 || occupated != player){
            return 0;
        }
        return figurineInZone;
    } 
    
    public void playerRecoveryFigurine(Player player) {
    	int number = howManyPlayerFigurine(player);
        removePlayerFigurine(player);
        player.recoveryFigurine(number);
        player.getInventory().addRessource(getRessource(),1);
    }
    	
    public int getMinimalFigurineRequierement() {
    	return 1;
    }
}
