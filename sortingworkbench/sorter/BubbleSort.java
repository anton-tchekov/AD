package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;

public class BubbleSort extends BaseSort
{
	@Override
	public String getName()
	{
		return "Bubble Sort";
	}

	@Override
	public <T extends Comparable<T>> void sort(List<T> toSort,
		SortingMetrics metrics)
	{
		boolean repeat = true;
		int n = toSort.size();
		while(repeat)
		{
			repeat = false;
			for(int curIdx = 1; curIdx < n; ++curIdx)
			{
				int prevIdx = curIdx - 1;
				T prev = toSort.get(prevIdx);
				T cur = toSort.get(curIdx);
				metrics.incrementCompares();
				if(cur.compareTo(prev) < 0)
				{
					toSort.set(curIdx, prev);
					toSort.set(prevIdx, cur);
					metrics.incrementMoves();
					repeat = true;
				}
			}
		}
	}
}
