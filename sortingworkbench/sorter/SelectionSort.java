package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;

public class SelectionSort extends BaseSort {

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics) {

    }
}
