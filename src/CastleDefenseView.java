import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

public class CastleDefenseView extends JFrame{

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private Player player = new Player(10, new Location(200,200), new Velocity(0,0),100);
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<ShopTurret> turrets = new ArrayList<ShopTurret>();
	private Castle castle = new Castle();
	private CardLayout cardLayout;
	private JPanel gamePanel;
	private JPanel mainPanel;
	private AniPanel aniPanel;
	private ShopPanel shopPanel;
	private HealthPanel healthPanel;
	private JButton playAgain;
	private JPanel replayAniPanel;
	private int coins,score,castleHealth;
	private JLabel scoreLabel;
	private JLabel gameOverLabel;

	public JButton getPlayAgain() {
		return playAgain;
	}
	
	public CastleDefenseView(String s) {
		super(s);
		setUp();
	}
	public void setUp() {
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		this.setContentPane(mainPanel);
		
		playAgain = new JButton("PLAY AGAIN");
		playAgain.setFont(new Font("Monospaced",Font.PLAIN,30));
		playAgain.setHorizontalAlignment(JButton.CENTER);
		
		replayAniPanel = new JPanel(new BorderLayout());
		
		gameOverLabel = new JLabel("GAME-OVER");
		gameOverLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
		gameOverLabel.setHorizontalAlignment(JLabel.CENTER);

		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("Monospaced", Font.ITALIC, 30));;
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		gamePanel = new JPanel();
		mainPanel.add(gamePanel,"Game Panel");
		cardLayout.show(mainPanel, "Game Panel");

		healthPanel = new HealthPanel();
		healthPanel.setOpaque(false);
		
		
		aniPanel = new AniPanel(new BorderLayout());
		healthPanel.setPreferredSize(new Dimension(750,50));
		aniPanel.add(healthPanel,BorderLayout.SOUTH);
		aniPanel.add(scoreLabel, BorderLayout.NORTH);
		aniPanel.add(replayAniPanel,BorderLayout.CENTER);
		playAgain.setPreferredSize(new Dimension(100,100));
		
		aniPanel.setBackground(new Color(246, 241, 241));
		
		replayAniPanel.setOpaque(false);
		replayAniPanel.add(gameOverLabel,BorderLayout.NORTH);
		replayAniPanel.add(playAgain,BorderLayout.SOUTH);
		
		replayAniPanel.setVisible(false);
		
		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(aniPanel,BorderLayout.CENTER);
	
	}

	public void setStats(int coins, int score, int castleHealth, ArrayList<CastleShopItem> items) {
		if(shopPanel == null) {
			shopPanel = new ShopPanel();
			gamePanel.add(shopPanel,BorderLayout.EAST);
		}
		shopPanel.updateCoins(coins);
		shopPanel.updateShop(items);
		this.coins = coins;
		this.score = score;
		this.castleHealth = castleHealth;
		scoreLabel.setText(""+score);
	}
	public void setEntities(ArrayList<Bullet> b, ArrayList<Enemy> e, Castle c, Player p, ArrayList<ShopTurret> t) {
		bullets = b;
		enemies = e;
		castle = c;
		player = p;
		turrets = t;
		repaint();
	}
	public void gameOver() {
		replayAniPanel.setVisible(true);
	}
	public void playAgain() {
		replayAniPanel.setVisible(false);
		this.requestFocusInWindow();
	}
	
	private class AniPanel extends JPanel{
		public AniPanel(BorderLayout l) {
			super(l);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(117, 113, 108));
			g.fillOval((int)player.getLoc().getX(), (int)player.getLoc().getY(), 2*player.getSize(),2*player.getSize());
			g.setColor(Color.BLACK);
			g.drawOval((int)player.getLoc().getX(), (int)player.getLoc().getY(), 2*player.getSize(),2*player.getSize());	
			g.drawOval((int)castle.getLoc().getX(), (int)castle.getLoc().getY(), 2*castle.getSize(), 2*castle.getSize());
			g.setColor(new Color(158, 108, 33));
			g.fillOval((int)castle.getLoc().getX(), (int)castle.getLoc().getY(), 2*castle.getSize(), 2*castle.getSize());
			for(ShopTurret t: turrets) {
				g.setColor(Color.BLACK);
				g.drawOval((int)t.getLoc().getX(),(int)t.getLoc().getY(), 2*t.getSize(),2*t.getSize());
				g.setColor(new Color(71, 71, 214));
				g.fillOval((int)t.getLoc().getX(),(int)t.getLoc().getY(), 2*t.getSize(),2*t.getSize());
			}
			for(Enemy e: enemies) {
				g.setColor(Color.BLACK);
				g.drawOval((int)e.getLoc().getX(),(int)e.getLoc().getY(), 2*e.getSize(),2*e.getSize());
				if(e.isPlayerTargeting()) {
					g.setColor(new Color(242, 182, 17));
				}
				else {
					g.setColor(Color.RED);
				}
				g.fillOval((int)e.getLoc().getX(),(int)e.getLoc().getY(), 2*e.getSize(),2*e.getSize());
			}
			g.setColor(Color.BLACK);
			for(Bullet e: bullets) {
				g.fillOval((int)e.getLoc().getX(),(int)e.getLoc().getY(), 2*e.getSize(),2*e.getSize());
			}
		}
	}
	private class HealthPanel extends JPanel{
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Sans Serif", Font.BOLD,13));
			g.drawString("CASTLE: ", 20, 25);
			g.drawRect(80, 14, 300, 15);
			g.setColor(new Color(212, 2, 2));
			g.fillRect(80, 13, 300, 15);
			if(castleHealth >= 66) {
				g.setColor(new Color(115, 191, 77));
			}
			else if(castleHealth >= 33) {
				g.setColor(Color.YELLOW);
			}
			else{
				g.setColor(Color.ORANGE);
			}
			g.fillRect(80,13,castleHealth*3,15);
		}
	}
}
