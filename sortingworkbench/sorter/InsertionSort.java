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
		assert toSort != null;
		assert metrics != null;

//		int alreadySorted = 1;
//		T currentElement = null;
//
//		/* Go Through the whole list once */
//		for(int listIndex = 0; listIndex < toSort.size(); listIndex++)
//		{
//			currentElement = toSort.get(listIndex);
//
//			/* Sort the current Element into the already Sorted Part */
//			for(int sortedIndex = 0; sortedIndex < alreadySorted; sortedIndex++)
//			{
//				metrics.incrementCompares();
//				if(currentElement.compareTo(toSort.get(sortedIndex)) < 0 || sortedIndex == alreadySorted-1)
//				{
//					/* Found bigger element in the Sorted Part or End of Sorted Part reached, no bigger element found */
//					if(sortedIndex != listIndex)
//					{
//						toSort.remove(listIndex);
//						metrics.incrementMoves();
//						toSort.add(sortedIndex, currentElement);
//						metrics.incrementMoves();
//					}
//
//					alreadySorted++;
//					break;
//				}
//			}
//		}

		int n = toSort.size();
		for(int i = 1; i < n; ++i)
		{
			int j = i;
			while(j > 0 && compare(toSort, j - 1, j, metrics) > 0)
			{
				swap(toSort, j, j - 1, metrics);
				--j;
			}
		}
	}
}
