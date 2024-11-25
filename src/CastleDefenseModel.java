import java.util.ArrayList;

public class CastleDefenseModel implements EntitySubject, StatSubject, DifficultySubject{

	//GameEntities
	private Player player = new Player(13, new Location(200,200), new Velocity(0,0), 100);
	private Castle castle = new Castle();
	private ArrayList<TurretBehavior> turrets = new ArrayList<TurretBehavior>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<ShopTurret> shopTurrets = new ArrayList<ShopTurret>();
	//Observers Array
	private ArrayList<StatObserver> statObservers = new ArrayList<StatObserver>();
	private ArrayList<EntityObserver> entityObservers = new ArrayList<EntityObserver>();
	private ArrayList<DifficultyObserver> difficultyObservers = new ArrayList<DifficultyObserver>();
	private Difficulty difficulty;
	private GameStage stage;
	//Game Statistics
	private int coins;
	private CastleShop shop = new CastleShop(coins,player,castle,this);
	private int score;
	private CastleDefenseController cont;
	/*
	 * TO DO:
	 * - SHOP
	 * - IMPLEMENT CASTLE TURRET **DONE**
	 * - Refactor Entire Weapon System to Make use of Inheritence instead of an Interface
	 * - MAIN MENU
	 * - DIFFICULTY SELECTOR
	 * - PASS CASTLE SHOP ITEMS TO VIEW
	 */
	public CastleDefenseModel() {
		score = 0;
		coins = 0;
		difficulty = Difficulty.HARD;
		stage = GameStage.START;
		registerStatObserver(shop);
		notifyDifficultyObservers();
		notifyEntityObservers();
		notifyStatObservers();
	}
	public void addCont(CastleDefenseController cont) {
		this.cont = cont;
	}
	public void detectItemPressed(int x, int y) {
		shop.detectItemPressed(x, y);
	}
	public void addCastleToTurrets() {
		turrets.add(castle);
	}
	
	//If Castle health is Less than or equal to 0 game over (add Later)
	private void gameOver(){
		stage = GameStage.OVER;
		notifyDifficultyObservers();
	}
	public void newGame() {
		castle = new Castle();
		player = new Player(13, new Location(200,200), new Velocity(0,0), 100);
		turrets = new ArrayList<TurretBehavior>();
		shopTurrets = new ArrayList<ShopTurret>();
		score = 0;
		coins = 0;
		shop =  new CastleShop(coins,player,castle,this);
		enemies.clear();
		bullets.clear();
		stage = GameStage.START;
	}
	public ArrayList<TurretBehavior> getTurrets() {
		return turrets;
	}
	public void setTurrets(ArrayList<TurretBehavior> turrets) {
		this.turrets = turrets;
	}
	public void purchaseTurret(){
		cont.setDetectingLoc(true);
	}
	public void purchaseTurret(int x, int y) {
		ShopTurret t = new ShopTurret(150, 15, new Location(x-15,y-45), new Velocity(0,0));
		turrets.add(t);
		shopTurrets.add(t);
	}
	public void fireCastleTurret() {
		for(TurretBehavior t: turrets) {
			Enemy e = t.findNearestEnemy(enemies);
			if(e != null) {
				bullets.add(new TurretBullet(t.getWeapon().getDamage(),t.getWeapon().getBulletSize(),t.getLoc().getX()+t.getSize()-5,t.getLoc().getY()+t.getSize()-5,getTurretBulletVelocity(e.getLoc().getX()+e.getSize(),e.getLoc().getY()+e.getSize(),t)));
			}
		}
	}
	public void fireTurrets() {
		for(ShopTurret t: shopTurrets) {
			Enemy e = t.findNearestEnemy(enemies);
			if(e != null) {
				bullets.add(new TurretBullet(t.getWeapon().getDamage(),t.getWeapon().getBulletSize(),t.getLoc().getX()+t.getSize()-5,t.getLoc().getY()+t.getSize()-5,getTurretBulletVelocity(e.getLoc().getX()+e.getSize(),e.getLoc().getY()+e.getSize(),t)));
			}
		}
	}
	public Velocity getTurretBulletVelocity(double x, double y, TurretBehavior t) {
		double rx = 0;
		double ry = 0;
		rx = Math.random()*t.getWeapon().getAccuracy();
		ry = Math.random()*t.getWeapon().getAccuracy();
		if((int)(Math.random()*2)==0) {
			rx = rx*(-1);
		}
		if((int)(Math.random()*2) == 0) {
			ry = ry*(-1);
		}	
		double speed = t.getWeapon().getBulletSpeed();
		double xDist = t.getLoc().getX()+t.getSize()-x+rx;
		double yDist = t.getLoc().getY()+t.getSize()-y+ry;
		double length = Math.sqrt(xDist*xDist + yDist*yDist);
		xDist /= length;
		yDist /= length;
		return new Velocity(-1*xDist*speed,yDist*speed);
	}
	//Adds Bullet / after firing weapon
	public void addBullet(Location l) {
		if(player.getProjectileCount() == 1) {
			bullets.add(new Bullet(player.getWeapon().getDamage(),player.getWeapon().getBulletSize(),player.getLoc().getX()+player.getSize()-5,player.getLoc().getY()+player.getSize()-5,getBulletVelocity(l)));
			return;
		}
		if(player.getProjectileCount() == 2) {
			bullets.add(new Bullet(player.getWeapon().getDamage(),player.getWeapon().getBulletSize(),player.getLoc().getX()+player.getSize()-5,player.getLoc().getY()+player.getSize()-5,getBulletVelocity(l.getX()+15,l.getY()+15)));
			bullets.add(new Bullet(player.getWeapon().getDamage(),player.getWeapon().getBulletSize(),player.getLoc().getX()+player.getSize()-5,player.getLoc().getY()+player.getSize()-5,getBulletVelocity(l.getX()-15,l.getY()-15)));
		}
		if(player.getProjectileCount() == 3) {
			bullets.add(new Bullet(player.getWeapon().getDamage()/2,player.getWeapon().getBulletSize(),player.getLoc().getX()+player.getSize()-5,player.getLoc().getY()+player.getSize()-5,getBulletVelocity(l)));
			bullets.add(new Bullet(player.getWeapon().getDamage(),player.getWeapon().getBulletSize(),player.getLoc().getX()+player.getSize()-5,player.getLoc().getY()+player.getSize()-5,getBulletVelocity(l.getX()+25,l.getY()+25)));
			bullets.add(new Bullet(player.getWeapon().getDamage(),player.getWeapon().getBulletSize(),player.getLoc().getX()+player.getSize()-5,player.getLoc().getY()+player.getSize()-5,getBulletVelocity(l.getX()-25,l.getY()-25)));
		}
	}

