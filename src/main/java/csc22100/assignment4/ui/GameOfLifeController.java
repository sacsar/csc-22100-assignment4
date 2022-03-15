package csc22100.assignment4.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

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

    private GameOfLifeViewModel viewModel;

    public void init(GameOfLifeViewModel viewModel) {
        this.viewModel = viewModel;
        // TODO: attach listeners/bindings that interact with the view model
    }
}
