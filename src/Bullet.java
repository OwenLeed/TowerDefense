
public class Bullet extends Entity{

	public Bullet(int damage,int size, Location loc, Velocity velo) {
		super(damage,size, loc, velo);
		// TODO Auto-generated constructor stub
	}
	public Bullet(int damage,int size, double x, double y, Velocity velo) {
		super(damage,size, new Location(x,y), velo);
		// TODO Auto-generated constructor stub
	}

}
