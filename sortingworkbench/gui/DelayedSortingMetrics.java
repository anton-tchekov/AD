package sortingworkbench.gui;

import sortingworkbench.SortingMetrics;

public class DelayedSortingMetrics extends SortingMetrics {

    /**
     * The ms to wait after <code>delayStep</code> number of swaps
     * have been done.
     */
    private final int delay;

    /**
     * The number of swaps to execute
     * before waiting <code>delay</code> milliseconds.
     */
    private final int delayStep;


    public DelayedSortingMetrics(ListType listType, int delay, int delayStep) {
        super(listType);
        this.delay = delay;
        this.delayStep = delayStep;
    }

    public int getDelay() {
        return delay;
    }

    public int getDelayStep() {
        return delayStep;
    }

    @Override
    public void incrementMoves() {
        if (this.getNumMoves() % this.getDelayStep() == 0) {
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                // Fine for us...
            }
        }

        super.incrementMoves();
    }
}
