package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;

public class UltimateSort extends BaseSort {

    @Override
    public String getName() {
        return "Ultimate Sort";
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> toSort,
        SortingMetrics metrics)
    {
		int[] arr = new int[800];
		int n = toSort.size();
		for(int i = 0; i < n; ++i)
		{
			int val = (Integer)toSort.get(i);
			++arr[val];
		}

		int pos = 0;
		for(int i = 0; i < n; ++i)
		{
			int cnt = arr[i];
			for(int j = 0; j < cnt; ++j)
			{
				Integer val = i;
				toSort.set(pos, (T)val);
				++pos;
				metrics.incrementMoves();
			}
		}
    }
}
