package csc22100.assignment4.ui;

import csc22100.assignment4.gameoflife.Position;
import csc22100.assignment4.gameoflife.life.LifeObserver;
import csc22100.assignment4.gameoflife.life.LifeService;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Map;

/**
 * View model for the {@link GameOfLifeController}.
 */
public class GameOfLifeViewModel implements LifeObserver {

    private final LifeService lifeService;
    private final ObservableMap<Position, Boolean> cellStates;
    private final IntegerProperty sleepIntervalSeconds = new SimpleIntegerProperty(5);
    private final BooleanProperty isRunning = new SimpleBooleanProperty(false);

    /**
     * Instantiate the view model
     * @param lifeService the {@link LifeService} which will power the simulation
     */
    public GameOfLifeViewModel(LifeService lifeService) {
        cellStates = FXCollections.observableMap(lifeService.getState());
        cellStates.putAll(lifeService.getState());
        this.lifeService = lifeService;
        // subscribe to updates from the service
        lifeService.subscribe(this);
    }

    /**
     * Notify the {@link LifeService} that the simulation should start running.
     */
    public void start() {
        lifeService.start(sleepIntervalSeconds.getValue());
        isRunning.setValue(true);
    }

    /**
     * Notify the {@link LifeService} that the simulation should stop.
     */
    public void stop() {
        lifeService.stop();
        isRunning.setValue(false);
    }

    /**
     * Notify the {@link LifeService} to perform one evolution step in the simulation
     */
    public void step() {
        lifeService.step();
    }

    /**
     * Notify the {@link LifeService} that it should reset to a new random state.
     */
    public void reset() {
        lifeService.reset();
    }

    /**
     * Notify the {@link LifeService} to update its sleep interval.
     */
    public void updateSleepInterval() {
        lifeService.setSleepInterval(sleepIntervalSeconds().getValue());
    }

    /**
     * Receive updated cell states from the {@link LifeService}
     * @param cellStates the latest states from the service
     */
    @Override
    public void receiveNotification(Map<Position, Boolean> cellStates) {
        // make sure we don't update the GUI from a non-GUI thread
        Platform.runLater(() -> this.cellStates.putAll(cellStates));
    }

    /**
     * Get the number of rows in the simulation.
     */
    public int getNumRows() {
        return lifeService.getNumRows();
    }


    /**
     * Get the number of columns in the simulation.
     */
    public int getNumCols() {
        return lifeService.getNumCols();
    }


    /**
     * Property for the duration of the interval between steps.
     */
    public IntegerProperty sleepIntervalSeconds() {
        return sleepIntervalSeconds;
    }

    /**
     * Observable map of the cell states.
     */
    public ObservableMap<Position, Boolean> getCellStates() {
        return cellStates;
    }

    /**
     * Lookup a particular cell state.
     */
    public Boolean getCellState(int finalRow, int finalCol) {
        return cellStates.get(new Position(finalRow, finalCol));
    }


    /**
     * Property to indicate whether the simulation is running.
     */
    public BooleanProperty isRunning() {
        return isRunning;
    }
}
