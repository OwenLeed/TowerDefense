import java.awt.Dimension;

public class CastleShopItem {

	private boolean available;
	private int price, level;
	private String name;
	private Location loc;
	private Dimension dim;

	public CastleShopItem(String name,int price,int x, int y) {
		dim = new Dimension(150,90);
		available = true;
		this.name = name;
		this.price = price;
		loc = new Location(x,y);
		level = 0;
	}

	public boolean isPressed(int x, int y) {
		//Sets with X Dimension of Entire Window
		x = x-750;
		if(x <= loc.getX()+dim.width && x >= loc.getX() && y <= loc.getY()+dim.height && y >= loc.getY()) {
			return true;
		}
		return false;
	}
	//Formatting for how to draw text on ShopPanel
	public String toString1() {
		return name;
	}
	public String toString3() {
		if(!name.equals("Purchase Turret")) {
			if(level == 0) {
				return "";
			}
			if(level >= 3) {
				return "Level MAX";
			}
			return "Level "+level;
		}
		return"";
	}
	public String toString2() {
		if(level < 3) {
			return price+" Coins";
		}
		return "";
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public Dimension getDim() {
		return dim;
	}
	public void setDim(Dimension dim) {
		this.dim = dim;
	}
}
