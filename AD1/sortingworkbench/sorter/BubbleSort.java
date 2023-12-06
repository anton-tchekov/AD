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
		assert toSort != null;
		assert metrics != null;

		boolean repeat = true;
		int n = toSort.size();
		while(repeat)
		{
			repeat = false;
			for(int cur = 1; cur < n; ++cur)
			{
				int prev = cur - 1;
				if(compare(toSort, cur, prev, metrics) < 0)
				{
					swap(toSort, cur, prev, metrics);
					repeat = true;
				}
			}
		}
	}
}
