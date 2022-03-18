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


/**
 * The "View" of our application.
 */
public class GameOfLifeController {
    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    Button resetButton;

    @FXML
    Button stepButton;

    @FXML
    MenuItem quitItem;

    @FXML
    GridPane gridPane;

    @FXML
    Slider slider;

    private GameOfLifeViewModel viewModel;

    /**
     * Initialize the controller based on the view model
     *
     * @param viewModel the {@link GameOfLifeViewModel} with which to bind
     */
    public void init(GameOfLifeViewModel viewModel) {
        this.viewModel = viewModel;

        quitItem.setOnAction((ev) -> Platform.exit());

        // TODO: setOnAction for the buttons.
        startButton.setOnAction((ev) -> viewModel.start());

        // TODO: Use viewModel.isRunning() to bind to the buttons' disableProperty
        // to enable/disable them as appropriate.
        // Hint: Remember the Bindings class contains helpers to transform one Binding to another

        // TODO: Also attach a *listener* to tell the viewmodel to
        // update the sleep interval in the LifeService when the value changes
        slider.valueProperty().bindBidirectional(viewModel.sleepIntervalSeconds());

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
