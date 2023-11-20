
package sortingworkbench.sorter;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;

import sortingworkbench.DataRetriever;
import sortingworkbench.SortingMetrics;
import sortingworkbench.util.ListValidator;

public class SorterTester2
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

	@Test
	public void testSelectionSort()
	{
		assertTrue(testSorter(new SelectionSort()));
	}

	@Test
	public void testInsertionSort()
	{
		assertTrue(testSorter(new InsertionSort()));
	}

	@Test
	public void testBubbleSort()
	{
		assertTrue(testSorter(new BubbleSort()));
	}

	@Test
	public void testQuickSort()
	{
		assertTrue(testSorter(new QuickSort()));
	}

	@Test
	public void testMergeSort()
	{
		assertTrue(testSorter(new MergeSort()));
	}

	@Test
	public void testQuickSortOpt()
	{
		assertTrue(testSorter(new QuickSortOpt()));
	}
}
