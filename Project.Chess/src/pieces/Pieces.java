package pieces;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import main.Color;

public abstract class Pieces {
    protected SVGPath icon;
    protected Color.PieceColor color;
    protected String name;
    protected boolean moved = false;

    public Pieces(Color.PieceColor color) {
        icon = new SVGPath();
        this.color = color;
    }

    public Color.PieceColor getColor() {
        return color;
    }

    public void wrapPiece(StackPane square) {
        square.getChildren().add(showPiece());
    }

    private Region showPiece() {
        Region region = new Region();
        region.setShape(icon);
        String className;
        if (color == Color.PieceColor.BLACK) {
            className = "blackPiece";
        } else {
            className = "whitePiece";
        }
        region.getStyleClass().add(className);

        return region;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public boolean isNotMoved() {
        return !moved;
    }

    public void setMoved() {
        this.moved = true;
    }
}
