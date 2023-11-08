package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;
import java.util.SplittableRandom;

public class QuickSort extends BaseSort
{
	@Override
	public String getName()
	{
		return "QuickSort Basic";
	}

	@Override
	public <T extends Comparable<T>> void sort(List<T> toSort,
		SortingMetrics metrics)
	{

	}
}
