import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.Timer;

public class CastleDefenseController implements EntityObserver, StatObserver, DifficultyObserver, KeyListener, MouseMotionListener,MouseListener,ActionListener{

	private CastleDefenseModel model;
	private CastleDefenseView view;
	private Location mouseLocation;
	private Timer gameTick;
	private Timer enemyTick;
	private boolean isFiring;
	private int tick;
	private int turretTick;
	private int dashTick;
	private int castleTick;
	private boolean isDetectingLoc;

	public CastleDefenseController(CastleDefenseModel model, CastleDefenseView view) {
		this.model = model;
		this.view = view;
		model.addCont(this);
		tick = model.getPlayer().getWeapon().getFireRate();
		dashTick = 0;
		turretTick = 0;
		castleTick = 0;
		isFiring = false;
		mouseLocation = new Location(view.getWidth()/2,view.getHeight()/2);
		view.addKeyListener(this);
		view.addMouseMotionListener(this);
		view.addMouseListener(this);
		view.getPlayAgain().addActionListener(this);
		model.registerEntityObserver(this);
		model.registerStatObserver(this);
		model.registerDifficultyObserver(this);
		gameTick = new Timer(22,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isFiring) {
					if(tick % model.getPlayer().getWeapon().getFireRate() == 0) {
						addBullet();
						tick = 0;
					}
					tick++;
				}
				if(model.getCastle().getWeapon() != null) {
					if(castleTick % model.getCastle().getWeapon().getFireRate() == 0) {
						model.fireCastleTurret();
						castleTick = 0;
					}
					castleTick ++;
				}
				if(!model.getTurrets().isEmpty()) {
					if(turretTick % model.getTurrets().get(0).getWeapon().getFireRate() == 0) {
						model.fireTurrets();
						turretTick = 0;
					}
					turretTick++;
				}
				if(dashTick % 25 == 0) {
					model.enemyDash();
					dashTick = 0;
				}
				dashTick++;
				model.entityMotion();
				model.detectBulletCollision();
				model.detectPlayerCastleCollision();
			}});

		gameTick.start();
	}
	public void updateEnemyTimer(int t) {
		enemyTick = new Timer(t,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.addEnemy();
			}
		});
		enemyTick.start();
	}
	@Override
	public void updateEntity(ArrayList<Bullet> b, ArrayList<Enemy> e, Castle c, Player p,ArrayList<ShopTurret> t) {
		view.setEntities(b, e, c, p,t);
	}
	@Override
	public void updateStat(int coins, int score, int castleHealth, ArrayList<CastleShopItem> items) {
		view.setStats(coins,score,castleHealth, items);
	}
	private void addBullet(){
		model.addBullet(mouseLocation);
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(isDetectingLoc) {
			model.purchaseTurret(e.getX(), e.getY());
			isDetectingLoc = false;
		}
		model.detectItemPressed(e.getX(), e.getY());
		
	}
	public CastleDefenseModel getModel() {
		return model;
	}
	public void setModel(CastleDefenseModel model) {
		this.model = model;
	}
	public boolean isDetectingLoc() {
		return isDetectingLoc;
	}
	public void setDetectingLoc(boolean isDetectingLoc) {
		this.isDetectingLoc = isDetectingLoc;
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLocation.setX(e.getX());
		mouseLocation.setY(e.getY());
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case 87: // W
			model.getPlayer().getAcc().setyAcc(1);
			model.getPlayer().setAcceleratingY(true);
			break;
		case 83: // S
			model.getPlayer().getAcc().setyAcc(-1);
			model.getPlayer().setAcceleratingY(true);
			break;
		case 65: // A
			model.getPlayer().getAcc().setxAcc(-1);
			model.getPlayer().setAcceleratingX(true);
			break;
		case 68: // D
			model.getPlayer().getAcc().setxAcc(1);
			model.getPlayer().setAcceleratingX(true);
			break;
		case 32: // Space
			isFiring = true;
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case 87:
			model.getPlayer().getAcc().setyAcc(0);
			model.getPlayer().setAcceleratingY(false);
			break;
		case 83:
			model.getPlayer().getAcc().setyAcc(0);
			model.getPlayer().setAcceleratingY(false);
			break;
		case 65:
			model.getPlayer().getAcc().setxAcc(0);
			model.getPlayer().setAcceleratingX(false);
			break;
		case 68:
			model.getPlayer().getAcc().setxAcc(0);
			model.getPlayer().setAcceleratingX(false);
			break;
		case 32:
			isFiring = false;
			break;
		}		
	}
	@Override
	public void updateDifficulty(GameStage g) {
		switch(g) {
		case START:
			updateEnemyTimer(3000);
			break;
		case BUILD:
			updateEnemyTimer(2000);
			break;
		case MAIN:
			updateEnemyTimer(1000);
			break;
		case END1:
			updateEnemyTimer(750);
			break;
		case END2:
			updateEnemyTimer(500);
			break;
		case OVER:
			view.gameOver();
			gameTick.stop();
			enemyTick.stop();
			break;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("PLAY AGAIN")) {
			model.newGame();
			gameTick.start();
			enemyTick.start();
			view.playAgain();
			view.addKeyListener(this);
		}
	}
}

