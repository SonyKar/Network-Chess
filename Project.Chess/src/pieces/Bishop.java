package pieces;

import main.Color;

public class Bishop extends Pieces {

    public Bishop(Color.PieceColor color) {
        super(color);
        icon.setContent("M175.827,200.452v25.966H50.59v-25.966H175.827z M158.438,166.801h-13.748v-55.218h13.748V85.615h-16.893 c5.627-6.366,9.066-14.709,9.066-23.871C150.611,41.807,114.506,0,114.506,0S78.415,41.807,78.415,61.744   c0,9.171,3.443,17.517,9.073,23.871H67.973v25.969h13.746v55.218H67.973v25.973h90.464V166.801L158.438,166.801z");
        setName("bishop");
    }
}
