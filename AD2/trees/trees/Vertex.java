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

	public int getNeighbourAmount()
	{
		return neighbours.size();
	}

	public String toString()
	{
		String result = getContent() + ":" + "(";

		for(Map.Entry<Vertex<E>, Integer> entry : getNeighbours().entrySet())
		{
			result += entry.getKey().getContent() + " -" + entry.getValue() +"-> ";
		}

		result = result.substring(0, result.length() - 6);
		result += ") ";
		return result;
	}
}
