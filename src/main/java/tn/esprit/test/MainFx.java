package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Rectangle2D;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        try {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();

            Parent root = loader.load();
            Scene scene = new Scene(root );
            primaryStage.setTitle("Posts");
            primaryStage.setScene(scene);
            primaryStage.setWidth(screenBounds.getWidth());
            primaryStage.setHeight(screenBounds.getHeight());
            primaryStage.show();



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
