package controller.importdata.excel;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static assets.navigation.FXMLNavigation.*;
public class Main extends Application {
	@Override
		public void start (Stage stage ) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(EXCEL_IMPORT_VIEW));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Choose file");
        stage.setScene(scene);
        stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
