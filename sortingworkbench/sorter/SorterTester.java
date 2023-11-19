
package sortingworkbench.sorter;
  
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import sortingworkbench.SortingMetrics;
import sortingworkbench.util.ListValidator;

public class SorterTester
{
  private List<Integer> testList;
  private SortingMetrics metrics;

  private void addRandomElements(List<Integer> list)
  {
    Random rand = new Random();

    for(int i = 0; i < 100; i++)
    {
      list.add(rand.nextInt(1000));
    }
  }

  @Before
  public void setupTest()
  {
    testList = new ArrayList<Integer>();
    metrics = new SortingMetrics(null);
    addRandomElements(testList);
  }

  @Test
  public void testSelectionSort()
  {
    BaseSort algo = new SelectionSort();
    algo.sort(testList, metrics);
    assertTrue(ListValidator.validateOrder(testList));
  }

  @Test
  public void testInsertionSort()
  {
    BaseSort algo = new InsertionSort();
    algo.sort(testList, metrics);
    assertTrue(ListValidator.validateOrder(testList));
  }

  @Test
  public void testBubbleSort()
  {
    BaseSort algo = new BubbleSort();
    algo.sort(testList, metrics);
    assertTrue(ListValidator.validateOrder(testList));
  }

  @Test
  public void testQuickSort()
  {
    BaseSort algo = new QuickSort();
    algo.sort(testList, metrics);
    assertTrue(ListValidator.validateOrder(testList));
  }

  @Test
  public void testMergeSort()
  {
    BaseSort algo = new MergeSort();
    algo.sort(testList, metrics);
    assertTrue(ListValidator.validateOrder(testList));
  }

  @Test
  public void testQuickSortOpt()
  {
    BaseSort algo = new QuickSortOpt();
    algo.sort(testList, metrics);
    assertTrue(ListValidator.validateOrder(testList));
  }
}