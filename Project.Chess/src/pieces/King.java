package pieces;

import main.Color;

public class King extends Pieces {
    public King(Color.PieceColor color) {
        super(color);
        icon.setContent("M28.901,37.11H8.209v-4.289h20.691V37.11z M26.028,0l-3.84,2.771L18.438,0l-3.834,2.771L11.083,0v8.043h14.945V0z    M26.028,13.723v-4.29H11.083v4.29h2.271v13.604h-2.271v4.29h14.945v-4.29h-2.271V13.723H26.028z");
        setName("king");
    }

    public short castlingType (Pieces[][] pieces, int x, int y, int x1, int y1) {
        if (y1 == 6 && pieces[x1][y1 + 1].getName().equals("rook") && pieces[x][y].getColor() == pieces[x1][y1 + 1].getColor()) {
            for (int i = y + 1; i <= y1; i++) {
                if (pieces[x1][i] != null) return 0;
            }
            if (pieces[x][y].isNotMoved() && pieces[x1][y1 + 1].isNotMoved()) {
                return 1;
            }
        } else if (y1 == 2 && pieces[x1][0].getName().equals("rook") && pieces[x][y].getColor() == pieces[x1][0].getColor()) {
            for (int i = y - 1; i > 0; i--) {
                if (pieces[x1][i] != null) return 0;
            }
            if (pieces[x][y].isNotMoved() && pieces[x1][0].isNotMoved()) {
                return 2;
            }
        }
        return 0;
    }
}
