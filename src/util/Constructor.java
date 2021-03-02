package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Constructor {

	public Stage ContructStage(String path, String title, Image icon) {
		
		Stage stage = new Stage();
		try {
			stage.getIcons().add(icon);
			Parent parent = FXMLLoader.load(getClass().getResource(path));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setTitle(title);
			stage.show();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return stage;
	}

}
