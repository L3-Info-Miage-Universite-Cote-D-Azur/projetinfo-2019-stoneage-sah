package game;

/**
 * La classe FigurineManagement gere la cohesion entre zone et figurine.  
 */
 
class FigurineManagement{
    
    /**
     * ableToPlaceFigurine() utilise les ableToPlaceFigurine() de zone et de player pour savoir si cela est possible. 
     * @param number : le nombre de figurine a placer, player : le joueur concerne, zone : la zone concernee.
     * @return true si possible de placer, false sinon
     */ 
    public static boolean ableToChooseZone(Zone zone,Player player,int indexZone){
    	//le indexZone == 4 est l'index de la chasse ou l'on peut posser tout le temps
    	return zone.ableToChooseZone(player.getCurrentFigurine()) && ( player.ableToPlaceFigurine(indexZone) || indexZone == 4) && zone.getMinimalFigurineRequierement()<=player.getCurrentFigurine();
    }
    
    /**
     * placeFigurine fait appel au placeFigurine de player et de zone puis affiche un message bilan. 
     * @param number : le nombre de figurine a placer, player : le joueur concerne, zone : la zone concernee.
     */ 
    public static void placeFigurine(Zone zone, Player player,int number,int indexZone){
        zone.placeFigurine(number, player); 
        player.placeFigurine(number);
        player.setHadPlaced(indexZone,true);
        System.out.println(stringPlaceFigurine(zone,player,number));
    }
    
    //affichage
    /**
     * Affichage textuel du joueur qui place dans la zone un nombre de figurine
     * @param zone zone a affichage
     * @param player player a afficher
     * @param number nombre a qui a etait placer
     * @return phrase qui resume l'action
     */
    public static String stringPlaceFigurine(Zone zone, Player player, int number){
        String str = "Le joueur "+ player.getName() + " a place "+ number +" figurines dans la zone "+zone.getName()+".";
        return str;
    }
    
    //phase de recuperation des figurine
    /**
     * permet a player de recupere ses figurines dans zone et renvoie le nombre de ressource gagnee dedans. 
     * @param zone : la zone concern�e, player : le joueur qui recupere les figurines. 
     */ 
    public static void recoveryFigurineInZone(Zone zone, Player player){
    	zone.playerRecoveryFigurine(player);
    }
}