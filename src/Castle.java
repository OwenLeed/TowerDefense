import java.util.ArrayList;

public class Castle extends Entity implements TurretBehavior {

	Weapon weapon;
	
	public Castle() {
		super(100,25, new Location(350,310), new Velocity(0,0));
		weapon = null;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
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

}
