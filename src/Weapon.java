
public class Weapon {

	private String name;
	private int damage,fireRate, bulletSpeed, bulletSize, accuracy;
	public Weapon(String name, int damage, int fireRate, int bulletSpeed, int bulletSize, int accuracy) {
		this.name = name;
		this.damage = damage;
		this.fireRate = fireRate;
		this.bulletSpeed = bulletSpeed;
		this.bulletSize = bulletSize;
		this.accuracy = accuracy;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFireRate() {
		return fireRate;
	}
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	public int getBulletSpeed() {
		return bulletSpeed;
	}
	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	public int getBulletSize() {
		return bulletSize;
	}
	public void setBulletSize(int bulletSize) {
		this.bulletSize = bulletSize;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
}