	//I had to get some inspiration for how to actually set the Velocities in accordance to mouse position, I looked at https://gamedev.stackexchange.com/questions/174104/make-a-bullet-move-towards-the-mouse-position
	private Velocity getBulletVelocity(Location l) {
		//Speed in acordance with weapon (add Later)
		double speed = player.getWeapon().getBulletSpeed();
		double xDist = player.getLoc().getX()+player.getSize()-l.getX();
		double yDist = player.getLoc().getY()+player.getSize()-l.getY();
		double length = Math.sqrt(xDist*xDist + yDist*yDist);
		xDist /= length;
		yDist /= length;
		return new Velocity(-1*xDist*speed,yDist*speed);
	}
	private Velocity getBulletVelocity(double x, double y) {
		//Speed in acordance with weapon (add Later)
		double speed = player.getWeapon().getBulletSpeed();
		double xDist = player.getLoc().getX()+player.getSize()-x;
		double yDist = player.getLoc().getY()+player.getSize()-y;
		double length = Math.sqrt(xDist*xDist + yDist*yDist);
		xDist /= length;
		yDist /= length;
		return new Velocity(-1*xDist*speed,yDist*speed);
	}
	//Adds enemy (work on)
	public void addEnemy() {
		EnemyType eT = getRandEnemy();
		Location startingLocation = getRandStartLocation();
		switch(eT) {
		case GRUNT:
			enemies.add(new Enemy(15,20,startingLocation,getEnemyVelocity(startingLocation,20,1.5,castle)));
			break;
		case GIANT:
			enemies.add(new Enemy(30,25,startingLocation,getEnemyVelocity(startingLocation,25,1,castle)));	
			break;
		case BATALLION:
			Velocity v = getEnemyVelocity(startingLocation,15,1.5,castle);
			enemies.add(new Enemy(15,15,startingLocation,v));
			enemies.add(new Enemy(15,15,startingLocation.getX()-17,startingLocation.getY()-28,v));
			enemies.add(new Enemy(15, 15,startingLocation.getX()+17,startingLocation.getY()-28,v));
			break;
		case SCOUT:
			enemies.add(new Enemy(10,10,startingLocation,getEnemyVelocity(startingLocation,10,3,castle)));
			break;
		case BASHER:
			enemies.add(new Enemy(true,true,10,10,startingLocation,getEnemyDashVelocity(startingLocation,10,7,player),7,true));
			break;
		case ARCHER:
			enemies.add(new Enemy(true,false,10,10,startingLocation,getEnemyDashVelocity(startingLocation,10,6,player),6,false));
			break;
		case ARMY:
			enemies.add(new Enemy(true,true,20,15,startingLocation.getX()+30,startingLocation.getY()+30,getEnemyDashVelocity(startingLocation,15,6,player),6,true));
			enemies.add(new Enemy(true,false,15,10,startingLocation.getX()-30,startingLocation.getY()-30,getEnemyDashVelocity(startingLocation,10,4,player),4,false));
			enemies.add(new Enemy(true,false,10,10,startingLocation,getEnemyDashVelocity(startingLocation,10,3,player),3,false));
			break;
		}	
	}
	// Gets Random Enemy Velocity
	private Velocity getEnemyVelocity(Location l,int size, double s, Entity target) {
		double rx = 0;
		double ry = 0;
		if((int)(Math.random()*2)==0) {
			rx = Math.random()*100;
			ry = Math.random()*100;
			if((int)(Math.random()*2)==0) {
				rx = rx*(-1);
			}
			if((int)(Math.random()*2) == 0) {
				ry = ry*(-1);
			}		
		}
		double speed = s;
		double xDist = ((target.getLoc().getX())+target.getSize()-l.getX())+rx;
		double yDist = ((target.getLoc().getY())+target.getSize()-l.getY())+ry;
		double length = Math.sqrt(xDist*xDist + yDist*yDist);
		xDist /= length;
		yDist /= length;
		return new Velocity(xDist*speed,-1*yDist*speed);
	}
	private Velocity getEnemyDashVelocity(Location l,int size, double s, Entity target) {
		double speed = s;
		double xDist = ((target.getLoc().getX())+target.getSize()-l.getX());
		double yDist = ((target.getLoc().getY())+target.getSize()-l.getY());
		double length = Math.sqrt(xDist*xDist + yDist*yDist);
		xDist /= length;
		yDist /= length;
		return new Velocity(xDist*speed,-1*yDist*speed);
	}
	

