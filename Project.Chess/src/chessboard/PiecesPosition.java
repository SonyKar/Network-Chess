package chessboard;

import main.Color;
import pieces.*;

public class PiecesPosition {
    public Pieces[][] pieces = {
            {new Rook(Color.PieceColor.BLACK), new Knight(Color.PieceColor.BLACK), new Bishop(Color.PieceColor.BLACK), new Queen(Color.PieceColor.BLACK), new King(Color.PieceColor.BLACK), new Bishop(Color.PieceColor.BLACK), new Knight(Color.PieceColor.BLACK), new Rook(Color.PieceColor.BLACK)},
            {new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK), new Pawn(Color.PieceColor.BLACK)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE), new Pawn(Color.PieceColor.WHITE)},
            {new Rook(Color.PieceColor.WHITE), new Knight(Color.PieceColor.WHITE), new Bishop(Color.PieceColor.WHITE), new Queen(Color.PieceColor.WHITE), new King(Color.PieceColor.WHITE), new Bishop(Color.PieceColor.WHITE), new Knight(Color.PieceColor.WHITE), new Rook(Color.PieceColor.WHITE)}
    };

    public void movePiece (int x, int y, int x1, int y1) {
        pieces[x1][y1] = pieces[x][y];
        pieces[x][y] = null;
        pieces[x1][y1].setMoved();
    }

}
