import java.util.ArrayList;

public interface TurretBehavior {
	
	public Enemy findNearestEnemy(ArrayList<Enemy> e);
	public Weapon getWeapon();
	public Location getLoc();
	public int getSize();
}
