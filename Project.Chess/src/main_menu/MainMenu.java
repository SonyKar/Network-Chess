package main_menu;

import game.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Color;

import java.io.IOException;
import java.util.Random;

public class MainMenu {
    @FXML
    public Button startGameButton;
    @FXML
    public Button findGameButton;
    @FXML
    public Button whiteButton;
    @FXML
    public Button blackButton;
    @FXML
    public Button backButton;
    @FXML
    public Button exitButton;

    public void startGameButtonHandler (ActionEvent event) {
        startGameScene((Button)event.getSource());
    }

    public void findGameButtonHandler (ActionEvent event) {
        startGame((Button)event.getSource(), null);
    }

    public void chooseColorButtonHandler (ActionEvent event) {
        if (((Button)event.getSource()).getText().equals("White")) {
            startGame((Button)event.getSource(), Color.PieceColor.WHITE);
        } else {
            startGame((Button)event.getSource(), Color.PieceColor.BLACK);
        }
    }

    public void randomColorButtonHandler (ActionEvent event) {
        Color.PieceColor color;
        if (new Random().nextBoolean()) {
            color = Color.PieceColor.WHITE;
        } else {
            color = Color.PieceColor.BLACK;
        }
        startGame((Button)event.getSource(), color);
    }

    public void backButtonHandler (ActionEvent event) {
        mainMenuScene((Button)event.getSource());
    }

    public void exitButtonHandler () {
        System.exit(0);
    }

    private void startGame (Button button, Color.PieceColor color) {
        try {
            chessboard(color);
            ((Stage)button.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chessboard (Color.PieceColor color) throws Exception {
        Stage subStage = new Stage();
        FXMLLoader chessBoardLoader = new FXMLLoader(this.getClass().getResource("../game/game.fxml"));
        Parent chessBoardForm = chessBoardLoader.load();

        // set up scene and set css file
        Scene scene = new Scene(chessBoardForm);
        scene.getStylesheets().add(this.getClass().getResource("/chessboard/Chessboard.css").toExternalForm());
        scene.getStylesheets().add(this.getClass().getResource("/main_menu/MainMenu.css").toExternalForm());

//        Setting title and scene in stage
        subStage.setTitle("Chess. The Game");
        subStage.setScene(scene);
        subStage.setMinWidth(760);
        subStage.setMinHeight(550);

//        Setting listeners
        subStage.widthProperty().addListener((observableValue, number, t1) -> ((Game) chessBoardLoader.getController()).onChangeSizeHandler());
        subStage.heightProperty().addListener((observableValue, number, t1) -> ((Game) chessBoardLoader.getController()).onChangeSizeHandler());
        subStage.maximizedProperty().addListener((ov, t, t1) -> ((Game) chessBoardLoader.getController()).onChangeSizeHandler());
        subStage.setOnCloseRequest(event -> {
            if (Game.player.checkConnection()) {
                Game.player.sendMessage("disconnected");
            }
            System.exit(1);
        });

        if (color != null) {
            Game.player.setColor(color);
            Game.player.sendMessage(color.toString());
        }

//        subStage.setMaximized(true);
        subStage.show();
    }

    private void startGameScene (Button button) {
        Scene scene;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("start_game.fxml")));
            ((Stage)button.getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mainMenuScene (Button button) {
        Scene scene;
        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("main_menu.fxml")));
            ((Stage)button.getScene().getWindow()).setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
