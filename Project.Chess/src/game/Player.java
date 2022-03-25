package game;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import main.Color;
import pieces.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Player {
    private Socket socket;
    private OutputStreamWriter osr;
    private Scanner read;
    private boolean isYourTurn = false;
    private Color.PieceColor color;
    private Game game;
    private boolean isOnline = true;

    public Player(Game game) {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 8080);
            osr = new OutputStreamWriter(socket.getOutputStream());
            read = new Scanner(socket.getInputStream());
            this.game = game;
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while(true) {
                if (read.hasNext()) {
                    String respond = read.nextLine();
                    System.out.println(respond);

                    switch (respond) {
                        case "disconnected" -> {
                            disconnect();
                            Platform.runLater(() -> game.gameInfo.setText("Another player has disconnected!"));
                        }
                        case "surrender" -> {
                            disconnect();
                            Platform.runLater(() -> game.gameInfo.setText("Victory!"));
                        }
                        case "WHITE", "BLACK", "connected" -> {
                            if (color == null) setColor(Color.PieceColor.valueOf(respond));
                            if (color == Color.PieceColor.WHITE) {
                                isYourTurn = true;
                                Platform.runLater(() -> game.gameInfo.setText("It is your turn!"));
                            } else {
                                Platform.runLater(() -> game.gameInfo.setText("Waiting for another player's turn..."));
                            }
                        }
                        default -> {
                            String[] tmp = respond.split(" ");
                            int i = 0;

                            int castlingType = 0;
                            Pieces selectedPiece = null;

                            for (int j = 0; j < 2; j++) {
                                switch (tmp[j]) {
                                    case "Check", "Checkmate" -> i++;
                                    case "shortCastling" -> { castlingType = 1; i++; }
                                    case "longCastling" -> { castlingType = 2; i++; }
                                    case "bishop" -> {
                                        selectedPiece = new Bishop(getOpponentsColor());
                                        i++;
                                    }
                                    case "queen" -> {
                                        selectedPiece = new Queen(getOpponentsColor());
                                        i++;
                                    }
                                    case "knight" -> {
                                        selectedPiece = new Knight(getOpponentsColor());
                                        i++;
                                    }
                                    case "rook" -> {
                                        selectedPiece = new Rook(getOpponentsColor());
                                        i++;
                                    }
                                }
                            }

                            int x = Integer.parseInt(tmp[i++]), y = Integer.parseInt(tmp[i++]),
                                    x1 = Integer.parseInt(tmp[i++]), y1 = Integer.parseInt(tmp[i]);

                            StackPane stackPaneFrom = (StackPane)game.chessboard.getChildren().get(x * 8 + y);
                            StackPane stackPaneTo = (StackPane)game.chessboard.getChildren().get(x1 * 8 + y1);

                            if (castlingType == 1) {
                                int rookPosition = y1 + 1;
                                int rookFinalPosition = y1 - 1;
                                Platform.runLater(() -> game.castlingProcessing(x, game.chessboard, x1, rookPosition, rookFinalPosition));
                            } else if (castlingType == 2) {
                                int rookPosition = 0;
                                int rookFinalPosition = y1 + 1;
                                Platform.runLater(() -> game.castlingProcessing(x, game.chessboard, x1, rookPosition, rookFinalPosition));
                            } else if (selectedPiece != null) {
                                game.pieces[x][y] = selectedPiece;
                                Pieces finalSelectedPiece = selectedPiece;
                                Platform.runLater(() -> game.movePieceOnChessboard(stackPaneFrom, stackPaneFrom, finalSelectedPiece));
                            }

                            game.movePiece(x, y, x1, y1);

                            Platform.runLater(() -> game.movePieceOnChessboard(stackPaneFrom, stackPaneTo, game.pieces[x1][y1]));

                            if (tmp[0].equals("Check") || tmp[0].equals("Checkmate")) {
                                Platform.runLater(() -> game.gameInfo.setText(tmp[0]));
                            } else {
                                Platform.runLater(() -> game.gameInfo.setText("It is your turn!"));
                            }

                            toggleTurn();
                        }
                    }
                }
            }
        }).start();
    }

    public void setColor (Color.PieceColor color) {
        this.color = color;
    }

    private Color.PieceColor getOpponentsColor() {
        return color == Color.PieceColor.BLACK ? Color.PieceColor.WHITE : Color.PieceColor.BLACK;
    }

    public void sendMessage(String message) {
        try {
            osr.write(message + "\n");
            osr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleTurn() {
        isYourTurn = !isYourTurn;
    }

    public boolean checkYourTurn() {
        return isYourTurn;
    }

    public Color.PieceColor getColor () {
        return color;
    }

    public boolean checkConnection() {
        return isOnline;
    }

    public void disconnect () {
        isOnline = false;
        socket = null;
        isYourTurn = false;
        Platform.runLater(() -> {
            for (int i = 0; i <= 3; i++) {
                game.buttons.getChildren().get(i).setDisable(true);
            }
            game.buttons.getChildren().get(4).setVisible(true);
        });
    }
}
