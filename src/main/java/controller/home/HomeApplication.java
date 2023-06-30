package controller.home;

import java.io.IOException;
import java.util.ArrayList;

import controller.report.hrmanager.unitworkerattendance.HRMUnitWorkerAttendanceReportController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.employee.worker.Worker;

import static assets.navigation.FXMLNavigation.*;

public class HomeApplication extends Application {

	@Override
	public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(LOGIN_VIEW));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HubStaff Login");
        stage.setScene(scene);
        stage.show();
	}
    public static void main(String[] args) {
    	
    	launch(args);
    }
}
