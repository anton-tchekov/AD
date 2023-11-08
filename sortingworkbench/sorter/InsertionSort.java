package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;

public class InsertionSort extends BaseSort
{
	@Override
	public String getName()
	{
		return "Insertion Sort";
	}

	@Override
	public <T extends Comparable<T>> void sort(List<T> toSort,
		SortingMetrics metrics)
	{

	}
}
