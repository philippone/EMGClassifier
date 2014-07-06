package classification;
import gui.EMGClassifierGUI;
import javafx.stage.Stage;


public class Main {

	public Main() {
	}

	public static void main(String[] args) {

		EMGClassifierGUI gui = new EMGClassifierGUI();
		Stage s = new Stage();
		try {
			gui.start(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
