import java.util.ArrayList;

public interface StatObserver {

	public void updateStat(int coins, int score, int castleHealth, ArrayList<CastleShopItem> items);
}
