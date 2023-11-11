package sortingworkbench.sorter;

import sortingworkbench.SortingMetrics;

import java.util.ArrayList;
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

		atomicLists.add(toSort);

		split(atomicLists, 0);
	}

	private <T extends Comparable<T>> void split(List<List<T>> atomicLists, int atList)
	{
		// Keep Track of the current list
		List<T> currentList = new LinkedList<T>();

		// Temporary Storage for each Half of a Split Procedure
		List<T> firstHalf = new LinkedList<T>();
		List<T> secondHalf = new LinkedList<T>();

		// Split the atomiclists further
		currentList = atomicLists.get(atList);
		atList = (atList + 1) % atomicLists.size();

		if(currentList.size() <= 1 && atList == atomicLists.size()) 
		{
			System.out.println("done");
			return;
		}
		else if(currentList.size() <= 1)
		{
			System.out.println("Found Atomic");
		}
		else
		{
			int length = currentList.size();
			int half_length = length / 2;
		
			for(int i = 0; i < half_length; i++) 
			{
				firstHalf.add(currentList.get(i));
			}

			for(int i = half_length; i < length; i++) 
			{
				secondHalf.add(currentList.get(i));
			}

			atomicLists.add(firstHalf);
			atomicLists.add(secondHalf);
			
			atomicLists.remove(firstHalf);
			atomicLists.remove(secondHalf);

			split(atomicLists, atList);
		}
	}

}