package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage homeStage;

	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			Image icon = new Image("view/resourses/USER.png");
			primaryStage.getIcons().add(icon);
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/gui/Home.fxml"));
			Main.homeStage = primaryStage;
			
			Scene scene = new Scene(parent);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Loja do Tadeu");
			primaryStage.show();				
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getHomeStage() {
		return homeStage;
	}
	
}

