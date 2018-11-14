import javafx.scene.text.Text;

public class BenchmarkTracker {
    private Text benchmarkText;
    private int positionsCalculated;

    BenchmarkTracker(Text benchmarkText, int positionsCalculated) {
        this.benchmarkText = benchmarkText;
        this.benchmarkText.getStyleClass().add("bottom-text-content");
        this.positionsCalculated = positionsCalculated;
    }

    public void updateBenchmark(int positionsCalculated) {
        this.positionsCalculated = positionsCalculated;
        this.benchmarkText.setText("Positions calculated: " + Integer.toString(this.positionsCalculated));
    }

    public Text getBenchmarkText() {
        return this.benchmarkText;
    }

    public int getPositionsCalculated() {
        return this.positionsCalculated;
    }
}
