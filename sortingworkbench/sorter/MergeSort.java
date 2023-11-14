package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.LinkedList;
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
		// Result of the Splitting
		List<List<T>> atomicLists = new LinkedList<List<T>>();

		List<T> testList = new LinkedList<T>();

		atomicLists.add(toSort);

		split(atomicLists, toSort, metrics, 0);
		System.out.println("done splitting");
		merge(atomicLists, toSort, metrics);
		System.out.println("done merging");

		System.out.println(atomicLists.size());

		updateToSort(atomicLists, toSort, 1);
	}

	private <T extends Comparable<T>> void merge(List<List<T>> atomicLists, List<T> toSort, SortingMetrics metrics)
	{
		int leftIndex = 0;
		int rightIndex = 0;

		int leftCurrent = 0;
		int rightCurrent = 0;

		while(atomicLists.size() > 1)
		{
			leftIndex = 0;
			rightIndex = 1;

			while(atomicLists.get(leftIndex).size() > 0)
			{
				if(atomicLists.get(leftIndex).get(leftCurrent).compareTo(atomicLists.get(rightIndex).get(rightCurrent)) <= 0)
				{
					metrics.incrementCompares();
					atomicLists.get(rightIndex).add(rightCurrent, atomicLists.get(leftIndex).get(leftCurrent));
					metrics.incrementMoves();
					atomicLists.get(leftIndex).remove(leftCurrent);
					metrics.incrementMoves();
				}
				else
				{	 
					rightCurrent++;
					if(rightCurrent == atomicLists.get(rightIndex).size())
					{
						atomicLists.get(rightIndex).add(atomicLists.get(leftIndex).get(leftCurrent));
						metrics.incrementMoves();
						atomicLists.get(leftIndex).remove(leftCurrent);
						metrics.incrementMoves();
					}
				}
			}
			atomicLists.remove(leftIndex); 
			metrics.incrementMoves();
			updateToSort(atomicLists, toSort, 0);
			rightCurrent = 0;
			leftCurrent = 0;
		}
	}

	private <T extends Comparable<T>> void split(List<List<T>> atomicLists, List<T> toSort, SortingMetrics metrics, int atList)
	{
		// Keep Track of the current list
		List<T> currentList;

		while(!allSizeOne(atomicLists))
		{
			// Split the atomiclists further
			currentList = atomicLists.get(atList);
			atList = (atList + 1) % atomicLists.size();

			if(currentList.size() == 1)
			{
				//System.out.println("Found Atomic");
			}
			else
			{
				int length = currentList.size();
				int half_length = length / 2;

				atomicLists.add(new LinkedList<T>());
				atomicLists.add(new LinkedList<T>());
			
				for(int i = 0; i < half_length; i++) 
				{
					atomicLists.get(atomicLists.size() - 2).add(currentList.get(i));
					metrics.incrementMoves();
				}

				for(int i = half_length; i < length; i++) 
				{
					atomicLists.get(atomicLists.size() - 1).add(currentList.get(i));
					metrics.incrementMoves();
				}
				
				atomicLists.remove(currentList);
				metrics.incrementMoves();
				updateToSort(atomicLists, toSort, 0);
			}
		}
	}

	private <T extends Comparable<T>> boolean allSizeOne(List<List<T>> atomicLists)
	{
		for(int i = 0; i < atomicLists.size(); i++)
		{
			if(atomicLists.get(i).size() > 1)
			{
				return false;
			}
		}

		return true;
	}

	int testVar = 0;

	private <T extends Comparable<T>> void updateToSort(List<List<T>> atomicLists, List<T> toSort, int overwrite)
	{
		int atElement = 0;

		if(testVar == 5 || overwrite == 1)
		{
			for(int i = 0; i < atomicLists.size(); i++)
			{
				for(int j = 0; j < atomicLists.get(i).size(); j++)
				{
					toSort.add(atElement, atomicLists.get(i).get(j));
					atElement++;
				}
			}

			testVar = 0;
		}
		else
		{
			testVar++;
		}
	}
}


