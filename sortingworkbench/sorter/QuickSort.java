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
		Deque<SubRange> stack = new ArrayDeque<SubRange>();
		stack.addFirst(new SubRange(0, toSort.size() - 1));
		while(!stack.isEmpty())
		{
			SubRange range = stack.removeFirst();
			if(range.Start >= range.End)
			{
				continue;
			}

			int i = range.Start;
			int j = range.End - 1;
			T pivot = toSort.get(range.End);
			while(i < j)
			{
				while(i < j && toSort.get(i).compareTo(pivot) <= 0)
				{
					metrics.incrementCompares();
					++i;
				}

				while(j > i && toSort.get(j).compareTo(pivot) > 0)
				{
					metrics.incrementCompares();
					--j;
				}

				if(toSort.get(i).compareTo(toSort.get(j)) > 0)
				{
					swap(toSort, i, j, metrics);
				}
			}

			if(toSort.get(i).compareTo(pivot) > 0)
			{
				metrics.incrementCompares();
				swap(toSort, i, range.End, metrics);
			}
			else
			{
				i = range.End;
			}

			stack.addFirst(new SubRange(range.Start, i - 1));
			stack.addFirst(new SubRange(i + 1, range.End));
		}
	}
}
