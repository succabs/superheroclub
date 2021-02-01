package fxsupersankarit;

import supersankarit.Supersankarit;	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Arttu Nikkilä
 * @version 26.3.2017
 *
 */
public class SupersankaritMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("SupersankaritGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final SupersankaritGUIController supersankaritCtrl = (SupersankaritGUIController)ldr.getController();
			
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("supersankarit.css").toExternalForm());
			primaryStage.setScene(scene);
            primaryStage.setTitle("Supersankarit");

            Supersankarit supersankarit = new Supersankarit(); 
            supersankaritCtrl.setSuperSankarit(supersankarit);
            
            primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	/** käynnistetään ohjelman käyttöliittymä
	 * @param args parametrit
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
