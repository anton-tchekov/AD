package trees;

import java.util.LinkedList;
import java.util.List;

public class dijkstraStorage<E> implements Comparable<dijkstraStorage<E>>
{
  private dijkstraStorage<E> prev;
  private Vertex<E> vertex;
  private int weightSum;

  public dijkstraStorage(dijkstraStorage<E> prevStorage, Vertex<E> newVertex, int newWeight)
  {
    prev = prevStorage;
    vertex = newVertex; 
    weightSum = newWeight;
  }

  public dijkstraStorage<E> getPrevStorage()
  {
    return prev;
  }

  public Vertex<E> getVertex()
  {
    return vertex;
  }

  public int getWeight()
  {
    return weightSum;
  }

  public void addToWeight(int addition)
  {
    weightSum += addition; 
  }

  @Override
  public int compareTo(dijkstraStorage<E> o) 
  {
    if (o == null) 
    {
      throw new NullPointerException("Object for compareTo is null");
    }
    
    return Integer.compare(this.getWeight(), o.getWeight());
  }

  @Override
  public String toString()
  {
    return Integer.toString(weightSum);
  }
}