	private Location getRandStartLocation() {
		int r = (int)(Math.random()*8);
		int rc = (int)(Math.random()*450);
		switch(r) {
		case 0:
			return new Location(-100+rc,-100); 
		case 1:
			return new Location(-100,-100+rc);
		case 2:
			return new Location(-100,800-rc);
		case 3:
			return new Location(-100+rc,800);
		case 4:
			return new Location(900,800-rc);
		case 5:
			return new Location(900-rc,800);
		case 6:
			return new Location(900-rc,-100);
		case 7:
			return new Location(900,-100+rc);
		}
		return null;
	}
	private EnemyType getRandEnemy() {
		int r = 0;
		switch(difficulty) {
		case EASY:
			r = (int)(Math.random()*8);
			break;
		case STANDARD:
			r = (int)(Math.random()*11);
			break;
		case HARD:
			r = (int)(Math.random()*15);	
		}
		if(r >= 0 && r<5) {
			return EnemyType.GRUNT;
		}
		if(r>=5 && r <8) {
			return EnemyType.GIANT;
		}
		if(r>=8 && r <9) {
			return EnemyType.BATALLION;
		}
		if(r>=9 && r <10) {
			return EnemyType.SCOUT;
		}
		if(r>=10 && r < 11) {
			return EnemyType.BASHER;
		}
		if(r>=11 && r <13) {
			return EnemyType.ARCHER;
		}
		if(r>=13 && r <=14) {
			return EnemyType.ARMY;
		}
		return null;
	}

