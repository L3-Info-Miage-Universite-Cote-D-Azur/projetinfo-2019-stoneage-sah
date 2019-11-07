package game;

public class Zone {
	
	/* FIELDS */
	private int divisor;
	private int availableSpace;
	private String name;
	
	/* FINAL FIELDS */
	private final int totalAvailableSpace;
		
	/* CONSTRUCTOR */
	public Zone (String name, int divisor, int availableSpace) {
		this.name = name;
		this.divisor = divisor;
		this.availableSpace = availableSpace;
		this.totalAvailableSpace = this.availableSpace;
	}
	
	/* METHODS */
	/**
	 * La fonction hasEnoughSpace(size) prends en paramètre un int et renvoie un booléen
	 * elle vérifie si size est > 0 et si size n'est pas plus grand que l'espace disponible.
	 * @param size corréspond au nombre de figurine a placer dans la zone
	 * @return true si il y a assez de place et que size>0 false sinon
	 */
	public boolean hasEnoughSpace (int size) {
		if (this.availableSpace - size < 0 || size < 0)
			return false;
		return true;
	}
	/**
	 * La fonction addFigurine(size) prends en paramètre un int et renvoie un booléen
	 * elle vérifie si la zone a une place restante > size , si oui , on enlève size au nombre de places disponible a la zone
	 * @param size correspond a un nombre de figurine
	 * @return true si le nombre de place restante est supérieur a size, false sinon
	 */
	public boolean addFigurine (int size)
	{
		if (this.hasEnoughSpace(size) == true) {
			this.availableSpace -= size;
			return true;
		}
		return false;
	}
	/**
	 * La fonction subFigurine(size) prends en paramètre un int et renvoie un booléen
	 * elle vérifie si size est inférieur au nombre total de place et si size est supérieur a 0, si oui , le nombre de place disponible augmente de size
	 * @param size correspond a un nombre de figurine
	 * @return true si size > 0 et size< nombre total de place , false sinon
	 */
	public boolean subFigurine (int size)
	{
		if (size <= this.totalAvailableSpace && size >= 0) {
			this.availableSpace += size;
			return true;
		}
		return false;
	}
	/**
	 * réinitialisation de l'espace disponible des zone
	 */
	public void resetZone () {
		this.availableSpace = this.totalAvailableSpace;
	}
	
	/* GETTERS */
	public int getAvailableSpace () {return this.availableSpace;}
	
	public int getDivisor () {return this.divisor;}
	
	public String getName () {return this.name;}
}
