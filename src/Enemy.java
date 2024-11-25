
public class Enemy extends Entity{

	private boolean playerTargeting,hasFriction,isDasher;
	private double dashSpeed;
	
	public Enemy(int health,int size, Location loc, Velocity velo) {
		super(health,size, loc, velo);
		playerTargeting = false;
		hasFriction = false;
	}
	public Enemy(int health, int size, double x, double y, Velocity velo) {
		super(health,size,new Location(x,y),velo);
		playerTargeting = false;
		hasFriction = false;
	}
	public Enemy(boolean playerTargeting, boolean hasFriction, int health,int size, Location loc, Velocity velo, double dashSpeed, boolean isDasher) {
		super(health,size, loc, velo);
		this.playerTargeting = playerTargeting;
		this.hasFriction = hasFriction;
		this.dashSpeed = dashSpeed;
		this.isDasher = isDasher;
	}
	public Enemy(boolean playerTargeting, boolean hasFriction, int health,int size, double x, double y, Velocity velo, double dashSpeed, boolean isDasher) {
		super(health,size, new Location(x,y), velo);
		this.playerTargeting = playerTargeting;
		this.hasFriction = hasFriction;
		this.dashSpeed = dashSpeed;
		this.isDasher = isDasher;
	}
	public void motion() {
		this.getLoc().setX((this.getLoc().getX()+this.getVelo().getxVelo()));
		this.getLoc().setY((this.getLoc().getY()-this.getVelo().getyVelo()));
	}
	public void friction() {
		if(this.getVelo().getxVelo() > 1) {
			this.getVelo().setxVelo(this.getVelo().getxVelo() - .15);
		}
		if(this.getVelo().getxVelo() < 1) {
			this.getVelo().setxVelo(this.getVelo().getxVelo() + .15);
		}

		if(this.getVelo().getyVelo() > 1) {
			this.getVelo().setyVelo(this.getVelo().getyVelo() - .15);
		}
		if(this.getVelo().getyVelo() < 1) {
			this.getVelo().setyVelo(this.getVelo().getyVelo() + .15);
		}
	}
	public boolean isPlayerTargeting() {
		return playerTargeting;
	}
	public void setPlayerTargeting(boolean playerTargeting) {
		this.playerTargeting = playerTargeting;
	}
	public boolean isHasFriction() {
		return hasFriction;
	}
	public void setHasFriction(boolean hasFriction) {
		this.hasFriction = hasFriction;
	}
	public boolean isDasher() {
		return isDasher;
	}
	public void setDasher(boolean isDasher) {
		this.isDasher = isDasher;
	}
	public double getDashSpeed() {
		return dashSpeed;
	}
	public void setDashSpeed(double dashSpeed) {
		this.dashSpeed = dashSpeed;
	}

}
