import javafx.geometry.Insets;
import javafx.scene.control.Label;


class MovesTableCell extends Label {
    public MovesTableCell() {
        this.getStyleClass().add("move-table-cell");
        this.setPrefSize(65, 25);
        this.setPadding(new Insets(5));
    }
}