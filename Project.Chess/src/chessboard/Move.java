package chessboard;

public class Move {
    private final int x;
    private final int y;
    private final int x1;
    private final int y1;
    private short castlingType = 0; // 0 - no castling, 1 - short castling, 2 - long castling
    private String selectedPieceFromModal = "";

    public Move (int x, int y, int x1, int y1, short castlingType) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.castlingType = castlingType;
    }

    public Move (int x, int y, int x1, int y1, String selectedPieceFromModal) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.selectedPieceFromModal = selectedPieceFromModal;
    }

    public String getMove () {
        String move = "";
        if (castlingType == 1) {
            move = "shortCastling ";
        } else if (castlingType == 2) {
            move = "longCastling ";
        } else if (!selectedPieceFromModal.equals("")) {
            move = selectedPieceFromModal + " ";
        }

        move += x + " " + y + " " + x1 + " " + y1;

        return move;
    }
}
