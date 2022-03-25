package pieces;

import main.Color;

public class Pawn extends Pieces {

    public Pawn(Color.PieceColor color) {
        super(color);
        icon.setContent("M170.07,190.338v25.724H45.992v-25.724H170.07z M63.23,181.176h89.604v-25.723H63.23V181.176z M76.849,80.986v64.926   h62.368V80.986c9.41-8.505,15.344-20.779,15.344-34.454c0-25.7-20.823-46.533-46.529-46.533S61.499,20.833,61.499,46.533   C61.499,60.213,67.442,72.481,76.849,80.986z");
        setName("pawn");
    }
}
