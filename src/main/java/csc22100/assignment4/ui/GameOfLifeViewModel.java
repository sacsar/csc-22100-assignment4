package csc22100.assignment4.ui;

import csc22100.assignment4.gameoflife.Position;
import csc22100.assignment4.gameoflife.life.LifeObserver;
import csc22100.assignment4.gameoflife.life.LifeService;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

public class GameOfLifeViewModel implements LifeObserver {

    private LifeService lifeService;

    public GameOfLifeViewModel(LifeService lifeService) {
        this.lifeService = lifeService;
        lifeService.subscribe(this);
    }

    @Override
    public void receiveNotification(Map<Position, Boolean> cellStates) {
        throw new NotImplementedException();
    }
}
