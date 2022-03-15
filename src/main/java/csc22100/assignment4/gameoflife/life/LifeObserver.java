package csc22100.assignment4.gameoflife.life;

import csc22100.assignment4.gameoflife.Position;

import java.util.Map;

public interface LifeObserver {
    void receiveNotification(Map<Position, Boolean> cellStates);
}
