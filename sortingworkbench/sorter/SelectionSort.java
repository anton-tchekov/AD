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
		assert toSort != null;
		assert metrics != null;

		int n = toSort.size();
		for(int i = 0; i < n; ++i)
		{
			int min = i;
			for(int j = i + 1; j < n; ++j)
			{
				if(compare(toSort, j, min, metrics) < 0)
				{
					min = j;
				}
			}

			swap(toSort, i, min, metrics);
		}
	}
}
