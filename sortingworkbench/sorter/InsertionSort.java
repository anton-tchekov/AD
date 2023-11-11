package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;
import sortingworkbench.util.ListValidator;

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
		int alreadySorted = 1;
		T currentElement = null;

		/* Go Through the whole list once */
		for(int listIndex = 0; listIndex < toSort.size(); listIndex++)
		{
			currentElement = toSort.get(listIndex);

			/* Sort the current Element into the already Sorted Part */
			for(int sortedIndex = 0; sortedIndex < alreadySorted; sortedIndex++)
			{
				metrics.incrementCompares();
				if(currentElement.compareTo(toSort.get(sortedIndex)) < 0 || sortedIndex == alreadySorted-1)
				{
					/* Found bigger element in the Sorted Part or End of Sorted Part reached, no bigger element found */
					if(sortedIndex != listIndex)
					{
						toSort.remove(listIndex);
						metrics.incrementMoves();
						toSort.add(sortedIndex, currentElement);
						metrics.incrementMoves();
					}

					alreadySorted++;
					break;
				}
			}
		}

		if(ListValidator.validateOrder(toSort))
		{
			System.out.println("Insertion Sort works");
		}
	}
}