	//Detects if Bullet is Collide with Enemy and lowers enemies health if is greater
	public void detectBulletCollision() {
		ArrayList<Entity> removals = new ArrayList<Entity>();
		for(Bullet b: bullets) {
			for(Enemy e: enemies) {
				if(b.detectCollision(e) != null) {
					score+=e.getHealth();
					b.setHealth(b.getHealth()-e.getHealth());
					e.setHealth(e.getHealth()-player.getWeapon().getDamage());
					if(e.getHealth()<=0) {
						coins++;
						removals.add(e);
						e.setHealth(0);
					}
					if(b.getHealth()<= 0) {
						removals.add(b);
					}
				}
			}
			if(b.getLoc().getX()< -100 || b.getLoc().getY() < -100 || b.getLoc().getX()>900 || b.getLoc().getY()>800) {
				removals.add(b);
			}
		}
		for(Entity e: removals) {
			if(e instanceof Bullet)
				bullets.remove(e);
			if(e instanceof Enemy)
				enemies.remove(e);
		}
	}
	//Implement Methods for Castle Health and Player Health
	public void detectPlayerCastleCollision() {
		ArrayList<Enemy> removals = new ArrayList<Enemy>();
		for(Enemy e: enemies) {
			if(e.detectCollision(player) != null) {
				removals.add(e);
				castle.setHealth(castle.getHealth()-e.getHealth());
				if(castle.getHealth() <= 0) {
					gameOver();
				}
			}
			if(e.detectCollision(castle) != null) {
				removals.add(e);
				castle.setHealth(castle.getHealth()-e.getHealth());
				if(castle.getHealth() <= 0) {
					gameOver();
				}
			}
			if(e.getLoc().getX()< -200 || e.getLoc().getY() < -200 || e.getLoc().getX()>1000 || e.getLoc().getY()>900) {
				removals.add(e);
			}
			for(ShopTurret t: shopTurrets) {
				if(e.detectCollision(t)!= null) {
					removals.add(e);
					t.setHealth(t.getHealth()-e.getHealth());
					if(t.getHealth() <= 0) {
						shopTurrets.remove(t);
						turrets.remove(t);
					}
				}
			}
		}
		for(Enemy e: removals) {
			enemies.remove(e);

		}
		//Notifies statistic and entity observers(panels)
		notifyStatObservers();
		notifyEntityObservers();
	}

	public ArrayList<ShopTurret> getShopTurrets() {
		return shopTurrets;
	}
	public void setShopTurrets(ArrayList<ShopTurret> shopTurrets) {
		this.shopTurrets = shopTurrets;
	}
	public void entityMotion() {
		player.motion();
		player.friction();
		for(Enemy e: enemies) {
			e.motion();
			if(e.isHasFriction()) {
				e.friction();
			}
			if(!e.isDasher() && e.isPlayerTargeting()) {
				e.setVelo(getEnemyDashVelocity(e.getLoc(),e.getSize(),e.getDashSpeed(),player));
			}
		}
		for(Bullet b: bullets) {
			b.motion();
		}
	}
	public void enemyDash() {
		for(Enemy e: enemies) {
			if(e.isDasher()) {
				if(e.isPlayerTargeting()) {
					Velocity v = getEnemyDashVelocity(e.getLoc(),e.getSize(),e.getDashSpeed(),player);
					e.setVelo(v);
				}
				if(!e.isPlayerTargeting()) {
					Velocity v = getEnemyDashVelocity(e.getLoc(),e.getSize(),e.getDashSpeed(),castle);
					e.setVelo(v);
				}
			}
		}
	}

	public void setCoins(int num) {
		coins = num;
	}
	//Gets player object for weapon/bullet/positon
	public Player getPlayer() {
		return player;
	}
	public Castle getCastle() {
		return castle;
	}
	public void setCastle(Castle castle) {
		this.castle = castle;
	}

	@Override
	public void registerStatObserver(StatObserver obs) {
		statObservers.add(obs);
	}
	@Override
	public void removeStatObserver(StatObserver obs) {
		statObservers.remove(obs);
	}
	@Override
	public void notifyStatObservers() {
		for(StatObserver obs: statObservers) {
			obs.updateStat(coins, score,castle.getHealth(),shop.getShopItems());
		}
		if(score >= 5000 && stage != GameStage.END2) {
			stage = GameStage.END2;
			notifyDifficultyObservers();
		}
		else if(score >= 4000 && stage != GameStage.END1 && score <5000) {
			stage = GameStage.END1;
			notifyDifficultyObservers();
		}
		else if(score >= 2500 && stage != GameStage.MAIN && score <4000) {
			stage = GameStage.MAIN;
			notifyDifficultyObservers();
		}
		else if(score >= 1000 && stage != GameStage.BUILD && score <2500){
			stage = GameStage.BUILD;
			notifyDifficultyObservers();
		}
	}
	@Override
	public void registerEntityObserver(EntityObserver obs) {
		entityObservers.add(obs);
	}
	@Override
	public void removeEntityObserver(EntityObserver obs) {
		entityObservers.remove(obs);
	}
	@Override
	public void notifyEntityObservers() {
		for(EntityObserver obs: entityObservers) {
			obs.updateEntity(bullets, enemies, castle, player, shopTurrets);
		}
	}
	@Override
	public void registerDifficultyObserver(DifficultyObserver o) {
		difficultyObservers.add(o);
		notifyDifficultyObservers();

	}
	@Override
	public void removeDifficultyObserver(DifficultyObserver o) {
		difficultyObservers.remove(o);
	}
	@Override
	public void notifyDifficultyObservers() {
		for(DifficultyObserver o: difficultyObservers) {
			o.updateDifficulty(stage);
		}
	}
}
