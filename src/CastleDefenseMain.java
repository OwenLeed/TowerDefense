import javax.swing.JFrame;

public class CastleDefenseMain {

	public static void main(String[] args) {
		CastleDefenseModel model = new CastleDefenseModel();
		CastleDefenseView view = new CastleDefenseView("Castle Defense");
		new CastleDefenseController(model, view);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(900,700);
		view.setLocation(200,200);
		view.setResizable(false);
		view.setVisible(true);
	}
}
