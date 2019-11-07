package Game;
import Client.*;

public class Program {
    public static void main(String[] args) {
    	/* FIELDS */
    	Player player = new Player();
    	Zone currentZone = null;
        Zone forest = new Zone("Forest", 3, 7);
        
        /* PHASE 1: PLACEMENT DE LA FIGURINE PAR LE JOUEUR*/
        System.out.println("Player has " + player.getInv().getWood() + " woods in his inventory");
        
        int chooseZone = ClientPlayer.chooseZone();
        if (chooseZone == 0) {
        	currentZone = forest;
        	System.out.println("Player choosed " + currentZone.getName() + " zone");
        } else {
        	System.out.println("Player choosed a false zone: returned " + chooseZone);
        	System.exit(2);
        }
        
        int figurineNb = -1;
        while (figurineNb <= 0 || figurineNb > player.getFigurine())
        	figurineNb = ClientPlayer.chooseNumber(player.getFigurine());
        System.out.println("Player choosed to put " + figurineNb + " figurine(s) in the " + currentZone.getName() + " zone");
        
        if (currentZone.addFigurine(figurineNb) == true) {
            System.out.println(currentZone.getAvailableSpace() + " remaining space in " + currentZone.getName() + " zone");
        	player.addFigurineInZone(chooseZone, figurineNb);
        }

        /* PHASE 2: RECOLTE DES RESSOURCES */
        player.getInv().addWood(2);
        System.out.println("Player won 2 woods");
        System.out.println("Player has " + player.getInv().getWood() + " woods in his inventory");
        currentZone.subFigurine(player.getFigurineInZone()[chooseZone]);
        System.out.println(currentZone.getAvailableSpace() + " remaining space in " + currentZone.getName() + " zone");
        player.subFigurineInZone(chooseZone);
        
        if (player.getInv().getWood() == 2)
        	System.out.println("The player won !");
        else
        	System.out.println("The player loose !");
    }
}
