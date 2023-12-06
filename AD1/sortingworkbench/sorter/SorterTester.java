
package sortingworkbench.sorter;

import java.util.List;

import sortingworkbench.DataRetriever;
import sortingworkbench.SortingMetrics;
import sortingworkbench.util.ListValidator;

public class SorterTester
{
	private boolean testSorter(Sorter sorter)
	{
		SortingMetrics metrics = new SortingMetrics(null);
		DataRetriever dr = new DataRetriever();
		List<Integer> list;

		for(int n = 1; n < 100; ++n)
		{
			list = dr.createIntegerList(SortingMetrics.ListType.ORDERED, n);
			sorter.sort(list, metrics);
			if(!ListValidator.validateOrder(list)) { return false; }

			list = dr.createIntegerList(SortingMetrics.ListType.ORDERED, n);
			sorter.sort(list, metrics);
			if(!ListValidator.validateOrder(list)) { return false; }

			list = dr.createIntegerList(SortingMetrics.ListType.REVERSE_ORDERED, n);
			sorter.sort(list, metrics);
			if(!ListValidator.validateOrder(list)) { return false; }

			if(n >= 10)
			{
				list = dr.createIntegerList(SortingMetrics.ListType.PARTIAL_ORDERED, n);
				sorter.sort(list, metrics);
				if(!ListValidator.validateOrder(list)) { return false; }
			}
		}

		return true;
	}

	public void testSelectionSort()
	{
		System.out.println("testSelectionSort: " + testSorter(new SelectionSort()));
	}

	public void testInsertionSort()
	{
		System.out.println("testInsertionSort: " + testSorter(new InsertionSort()));
	}

	public void testBubbleSort()
	{
		System.out.println("testBubbleSort: " + testSorter(new BubbleSort()));
	}

	public void testQuickSort()
	{
		System.out.println("testQuickSort: " + testSorter(new QuickSort()));
	}

	public void testMergeSort()
	{
		System.out.println("testMergeSort: " + testSorter(new MergeSort()));
	}

	public void testQuickSortOpt()
	{
		System.out.println("testQuickSortOpt: " + testSorter(new QuickSortOpt()));
	}
}
