package Game;

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
	public boolean hasEnoughSpace (int size) {
		if (this.availableSpace - size < 0 || size < 0)
			return false;
		return true;
	}
	
	public boolean addFigurine (int size)
	{
		if (this.hasEnoughSpace(size) == true) {
			this.availableSpace -= size;
			return true;
		}
		return false;
	}
	
	public boolean subFigurine (int size)
	{
		if (size <= this.totalAvailableSpace && size >= 0) {
			this.availableSpace += size;
			return true;
		}
		return false;
	}
	
	public void resetZone () {
		this.availableSpace = this.totalAvailableSpace;
	}
	
	/* GETTERS */
	public int getAvailableSpace () {return this.availableSpace;}
	
	public int getDivisor () {return this.divisor;}
	
	public String getName () {return this.name;}
}
