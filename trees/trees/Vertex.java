package trees;

import java.util.HashSet;

public class Vertex<E>
{
  private E content;
  private HashSet<Vertex<E>> neighbours;
  
  public Vertex(E newContent)
  {
    neighbours = new HashSet<Vertex<E>>();
    content = newContent;
  }

  public void removeLink(Vertex<E> vertex)
  {
    neighbours.remove(vertex);
  }

  public void addLink(Vertex<E> vertex)
  {
    neighbours.add(vertex);
  }

  public HashSet<Vertex<E>> getNeighbours()
  {
    return neighbours;
  }

  public E getContent()
  {
    return content;
  }

  public int getNeighbourAmount()
  {
    return neighbours.size();
  }

  public String toString()
  {
    String result = getContent() + ":" +"(";

    for(Vertex<E> v : getNeighbours())
    {
      result += v.getContent() + "->";
    }
    result = result.substring(0, result.length()-2);
    result += ") ";

    return result;
  }
}
