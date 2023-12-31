package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSortOpt extends BaseSort
{
	private static final int THRESHOLD = 3;

	class SubRange
	{
		int Start;
		int End;

		SubRange(int start, int end)
		{
			Start = start;
			End = end;
		}
	}

	@Override
	public String getName()
	{
		return "QuickSort Optimized";
	}

	@Override
	public <T extends Comparable<T>> void sort(List<T> toSort,
		SortingMetrics metrics)
	{
		assert toSort != null;
		assert metrics != null;

		Deque<SubRange> stack = new ArrayDeque<SubRange>();
		stack.addFirst(new SubRange(0, toSort.size() - 1));
		while(!stack.isEmpty())
		{
			SubRange range = stack.removeFirst();
			int start = range.Start;
			int end = range.End;
			if(start >= end)
			{
				continue;
			}

			int len = end - start;
			if(len <= THRESHOLD)
			{
				/* Insertion sort */
				for(int i = start + 1; i <= end; ++i)
				{
					int j = i;
					while(j > start && compare(toSort, j - 1, j, metrics) > 0)
					{
						swap(toSort, j, j - 1, metrics);
						--j;
					}
				}
			}
			else
			{
				/* Quicksort */
				int pivot = ThreadLocalRandom.current().nextInt(start, end);
				swap(toSort, pivot, end, metrics);
				int s = start;
				int e = end - 1;
				while(s < e)
				{
					while(s < e && compare(toSort, s, end, metrics) <= 0) { ++s; }
					while(e > s && compare(toSort, e, end, metrics) > 0) { --e; }
					if(compare(toSort, s, e, metrics) > 0)
					{
						swap(toSort, s, e, metrics);
					}
				}

				if(compare(toSort, s, end, metrics) > 0)
				{
					swap(toSort, s, end, metrics);
				}
				else
				{
					s = end;
				}

				stack.addFirst(new SubRange(start, s - 1));
				stack.addFirst(new SubRange(s + 1, end));
			}
		}
	}
}
