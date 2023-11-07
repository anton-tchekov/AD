package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;

public class BubbleSort extends BaseSort {
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {
        metrics.incrementCompares();
        metrics.incrementMoves();
    }
}
