package csc22100.assignment4.ui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class GameOfLifeController {
    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button stepButton;

    @FXML
    private MenuItem quitItem;

    @FXML
    private GridPane gridPane;

    @FXML
    private Slider slider;

    private GameOfLifeViewModel viewModel;

    public void init(GameOfLifeViewModel viewModel) {
        this.viewModel = viewModel;
        // TODO: attach listeners/bindings that interact with the view model
        slider.valueProperty().addListener((val) -> {
            // TODO: What happens when the value of the slider changes?
        });

        quitItem.setOnAction((ev) -> Platform.exit());

        if (viewModel.getCellStates() == null) {
            throw new IllegalArgumentException();
        }

        initializeGrid();
    }

    private void initializeGrid() {
        int numRows = viewModel.getNumRows();
        int numCols = viewModel.getNumCols();

        NumberBinding allCellSize = Bindings.min(gridPane.widthProperty(), gridPane.heightProperty());
        for (int row = 0; row < viewModel.getNumRows(); row++) {
            for (int col = 0; col < viewModel.getNumCols(); col++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setStroke(Color.DARKGRAY);
                rectangle.setStrokeType(StrokeType.INSIDE);
                rectangle.widthProperty().bind(Bindings.divide(allCellSize, numCols));
                rectangle.heightProperty().bind(Bindings.divide(allCellSize, numRows));
                int finalRow = row;
                int finalCol = col;
                rectangle.fillProperty().bind(Bindings.createObjectBinding(
                        () -> viewModel.getCellState(finalRow, finalCol) ? Color.BLACK : Color.WHITE,
                        viewModel.getCellStates()));
                gridPane.add(rectangle, row, col, 1, 1);
            }
        }
    }
}
