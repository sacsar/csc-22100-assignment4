package csc22100.assignment4.gameoflife.life;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import csc22100.assignment4.gameoflife.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link LifeService} class is what our application will use to access the actual
 * {@link Life} object that it's displaying.
 * <p>
 * You shouldn't need to modify this class.
 */
public class LifeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LifeService.class);
    private int numRows;
    private int numCols;
    private Life life;
    private int sleepInterval;
    private int currentSleepMillis;
    private final Thread runnerThread;
    private final LifeRunner runner;
    private final List<LifeObserver> observers = new ArrayList<>();

    /**
     * Instantiate a {@link LifeService}
     */
    public LifeService(Life life, Ticker ticker) {
        this.life = life;
        this.numRows = life.getNumRows();
        this.numCols = life.getNumCols();
        this.sleepInterval = Integer.MAX_VALUE;
        this.currentSleepMillis = Integer.MAX_VALUE;
        this.runner = new LifeRunner(ticker);
        this.runnerThread = new Thread(runner);
        runnerThread.start();
    }

    /**
     * Subscribe to receive notifications from this {@link LifeService}
     * when the cell state changes
     */
    public void subscribe(LifeObserver observer) {
        observers.add(observer);
        observer.receiveNotification(life.getCellStates());
    }

    /**
     * Unsubscribe from notifications from this {@link LifeService}
     */
    public void unsubscribe(LifeObserver observer) {
        observers.remove(observer);
    }

    /**
     * Get the number of rows to display for the simulation
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Get the number of columns to display for the simulation
     */
    public int getNumCols() {
        return numCols;
    }

    public void start(int sleepInterval) {
        this.sleepInterval = sleepInterval;
        currentSleepMillis = sleepInterval * 1000;
        LOGGER.info("Start received with sleep interval {}; currentSleepMillis {}", sleepInterval, currentSleepMillis);
        runnerThread.interrupt();
    }

    public void stop() {
        LOGGER.info("Stop received, setting currentSleepMillis to int.maxvalue");
        currentSleepMillis = Integer.MAX_VALUE;
        runnerThread.interrupt();
    }

    public void step() {
        LOGGER.info("Add step signal received");
        runner.addStep();
        runnerThread.interrupt();
    }

    public void setSleepInterval(int sleepInterval) {
        this.sleepInterval = sleepInterval;
        if (currentSleepMillis < Integer.MAX_VALUE) {
            currentSleepMillis = this.sleepInterval * 1000;
        }
        LOGGER.info("Interval update received; sleepInterval: {} currentSleepMillis: {}", sleepInterval, currentSleepMillis);
        runnerThread.interrupt();
    }

    public Map<Position, Boolean> getState() {
        return life.getCellStates();
    }

    private void notifyObservers() {
        Map<Position, Boolean> cellStates = life.getCellStates();
        observers.forEach(o -> o.receiveNotification(cellStates));
    }

    public void reset() {
        this.life =  new Life(numRows, numCols);
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
                    LOGGER.info("Evolving");
                    life = life.evolve();
                    notifyObservers();
                }
                LOGGER.info("Commencing sleep for {}", currentSleepMillis);
                sleep(currentSleepMillis);
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
                LOGGER.info("Have slept for {}", timeSlept);
                if (timeSlept < currentSleepMillis) {
                    // we haven't slept long enough
                    LOGGER.info("Steps remaining {}", stepsRemaining.get());
                    if (stepsRemaining.get() <= 0) {
                        LOGGER.info("Going back to sleep");
                        // nothing to do, go back to sleep
                        sleep(currentSleepMillis - stopwatch.elapsed(TimeUnit.MILLISECONDS));
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
