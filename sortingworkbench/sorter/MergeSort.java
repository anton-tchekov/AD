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

		testList.add((T) (Integer)1);
		testList.add((T) (Integer)66);
		testList.add((T) (Integer)84);
		testList.add((T) (Integer)4);
		testList.add((T) (Integer)53);
		testList.add((T) (Integer)23);
		atomicLists.add(testList);

		split(atomicLists, 0);
		merge(atomicLists);
	}

	private <T extends Comparable<T>> void merge(List<List<T>> atomicLists)
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
					atomicLists.get(rightIndex).add(rightCurrent, atomicLists.get(leftIndex).get(leftCurrent));
					atomicLists.get(leftIndex).remove(leftCurrent);
				}
				else
				{	 
					rightCurrent++;
					if(rightCurrent == atomicLists.get(rightIndex).size())
					{
						atomicLists.get(rightIndex).add(atomicLists.get(leftIndex).get(leftCurrent));
						atomicLists.get(leftIndex).remove(leftCurrent);
					}
				}
			}
			atomicLists.remove(leftIndex); 
			rightCurrent = 0;
			leftCurrent = 0;
		}
	}

	private <T extends Comparable<T>> void split(List<List<T>> atomicLists, int atList)
	{
		// Keep Track of the current list
		List<T> currentList;

		// Split the atomiclists further
		currentList = atomicLists.get(atList);
		atList = (atList + 1) % atomicLists.size();

		if(allSizeOne(atomicLists)) 
		{
			return;
		}
		else if(currentList.size() == 1)
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
			}

			for(int i = half_length; i < length; i++) 
			{
				atomicLists.get(atomicLists.size() - 1).add(currentList.get(i));
			}
			
			atomicLists.remove(currentList);
		}
		split(atomicLists, atList);
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
}

