package csc22100.assignment4.ui;

import csc22100.assignment4.gameoflife.Position;
import csc22100.assignment4.gameoflife.life.LifeObserver;
import csc22100.assignment4.gameoflife.life.LifeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

public class GameOfLifeViewModel implements LifeObserver {

    private final LifeService lifeService;
    private final ObservableMap<Position, Boolean> cellStates;

    public GameOfLifeViewModel(LifeService lifeService) {
        cellStates = FXCollections.observableMap(lifeService.getState());
        this.lifeService = lifeService;
        lifeService.subscribe(this);
        cellStates.putAll(lifeService.getState());
    }

    public int getNumRows() {
        return lifeService.getNumRows();
    }

    public void start() {
        // TODO: Implement me
    }

    @Override
    public void receiveNotification(Map<Position, Boolean> cellStates) {
       throw new NotImplementedException();
    }

    public int getNumCols() {
        return lifeService.getNumCols();
    }

    public ObservableMap<Position, Boolean> getCellStates() {
        return cellStates;
    }

    public Boolean getCellState(int finalRow, int finalCol) {
        return cellStates.get(new Position(finalRow, finalCol));
    }
}
