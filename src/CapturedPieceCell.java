import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class CapturedPieceCell extends Label {
    public CapturedPieceCell(String pieceCss) {
        this.setPrefSize(40, 40);
        this.getStyleClass().addAll(pieceCss, "icon-size");
        this.setPadding(new Insets(5));
    }
}
