import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShopPanel extends JPanel{

	private int shopSquareIncrement = 90; 
	private JLabel coins;
	private ArrayList<CastleShopItem> items = new ArrayList<CastleShopItem>();

	public ShopPanel() {

		coins = new JLabel("Coins :");
		coins.setVerticalAlignment(JLabel.TOP);
		coins.setFont(new Font("Sans Serif", Font.BOLD, 20));
		JLabel shopLabel = new JLabel("Castle Shop");
		shopLabel.setFont(new Font("Sans Serif", Font.BOLD, 20));
		shopLabel.setHorizontalAlignment(JLabel.LEFT);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(150,700));
		this.setBackground(new Color(171, 133, 99));
		this.add(shopLabel,BorderLayout.NORTH);
		this.add(coins, BorderLayout.CENTER);

	}
	public void updateShop(ArrayList<CastleShopItem> items) {
		this.items = items;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int shopIncrement = 0;
		g.drawRect(1, 1, 150, 50);
		g.setColor(new Color(158, 108, 33));
		g.fillRect(1, 1, 150, 50);
		g.setFont(new Font("Sans Serif", Font.PLAIN, 14));
		for(int i = 50; i < 700; i = i+shopSquareIncrement) {
			g.setColor(Color.BLACK);
			g.drawRect(0, i, 150, 90);
			if(shopIncrement < 7) {
				g.drawString(items.get(shopIncrement).toString1(), 1, i+30);
				g.drawString(items.get(shopIncrement).toString2(), 1, i+50);
				g.drawString(items.get(shopIncrement).toString3(), 1, i+70);
				shopIncrement ++;
			}
		}
	}

	public void updateCoins(int coins) {
		this.coins.setText("Coins: "+coins);
	}
}
