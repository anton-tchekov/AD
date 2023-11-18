package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort extends BaseSort
{
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
		return "QuickSort Basic";
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

			int pivotIdx = ThreadLocalRandom.current().nextInt(start, end);
			T pivot = toSort.get(pivotIdx);

			swap(toSort, pivotIdx, end, metrics);

			int s = start;
			int e = end - 1;

			while(s < e)
			{
				while(s < e && toSort.get(s).compareTo(pivot) <= 0)
				{
					metrics.incrementCompares();
					++s;
				}

				while(e > s && toSort.get(e).compareTo(pivot) > 0)
				{
					metrics.incrementCompares();
					--e;
				}

				if(toSort.get(s).compareTo(toSort.get(e)) > 0)
				{
					swap(toSort, s, e, metrics);
				}
			}

			if(toSort.get(s).compareTo(pivot) > 0)
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
