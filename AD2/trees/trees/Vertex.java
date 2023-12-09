package trees;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Vertex<E>
{
	private E content;
	private HashMap<Vertex<E>, Integer> neighbours;

	public Vertex(E newContent)
	{
		neighbours = new HashMap<Vertex<E>, Integer>();
		content = newContent;
	}

	public void removeLink(Vertex<E> vertex)
	{
		neighbours.remove(vertex);
	}

	public void addLink(Vertex<E> vertex, int weight)
	{
		neighbours.put(vertex, weight);
	}

	public HashMap<Vertex<E>, Integer> getNeighbours()
	{
		return neighbours;
	}

	public E getContent()
	{
		return content;
	}

  public boolean areNeighbours(Vertex<E> vertex)
  {
    if(neighbours.containsKey(vertex))
    {
      return true;
    }
    
    return false;
  }

	public int getNeighbourAmount()
	{
		return neighbours.size();
	}

  public int getNeighbourWeight(Vertex<E> vertex)
  {
    Integer result = getNeighbours().get(vertex);

    if(result == null)
    {
      throw new IllegalArgumentException("Given Vertices have no Link");
    }

    return result.intValue();
  }

	public String toString()
	{
		String result = getContent() + ":" + "(";

		for(Map.Entry<Vertex<E>, Integer> entry : getNeighbours().entrySet())
		{
			result += "[" + entry.getKey().getContent().toString() + "," + entry.getValue() +"] ";
		}
		result += ") ";
		return result;
	}

}
