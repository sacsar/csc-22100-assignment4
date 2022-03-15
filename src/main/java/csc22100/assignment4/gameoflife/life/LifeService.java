package csc22100.assignment4.gameoflife.life;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import csc22100.assignment4.gameoflife.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link LifeService} class is what our application will use to access the actual
 * {@link Life} object that it's displaying.
 *
 * You shouldn't need to modify this class.
 */
public class LifeService {

    private Life life;
    private int sleepInterval;
    private final Thread runnerThread;
    private final LifeRunner runner;
    private final List<LifeObserver> observers = new ArrayList<>();

    public LifeService(Life life, Ticker ticker) {
        this.life = life;
        this.sleepInterval = Integer.MAX_VALUE;
        this.runner = new LifeRunner(ticker);
        this.runnerThread = new Thread(runner);
        runnerThread.start();
    }

    public void subscribe(LifeObserver observer) {
        observers.add(observer);
        observer.receiveNotification(life.getCellStates());
    }

    public void unsubscribe(LifeObserver observer) {
        observers.remove(observer);
    }

    public int getMaxX() {
        return life.getMaxX();
    }

    public int getMaxY() {
        return life.getMaxY();
    }

    public void start(int sleepInterval) {
        this.sleepInterval = sleepInterval;
        runnerThread.interrupt();
    }

    public void stop() {
        this.sleepInterval = Integer.MAX_VALUE;
        runnerThread.interrupt();
    }

    public void step() {
        runner.addStep();
        runnerThread.interrupt();
    }

    public void setSleepInterval(int sleepInterval) {
        this.sleepInterval= sleepInterval;
        runnerThread.interrupt();
    }

    public Map<Position, Boolean> getState() {
        return life.getCellStates();
    }

    private void notifyObservers() {
        Map<Position, Boolean> cellStates = life.getCellStates();
        observers.forEach(o -> o.receiveNotification(cellStates));
    }

    class LifeRunner implements Runnable {
        private final AtomicInteger stepsRemaining = new AtomicInteger(0);
        private final Stopwatch stopwatch;

        public LifeRunner(Ticker ticker) {
            this.stopwatch = Stopwatch.createUnstarted(ticker);
        }

        @Override
        public void run() {
            do {
                if (stepsRemaining.get() > 0) {
                    life = life.evolve();
                    notifyObservers();
                }
                sleep(sleepInterval);
                stepsRemaining.addAndGet(1);
            } while (stepsRemaining.get() > 0);
        }

        void sleep(long toSleep) {
            if (!stopwatch.isRunning()) {
                stopwatch.start();
            }
            try {
                Thread.sleep(toSleep);
            } catch (InterruptedException e) {
                long timeSlept = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                if (timeSlept < sleepInterval) {
                    // we haven't slept long enough
                    if (stepsRemaining.get() <= 0) {
                        // nothing to do, go back to sleep
                        sleep(sleepInterval - stopwatch.elapsed(TimeUnit.MILLISECONDS));
                    } else {
                        // got a step; pause the stopwatch;
                        stopwatch.stop();
                    }
                } else {
                    // slept long enough;
                    stopwatch.reset();
                }
            }
        }

        void addStep() {
            stepsRemaining.addAndGet(1);
        }
    }
}
