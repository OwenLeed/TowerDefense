import java.util.ArrayList;

public class ShopTurret extends Entity implements TurretBehavior{

	Weapon w;
	
	public ShopTurret(int health, int size, Location loc, Velocity velo) {
		super(health, size, loc, velo);
		this.w = new Weapon("Archer",8,18,10,4,100);
	}

	@Override
	public Enemy findNearestEnemy(ArrayList<Enemy> e) {
		if(e.isEmpty())
			return null;
		double minDistance = 2000;
		Enemy closest = null;
		double xd = 0;
		double yd = 0;
		for(Enemy enemy: e) {
			if(enemy.getLoc().getX() < 0 || enemy.getLoc().getX() > 750 || enemy.getLoc().getY() < 0 || enemy.getLoc().getY()> 700)
				continue;
			xd = (this.getSize()+this.getLoc().getX()) - (enemy.getLoc().getX() + enemy.getSize());
			yd = (this.getSize()+this.getLoc().getY()) - (enemy.getLoc().getY() + enemy.getSize());
			if(Math.sqrt((xd*xd) + (yd*yd)) < minDistance) {
				minDistance = Math.sqrt((xd*xd) + (yd*yd));
				closest = enemy;
			}
		}
		return closest;
	}

	@Override
	public Weapon getWeapon() {
		return w;
	}
	
	public void setWeapon(Weapon w) {
		this.w = w;
	}

}
