import java.util.ArrayList;

public interface EntityObserver {

	void updateEntity(ArrayList<Bullet> b, ArrayList<Enemy> e, Castle c, Player p, ArrayList<ShopTurret> shopTurrets);
}
