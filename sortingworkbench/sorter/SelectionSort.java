package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;

public class SelectionSort extends BaseSort
{
	@Override
	public String getName()
	{
		return "Selection Sort";
	}

	@Override
	public <T extends Comparable<T>> void sort(List<T> toSort,
		SortingMetrics metrics)
	{
		int n = toSort.size();
		for(int i = 0; i < n; ++i)
		{
			int minIdx = i;
			T first = toSort.get(i);
			T min = first;
			for(int j = i + 1; j < n; ++j)
			{
				T cur = toSort.get(j);
				metrics.incrementCompares();
				if(cur.compareTo(min) < 0)
				{
					minIdx = j;
					min = cur;
				}
			}

			toSort.set(i, min);
			toSort.set(minIdx, first);
			metrics.incrementMoves();
		}
	}
}
