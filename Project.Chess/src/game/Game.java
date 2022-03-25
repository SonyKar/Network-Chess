package game;

import chessboard.Chessboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Main;

public class Game extends Chessboard {

    @FXML
    public GridPane chessboard;
    @FXML
    public HBox game;
    @FXML
    public VBox buttons;
    @FXML
    public Label gameInfo;

    public static Player player;

    public void onChangeSizeHandler() {
        double width;
        double height;
        if (((Stage)game.getScene().getWindow()).isMaximized()) {
            width = Screen.getPrimary().getVisualBounds().getMaxX() / 1.8 - buttons.getWidth() - 50;
            height = Screen.getPrimary().getVisualBounds().getMaxX() / 1.8 - 50 - gameInfo.getHeight();
        } else {
            width = game.getScene().getWindow().getWidth() - buttons.getWidth() - 50;
            height = game.getScene().getWindow().getHeight() - 50 - gameInfo.getHeight();
        }
        double min = Math.min(width, height);
        min /= 8;

//        ChessboardSquares resize
        chessboardResize(chessboard, min, min);

        for (int i = 0; i < 64; i++) {
//            StackPane resize (SVG images containers)
            squareResize((StackPane)chessboard.getChildren().get(i), min, min);

//            Region resize (SVG images)
            int row = i / 8;
            int col = i - (row * 8);
            if (pieces[row][col] != null) {
                regionResize((Region)((StackPane)chessboard.getChildren().get(i)).getChildren().get(0), min / 2, min - 15);
            }
        }
    }

//    Buttons' handlers
    public void endTurnHandler(ActionEvent event) {
        if (move != null) {
            gameInfo.setText("Waiting for another player's turn...");
            String buttonText = ((Button)event.getSource()).getText();
            if (buttonText.equals("End Turn")) {
                Game.player.sendMessage(move.getMove());
            } else {
                Game.player.sendMessage(buttonText + " " + move.getMove());
            }
            move = null;
        }
    }

    public void surrenderHandler() {
        gameInfo.setText("Defeat!");
        player.disconnect();
        Game.player.sendMessage("surrender");
    }

    public void closeGameHandler(ActionEvent event) {
        Main.MainMenu(new Stage());
        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        player = new Player(this);
        initializeChessboard(chessboard);
    }
}
