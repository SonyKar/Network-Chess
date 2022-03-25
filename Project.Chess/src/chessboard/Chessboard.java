package chessboard;

import game.Game;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.Color;
import pieces.*;

import java.io.IOException;
import java.nio.file.Paths;

public class Chessboard extends PiecesPosition{

    private final AudioClip hitSound = new AudioClip(Paths.get("src/sounds/hit.wav").toUri().toString());
    protected Move move = null;
    protected Pieces selectedPieceFromModal = null;

    protected void initializeChessboard(GridPane chessboard) {
        final int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = new StackPane();
                String color;
                if ((row + col) % 2 == 0) {
                    color = "whiteSquare";
                } else {
                    color = "blackSquare";
                }
                square.getStyleClass().add(color);

                if (pieces[row][col] != null) {
                    pieces[row][col].wrapPiece(square);
                }
                
                square.setOnDragDetected(mouseEvent -> onDragDetectedHandler(mouseEvent, square));
                square.setOnDragOver(this::onDragOverHandler);
                square.setOnDragDropped(dragEvent -> onDragDroppedHandler(dragEvent, square));
                square.setOnDragEntered(dragEvent -> onDragEnteredHandler(square));
                square.setOnDragExited(dragEvent -> onDragExitedHandler(square));
                square.setOnDragDone(dragEvent -> onDragDoneHandler(dragEvent, square));

                chessboard.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            chessboard.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, 100, Priority.ALWAYS, HPos.CENTER, true));
            chessboard.getRowConstraints().add(new RowConstraints(50, Control.USE_COMPUTED_SIZE, 100, Priority.SOMETIMES, VPos.CENTER, true));
        }
    }
    public void movePieceOnChessboard (StackPane from, StackPane to, Pieces piece) {
        double width = ((Region)from.getChildren().get(0)).getPrefWidth();
        double height = ((Region)from.getChildren().get(0)).getPrefHeight();
        from.getChildren().clear();
        to.getChildren().clear();
        piece.wrapPiece(to);
        regionResize((Region)to.getChildren().get(0), width, height);
    }
    private void selectPieceModal(Window window, Color.PieceColor color) {
        Stage stage = new Stage();
        FXMLLoader modalLoader = new FXMLLoader(Chessboard.class.getResource("SelectPieceModal.fxml"));
        Parent root;
        try {
            root = modalLoader.load();
            ((SelectPieceModal)modalLoader.getController()).getController(this, color);
            stage.setScene(new Scene(root));
            stage.setTitle("Select a Piece");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setOnCloseRequest(windowEvent -> {
                if (selectedPieceFromModal == null) {
                    windowEvent.consume();
                }
            });
            stage.initOwner(window);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void castlingProcessing(int x, GridPane chessboard, int x1, int rookPosition, int rookFinalPosition) {
        movePiece(x, rookPosition, x1, rookFinalPosition);
        StackPane stackPaneRook = (StackPane)chessboard.getChildren().get(x1 * 8 + rookPosition);
        StackPane stackPaneCastling = (StackPane)chessboard.getChildren().get(x1 * 8 + rookFinalPosition);
        movePieceOnChessboard(stackPaneRook, stackPaneCastling, pieces[x1][rookFinalPosition]);
    }

//    Resize methods

    protected void regionResize(Region region, double width, double height) {
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
        region.setMinSize(width, height);
    }

    protected void squareResize(StackPane stackPane, double width, double height) {
        stackPane.setPrefSize(width, height);
        stackPane.setMaxSize(width, height);
        stackPane.setMinSize(width, height);
        if (Game.player.getColor() == Color.PieceColor.BLACK) {
            stackPane.rotateProperty().setValue(180);
        }
    }

    protected void chessboardResize(GridPane chessboard, double width, double height) {
        for (int i = 0; i < 8; i++) {
            chessboard.getColumnConstraints().get(i).setPrefWidth(width);
            chessboard.getColumnConstraints().get(i).setMaxWidth(width);
            chessboard.getColumnConstraints().get(i).setMinWidth(width);
            chessboard.getRowConstraints().get(i).setPrefHeight(height);
            chessboard.getRowConstraints().get(i).setMaxHeight(height);
            chessboard.getRowConstraints().get(i).setMinHeight(height);
        }
        if (Game.player.getColor() == Color.PieceColor.BLACK) {
            chessboard.rotateProperty().setValue(180);
            chessboard.paddingProperty().setValue(new Insets(50, 50, 0, 0));
        } else {
            chessboard.paddingProperty().setValue(new Insets(0, 0, 50, 50));
        }
    }

//    Drag & Drop handlers

    private void onDragDetectedHandler(MouseEvent mouseEvent, StackPane square) {

        int i = GridPane.getRowIndex(square);
        int j = GridPane.getColumnIndex(square);

        if (square.getChildren().isEmpty() || !Game.player.checkYourTurn() || (Game.player.getColor() != pieces[i][j].getColor())) {
            mouseEvent.consume();
        } else {
            Dragboard db = square.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(i + " " + j);

            square.getStyleClass().add("movingPiece");

            db.setContent(content);
        }
    }

    private void onDragOverHandler(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            dragEvent.acceptTransferModes( TransferMode.ANY );
        }

        dragEvent.consume();
    }

    private void onDragDroppedHandler(DragEvent dragEvent, StackPane square) {
        Dragboard db = dragEvent.getDragboard();

        int x = 0, y = 0;
        if (db.hasString()) {
            String[] tmp = db.getString().split(" ");
            x = Integer.parseInt(tmp[0]);
            y = Integer.parseInt(tmp[1]);
        } else {
            dragEvent.consume();
        }

        GridPane chessboard = (GridPane) square.getParent();
        int x1 = GridPane.getRowIndex(square);
        int y1 = GridPane.getColumnIndex(square);

        StackPane stackPaneOrigin = (StackPane)chessboard.getChildren().get(x * 8 + y);

        if (pieces[x1][y1] != null && pieces[x][y].getColor() == pieces[x1][y1].getColor()) {
            dragEvent.consume();
        } else {
            StackPane stackPane = (StackPane)chessboard.getChildren().get(x1 * 8 + y1);

            short castlingType = 0;
            if (pieces[x][y].getName().equals("king")) {
                int rookPosition, rookFinalPosition;
                    castlingType = ((King)pieces[x][y]).castlingType(pieces, x, y, x1, y1);
                if (castlingType == 1) {
                    rookPosition = y1 + 1;
                    rookFinalPosition = y1 - 1;
                    castlingProcessing(x, chessboard, x1, rookPosition, rookFinalPosition);
                } else if (castlingType == 2) {
                    rookPosition = 0;
                    rookFinalPosition = y1 + 1;
                    castlingProcessing(x, chessboard, x1, rookPosition, rookFinalPosition);
                }
            }

            if (pieces[x][y].getName().equals("pawn")) {
                if (pieces[x][y].getColor() == Color.PieceColor.WHITE && x1 == 0
                        || pieces[x][y].getColor() == Color.PieceColor.BLACK && x1 == 7) {
                    selectPieceModal(square.getScene().getWindow(), pieces[x][y].getColor());
                    pieces[x][y] = selectedPieceFromModal;
                    movePieceOnChessboard(stackPaneOrigin, stackPaneOrigin, selectedPieceFromModal);
                }
            }

            movePiece(x, y, x1, y1);

            if (selectedPieceFromModal != null) {
                move = new Move(x, y, x1, y1, selectedPieceFromModal.getName());
            } else {
                move = new Move(x, y, x1, y1, castlingType);
            }
            movePieceOnChessboard(stackPaneOrigin, stackPane, pieces[x1][y1]);

            Label gameInfo = ((Label)((VBox)(square.getParent()).getParent()).getChildren().get(0));
            gameInfo.setText("End your turn!");

            hitSound.play();
            Game.player.toggleTurn();
            selectedPieceFromModal = null;
        }
    }

    private void onDragEnteredHandler(StackPane square) {
        if (!square.getStyleClass().contains("movingPiece")) {
            square.getStyleClass().add("bordered-move");
        }
    }

    private void onDragExitedHandler(StackPane square) {
        if (square.getStyleClass().contains("bordered-move")) {
            square.getStyleClass().remove(1);
        }
    }

    private void onDragDoneHandler(DragEvent dragEvent, StackPane square) {
        Dragboard db = dragEvent.getDragboard();

        int x = 0, y = 0;
        if (db.hasString()) {
            String[] tmp = db.getString().split(" ");
            x = Integer.parseInt(tmp[0]);
            y = Integer.parseInt(tmp[1]);
        } else {
            dragEvent.consume();
        }

        StackPane stackPaneOrigin = (StackPane)((GridPane)square.getParent()).getChildren().get(x * 8 + y);
        stackPaneOrigin.getStyleClass().remove(1);
    }
}
