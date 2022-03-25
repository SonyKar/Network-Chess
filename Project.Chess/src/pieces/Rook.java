package pieces;

import main.Color;

public class Rook extends Pieces {
    public Rook(Color.PieceColor color) {
        super(color);
        icon.setContent("M29.84,35.68h-24v-5.908h24V35.68z M26.54,11.889H9.141v16.098H26.54V11.889z M24.626,0v4.208h-4.36V0h-4.852v4.208h-4.36   V0H6.59v10.244h22.5V0H24.626z");
        setName("rook");
    }
}
