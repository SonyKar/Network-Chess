package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void MainMenu (Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("../main_menu/main_menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Chess. Main Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(670);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenu(primaryStage);
    }


    public static void main(String[] args) {launch(args);}
}
