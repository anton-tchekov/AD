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

		List<T> testList = new LinkedList<T>();

		testList.add((T) "Hi");
		testList.add((T) "Bye");
		testList.add((T) "Hi2");
		testList.add((T) "Bye2");
		testList.add((T) "Wassup");
		atomicLists.add(testList);

		split(atomicLists, 0);
	}

	private <T extends Comparable<T>> void split(List<List<T>> atomicLists, int atList)
	{
		// Keep Track of the current list
		List<T> currentList;

		// Split the atomiclists further
		currentList = atomicLists.get(atList);
		atList = (atList + 1) % atomicLists.size();

		System.out.println(atomicLists.toString()); 

		if(allSizeOne(atomicLists)) 
		{
			System.out.println("done");

			System.out.println(atomicLists.toString()); 

			return;
		}
		else if(currentList.size() == 1)
		{
			System.out.println("Found Atomic");
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

