package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.List;
import java.util.ArrayList;

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
		assert toSort != null;
		assert metrics != null;
		split(toSort, metrics);
	}

	private <T extends Comparable<T>> void split(List<T> result, SortingMetrics metrics)
	{
		int listLength = result.size();

		if(listLength == 1 || listLength == 0)
		{
			// Done
			return;
		}
		else
		{
			ArrayList<T> leftHalf = new ArrayList<T>();
			ArrayList<T> rightHalf = new ArrayList<T>();

			int halfLength = listLength / 2;

			for(int i = 0; i < halfLength; i++)
			{
				leftHalf.add(result.get(i));
				metrics.incrementMoves();
			}
			for(int i = halfLength; i < result.size(); i++)
			{
				rightHalf.add(result.get(i));
				metrics.incrementMoves();
			}

			split(leftHalf, metrics);
			split(rightHalf, metrics);

			merge(result, leftHalf, rightHalf,metrics);
		}
	}

	private <T extends Comparable<T>> void merge(List<T> combinedList, List<T> leftHalf, List<T> rightHalf, SortingMetrics metrics)
	{
		int leftHalfLength = leftHalf.size();
		int rightHalfLength = rightHalf.size();

		int combinedListIndex = 0;
		int leftHalfIndex = 0;
		int rightHalfIndex = 0;

		// Sort 2 sublists
		while(leftHalfIndex < leftHalfLength && rightHalfIndex < rightHalfLength)
		{
			if (leftHalf.get(leftHalfIndex).compareTo(rightHalf.get(rightHalfIndex)) <= 0)
			{
				combinedList.set(combinedListIndex++, leftHalf.get(leftHalfIndex++));
				metrics.incrementMoves();
			}
			else
			{
				combinedList.set(combinedListIndex++, rightHalf.get(rightHalfIndex++));
				metrics.incrementMoves();
			}
			metrics.incrementCompares();
		}

		while(leftHalfIndex < leftHalfLength)
		{
			combinedList.set(combinedListIndex++, leftHalf.get(leftHalfIndex++));
			metrics.incrementMoves();
		}

		while(rightHalfIndex < rightHalfLength)
		{
			combinedList.set(combinedListIndex++, rightHalf.get(rightHalfIndex++));
			metrics.incrementMoves();
		}
	}
}
