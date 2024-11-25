import java.util.ArrayList;

public class CastleShop implements StatObserver{
	
	private ArrayList<CastleShopItem> shopItems = new ArrayList<CastleShopItem>();
	private int coins;
	private Player p;
	private Castle c;
	private CastleDefenseModel m;
	
	public CastleShop(int coins, Player p, Castle c, CastleDefenseModel m) {
		this.coins = coins;
		this.p = p;
		this.c = c;
		this.m = m;
		
		setUpCastleShop();
	}
	private void setUpCastleShop() {
		shopItems.add(new CastleShopItem("Upgrade Weapon",10,0,50));
		shopItems.add(new CastleShopItem("Additional Arrows",10,0,140));
		shopItems.add(new CastleShopItem("Castle Turret",25,0,230));
		shopItems.add(new CastleShopItem("Repair Castle",25,0,320));
		shopItems.add(new CastleShopItem("Upgrade Speed",15,0,410));
		shopItems.add(new CastleShopItem("Purchase Turret",10,0,500));
		shopItems.add(new CastleShopItem("Upgrade Turrets",50,0,590));
	}
	
	public void detectItemPressed(int x, int y) {
		for(CastleShopItem item: shopItems) {
			if(item.isPressed(x,y) == true){
				if(item.getLevel() < 3 && coins >= item.getPrice()) {
					handleItemPressed(item);
				}
			}
		}
	}
	private void handleItemPressed(CastleShopItem item) {
		switch(item.getName()) {
			case "Upgrade Weapon":
				upgradeWeapon(item);
				break;
			case "Castle Turret":
				castleTurret(item);
				break;
			case "Upgrade Speed":
				playerSpeed(item);
				break;
			case "Additional Arrows":
				additionalArrows(item);
				break;	
			case "Purchase Turret":
				purchaseTurret(item);
				break;
			case "Upgrade Turrets":
				upgradeTurrets(item);
				break;
			case "Repair Castle":
				m.setCoins(coins - item.getPrice());
				c.setHealth(c.getHealth()+50);
				item.setPrice(item.getPrice()+10);
				break;
		}
	}
	private void upgradeTurrets(CastleShopItem item) {
		m.setCoins(coins - item.getPrice());
		item.setLevel(item.getLevel()+1);
		switch(item.getLevel()) {
		case 1:
			for(ShopTurret t: m.getShopTurrets()) {
				t.setWeapon(new Weapon("Archer",10,20,10,4,100));
			}
			item.setPrice(150);
			break;
		case 2:
			for(ShopTurret t: m.getShopTurrets()) {
				t.setWeapon(new Weapon("Catapult",15,15,10,7,75));
			}
			item.setPrice(300);
			break;
		case 3:
			for(ShopTurret t: m.getShopTurrets()) {
				t.setWeapon(new Weapon("Ballista",20,10,20,5,25));
			}
		}
		
	}
	private void purchaseTurret(CastleShopItem item) {
		m.purchaseTurret();
		m.setCoins(coins - item.getPrice());
		item.setPrice(item.getPrice()+10);
	}
	private void additionalArrows(CastleShopItem item) {
		m.setCoins(coins - item.getPrice());
		item.setLevel(item.getLevel()+1);
		switch(item.getLevel()) {
		case 1:
			p.setProjectileCount(p.getProjectileCount()+1);
			item.setPrice(20);
			break;
		case 2:
			p.setProjectileCount(p.getProjectileCount()+1);
			item.setLevel(item.getLevel()+1);
			break;
		}
	}
	private void playerSpeed(CastleShopItem item) {
		m.setCoins(coins - item.getPrice());
		item.setLevel(item.getLevel()+1);
		switch(item.getLevel()) {
		case 1:
			p.setMaxSpeed(6);
			item.setPrice(25);
			break;
		case 2:
			p.setMaxSpeed(7);
			item.setPrice(35);
			break;
		case 3:
			p.setMaxSpeed(8);
		}
	}
	private void castleTurret(CastleShopItem item) {
		m.setCoins(coins - item.getPrice());
		item.setLevel(item.getLevel()+1);
		switch(item.getLevel()) {
		case 1:
			c.setWeapon(new Weapon("Archer",10,20,10,4,100));
			m.addCastleToTurrets();
			item.setPrice(50);
			break;
		case 2:
			c.setWeapon(new Weapon("Catapult",15,15,10,7,75));
			item.setPrice(100);
			break;
		case 3:
			c.setWeapon(new Weapon("Ballista",20,10,20,5,25));
		}
	}
	private void upgradeWeapon(CastleShopItem item) {
		m.setCoins(coins - item.getPrice());
		item.setLevel(item.getLevel()+1);
		switch(item.getLevel()) {
		case 1:
			p.setWeapon( new Weapon("Bow & Arrow",8,15,15,4,100));
			item.setPrice(45);
			break;
		case 2:
			p.setWeapon( new Weapon("Crossbow",15,12,20,5,100));
			item.setPrice(75);
			break;
		case 3:
			p.setWeapon( new Weapon("Auto-Bow",20,7,25,6,100));
			break;
		}
	}

	public ArrayList<CastleShopItem> getShopItems() {
		return shopItems;
	}
	
	public void setShopItems(ArrayList<CastleShopItem> shopItems) {
		this.shopItems = shopItems;
	}
	@Override
	public void updateStat(int coins, int score, int castleHealth, ArrayList<CastleShopItem> items) {
		this.coins = coins;
	}
}
