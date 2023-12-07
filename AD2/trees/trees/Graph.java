package trees;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class Graph<E>
{
	private Set<Vertex<E>> vertices;

	public Graph()
	{
		vertices = new HashSet<Vertex<E>>();
	}

	public boolean containsVertex(Vertex<E> v)
	{
		return vertices.contains(v);
	}

	public Set<Vertex<E>> getVertices()
	{
		return vertices;
	}

	public void addVertex(Vertex<E> vertex)
	{
		if(vertex == null)
		{
			throw new NullPointerException("Either of the vertices is null");
		}

		if(vertices.contains(vertex))
		{
			throw new IllegalArgumentException("Vertex is already in the Graph");
		}

		vertices.add(vertex);
	}

	public void addVertex(E content, Vertex<E> link, int weight)
  {
    Vertex<E> newVertex = new Vertex<E>(content);
    addVertex(newVertex);
    addLink(newVertex, link, weight);
  }

	public void removeVertex(Vertex<E> vertex)
	{
		if(vertex == null)
		{
			throw new NullPointerException("Either of the vertices is null");
		}

		if(!vertices.contains(vertex))
		{
			throw new IllegalArgumentException("Vertex doesnt exist in Graph");
		}

		// Remove all Neighbors of the vertex, if they exist in the same Graph
		// if they dont... thats bad
		for(Map.Entry<Vertex<E>, Integer> entry : vertex.getNeighbours().entrySet())
		{
			if(!vertices.contains(entry.getKey()))
			{
				throw new IllegalStateException("Vertex has Neighbours that arent in the same Graph");
			}

			vertex.removeLink(entry.getKey());
		}

		vertices.remove(vertex);
	}

	public void addLink(Vertex<E> firstVertex, Vertex<E> secondVertex, int weight)
	{
		if(firstVertex == null || secondVertex == null)
		{
			throw new NullPointerException("Either of the vertices is null");
		}

		if(!vertices.contains(firstVertex))
		{
			throw new IllegalArgumentException("firstVertex is not part of the Graph");
		}

		if(!vertices.contains(secondVertex))
		{
			throw new IllegalArgumentException("secondVertex is not part of the Graph");
		}

		if(firstVertex.getNeighbours().containsKey(secondVertex) &&
			secondVertex.getNeighbours().containsKey(firstVertex))
		{
			throw new IllegalArgumentException("Link already exists");
		}

		firstVertex.addLink(secondVertex, weight);
		secondVertex.addLink(firstVertex, weight);
	}

	public void removeLink(Vertex<E> firstVertex, Vertex<E> secondVertex)
	{
		if(firstVertex == null || secondVertex == null)
		{
			throw new NullPointerException("Either of the vertices is null");
		}

		if(!vertices.contains(firstVertex))
		{
			throw new IllegalArgumentException("firstVertex isnt part of the Graph");
		}

		if(!vertices.contains(secondVertex))
		{
			throw new IllegalArgumentException("secondVertex isnt part of the Graph");
		}

		if(!firstVertex.getNeighbours().containsKey(secondVertex) &&
			!secondVertex.getNeighbours().containsKey(firstVertex))
		{
			throw new IllegalArgumentException("Link doesnt exist");
		}

		firstVertex.removeLink(secondVertex);
		secondVertex.removeLink(firstVertex);
	}

	public String toString()
	{
		String result = "";
		for(Vertex<E> v : vertices)
		{
			result += v.toString() + "\n";
		}

		return result;
	}
}
