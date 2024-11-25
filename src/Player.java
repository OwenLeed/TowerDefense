
public class Player extends Entity{

	private int maxSpeed;
	private boolean isAcceleratingX, isAcceleratingY;
	private int projectileCount;
	private Weapon w;

	public Player(int size, Location loc, Velocity velo,int health) {
		super(health,size, loc, velo);
		w = new Weapon("Slingshot",8,20,13,3,100);
		maxSpeed = 5;
		projectileCount = 1;
	}
	public Weapon getWeapon() {
		return w;
	}
	public void setWeapon(Weapon w) {
		this.w = w;
	}
	public void friction() {
		if(!isAcceleratingX) {
			if(this.getVelo().getxVelo() > 0) {
				this.getVelo().setxVelo(this.getVelo().getxVelo() - .15);
			}
			if(this.getVelo().getxVelo() < 0) {
				this.getVelo().setxVelo(this.getVelo().getxVelo() + .15);
			}
			if(Math.abs(this.getVelo().getxVelo()) <= .15) {
				this.getVelo().setxVelo(0);
			}
		}
		if(!isAcceleratingY) {
			if(this.getVelo().getyVelo() > 0) {
				this.getVelo().setyVelo(this.getVelo().getyVelo() - .15);
			}
			if(this.getVelo().getyVelo() < 0) {
				this.getVelo().setyVelo(this.getVelo().getyVelo() + .15);
			}
			if(Math.abs(this.getVelo().getyVelo()) <= .15) {
				this.getVelo().setyVelo(0);
			}
		}
	}
	@Override
	public void motion() {
		this.getVelo().setxVelo(this.getVelo().getxVelo()+this.getAcc().getxAcc());
		this.getVelo().setyVelo(this.getVelo().getyVelo()+this.getAcc().getyAcc());
		if(this.getVelo().getxVelo()>maxSpeed)
			this.getVelo().setxVelo(maxSpeed);
		if(this.getVelo().getxVelo()<-maxSpeed)
			this.getVelo().setxVelo(-maxSpeed);
		if(this.getVelo().getyVelo()>maxSpeed)
			this.getVelo().setyVelo(maxSpeed);
		if(this.getVelo().getyVelo()<-maxSpeed)
			this.getVelo().setyVelo(-maxSpeed);
		this.getLoc().setX((int)(this.getLoc().getX()+this.getVelo().getxVelo()));
		this.getLoc().setY((int)(this.getLoc().getY()-this.getVelo().getyVelo()));
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public boolean isAcceleratingX() {
		return isAcceleratingX;
	}
	public void setAcceleratingX(boolean isAcceleratingX) {
		this.isAcceleratingX = isAcceleratingX;
	}
	public boolean isAcceleratingY() {
		return isAcceleratingY;
	}
	public void setAcceleratingY(boolean isAcceleratingY) {
		this.isAcceleratingY = isAcceleratingY;
	}
	public int getProjectileCount() {
		return projectileCount;
	}
	public void setProjectileCount(int projectileCount) {
		this.projectileCount = projectileCount;
	}
}
