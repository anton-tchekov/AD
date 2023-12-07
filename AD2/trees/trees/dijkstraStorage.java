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
    if(o == null)
    {
      throw new IllegalArgumentException("Object for compareTo is null");
    }

    if(o.getWeight() == this.getWeight())
    {
      return 0;
    }
    else if(o.getWeight() > this.getWeight())
    {
      return 1;
    }
    else if(o.getWeight() < this.getWeight())
    {
      return -1;
    }
  
    return 0;
  }
}