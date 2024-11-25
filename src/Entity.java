
public class Entity {

	private Acceleration acc;
	private Location loc;
	private Velocity velo;
	private int size,health; // Hit-box Size

	public Entity(int size, Location loc, Velocity velo) {
		this.loc = loc;
		this.velo = velo;
		this.size = size;
		acc = new Acceleration(0,0);
		health = 1;
	}
	public Entity(int health,int size, Location loc, Velocity velo) {
		this.loc = loc;
		this.velo = velo;
		this.size = size;
		acc = new Acceleration(0,0);
		this.health = health;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public Acceleration getAcc() {
		return acc;
	}
	public void setAcc(Acceleration acc) {
		this.acc = acc;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public Velocity getVelo() {
		return velo;
	}
	public void setVelo(Velocity velo) {
		this.velo = velo;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Entity detectCollision(Entity e) {
		int xDist, yDist, doubleRad;
		xDist = (int)((loc.getX()+size) - (e.getLoc().getX()+e.getSize()));
		yDist = (int)((loc.getY()+size) - (e.getLoc().getY()+e.getSize()));
		doubleRad = size + e.getSize();
		if((doubleRad*doubleRad) >= (xDist*xDist) + (yDist*yDist)) {
			return e;
		}
		return null;
	}
	public void motion() {
		loc.setX((loc.getX()+velo.getxVelo()));
		loc.setY((loc.getY()-velo.getyVelo()));
	}

}
