package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.ArrayList;
import java.util.List;

public class MergeSort extends BaseSort
{
	@Override
	public String getName()
	{
		return "MergeSort";
	}

	@Override
	public <T extends Comparable<T>> void sort(List<T> toSort,
		SortingMetrics metrics)
	{

	}
}
