package game;



/**
 * La classe represente les zones de ressource du jeu. 
 */


class ZoneHut implements Zone{
    /* FIELDS */
	private int availableSpace;//Les places disponible dans la zone actuellement.
	private Player occupated;//Le joueur present dans la zone
	private int figurineInZone;//Dictionnaire qui contient pour un joueur, le nombre de figurine qu'il a dans la zone.
	private String name; //Le nom de la zone
	
	/* FINAL FIELDS */
    //Probablement inutile dans cette conception private final int totalAvailableSpace;
	//La place totale disponible dans la zone. 
    
    ZoneHut(String name){
        this.name=name;
        this.availableSpace=Settings.MAX_ZONEHUT_SPACE;
        //this.totalAvailableSpace=this.availableSpace;
    }
    
    //GETTERS
    public int getAvailableSpace(){return availableSpace;}
    public String getName(){return name;}
    public int figurineInZone() {return figurineInZone;}
    public Player occupated() {return occupated;}
  
  
    
    //Figurine
    /**
     * placeFigurine(int, Player) place number figurine appartenant a player dans la zone. 
     * @param number : le nombre de figurine a mettre dans la zone.
     * @param player : le joueur qui les mets. 
     */ 
    public void placeFigurine(int number,Player player){
        availableSpace-=number;
        figurineInZone += number;
        occupated = player;
    }
    
    /**
     * Decide si le joueur peut choisir cette zone.
     * @param currentplayerFigurine : le nombre de figurine restant au joueur.
     * @return l'espace restante dans la zone si superieur a 0
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
        figurineInZone -= 2;
    }
    
    /**
     * Retourne le nombre de figurines dans la zone. 
     * @param player: le joueur qui peut placer ses figurines.
     * @return 0 s'il n'y a pas de figurine dans la zone ou si le joueur est différent de celui qui occupe celle-ci
     * @return figurineZone s'il y a de la place dans la zone 
     */ 
    public int howManyPlayerFigurine(Player player){
        if(figurineInZone == 0 || occupated != player){
            return 0;
        }
        return figurineInZone;
    }
    
    /**
     * Retourne le nombre de figurines que player a dans la zone. 
     * @param player: le joueur dont on veut savoir le nombre de figurines dans la zone. 
     */ 
    public void playerRecoveryFigurine(Player player) {
    	int number = howManyPlayerFigurine(player);
        removePlayerFigurine(player);
        player.recoveryFigurine(number);
        boolean ok = player.increaseMaxFigurine();
        if(ok) { System.out.println("Le joueur "+ player.getName()+" a maintenant "+ player.getMaxFigurine()+" figurine");}
        else { System.out.println("Le joueur "+ player.getName()+" a deja le maximum de figurine");}
    }
    
    /**
     * Donne le nombre de figurine minimum necessaire pour occuper la zone.
     * @return 2.
     */
    public int getMinimalFigurineRequierement() {
    	return 2;
    }
}
}
