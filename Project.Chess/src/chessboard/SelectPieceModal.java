package chessboard;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Color;
import pieces.*;

public class SelectPieceModal {
    public Button bishopButton;
    public Button queenButton;
    public Button knightButton;
    public Button rookButton;
    private Chessboard chessboard;
    private Color.PieceColor color;

    protected void getController(Chessboard chessboard, Color.PieceColor color) {
        this.chessboard = chessboard;
        this.color = color;
    }

    public void pieceSelectionHandler(ActionEvent event) {
        switch (((Button) event.getSource()).getText()) {
            case "Bishop" -> chessboard.selectedPieceFromModal = new Bishop(color);
            case "Queen" -> chessboard.selectedPieceFromModal = new Queen(color);
            case "Knight" -> chessboard.selectedPieceFromModal = new Knight(color);
            case "Rook" -> chessboard.selectedPieceFromModal = new Rook(color);
        }

        ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
}
