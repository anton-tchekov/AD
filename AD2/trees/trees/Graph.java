package trees;

import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

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

  public Vertex<E> breadthSearch(Vertex<E> startVertex, E toSearch)
  {
    Queue<Vertex<E>> internalQueue = new LinkedList<Vertex<E>>();
    HashSet<Vertex<E>> markedSet = new HashSet<Vertex<E>>();
    Vertex<E> currentVertex;

    if(startVertex == null) { throw new NullPointerException("startVertex is null"); }
    if(toSearch == null) { throw new NullPointerException("toSearch is null"); }

    // Add startVertex to the queue as setup
    internalQueue.add(startVertex);

    // Aslong as the Queue isnt Empty
    while(internalQueue.size() > 0)
    {
      // Dequeue the first Vertex
      currentVertex = internalQueue.poll();
      markedSet.add(currentVertex);

      // If the current Vertex is the correct Element, return it
      if(currentVertex.getContent() == toSearch)
      {
        return currentVertex;
      }
      else
      {
        // else Enqueue all neighbours of the current vertex
        for(Map.Entry<Vertex<E>, Integer> entry : currentVertex.getNeighbours().entrySet())
        {
          // Dont add the Vertex if its already Marked
          if(!markedSet.contains(entry.getKey()))
          {
            internalQueue.add(entry.getKey());
          } 
        }
      }
    }

    // If the queue is empty and the element wasnt found return null
    return null;
  }

  public Vertex<E> depthSearch(Vertex<E> startVertex, E toSearch)
  {
    Stack<Vertex<E>> internalStack = new Stack<Vertex<E>>();
    HashSet<Vertex<E>> markedSet = new HashSet<Vertex<E>>();
    Vertex<E> currentVertex;

    if(startVertex == null) { throw new NullPointerException("startVertex is null"); }
    if(toSearch == null) { throw new NullPointerException("toSearch is null"); }

    // Add startVertex to the queue as setup
    internalStack.add(startVertex);

    // Aslong as the Queue isnt Empty
    while(internalStack.size() > 0)
    {
      // Dequeue the first Vertex
      currentVertex = internalStack.pop();
      markedSet.add(currentVertex);

      // If the current Vertex is the correct Element, return it
      if(currentVertex.getContent() == toSearch)
      {
        return currentVertex;
      }
      else
      {
        // else Enqueue all neighbours of the current vertex
        for(Map.Entry<Vertex<E>, Integer> entry : currentVertex.getNeighbours().entrySet())
        {
          // Dont add the Vertex if its already Marked
          if(!markedSet.contains(entry.getKey()))
          {
            internalStack.add(entry.getKey());
          } 
        }
      }
    }

    // If the queue is empty and the element wasnt found return null
    return null;
  }

  public List<Vertex<E>> dijkstraSearch(Vertex<E> startVertex, E toSearch)
  {
    // Create a PriorityQueue and a marketSet
    PriorityQueue<dijkstraStorage<E>> internalPriorityQueue = new PriorityQueue<dijkstraStorage<E>>();
    HashSet<Vertex<E>> markedSet = new HashSet<Vertex<E>>();
    dijkstraStorage<E> currentStorage;

    // Start with a visited- zero cost- start vertex
    dijkstraStorage<E> startStorage = new dijkstraStorage<E>(null, startVertex, 0);

    // put the startVertex into the Queue
    internalPriorityQueue.add(startStorage);

    // Aslong as the internalqueue isnt empty
    while(internalPriorityQueue.size() > 0)
    {
      //System.out.print("Current Queue Weights: " + internalPriorityQueue);
      // Dequeue the internal priority
      currentStorage = internalPriorityQueue.poll();
      //System.out.println(" Chosen Weight: " + currentStorage.getWeight());
      markedSet.add(currentStorage.getVertex());

      // If the current vertex contains the toSearch Element, stop and return the Path
      if(currentStorage.getVertex().getContent() == toSearch)
      {
        List<Vertex<E>> prevList = new LinkedList<Vertex<E>>();

        while(currentStorage.getPrevStorage() != null)
        {
          prevList.add(0, currentStorage.getVertex());
          currentStorage = currentStorage.getPrevStorage();
        }

        return prevList;
      }

      // else add all Neighbours into the PriorityQueue that werent visited
      for(Map.Entry<Vertex<E>, Integer> entry : currentStorage.getVertex().getNeighbours().entrySet())
      {
        // Dont add the Vertex if its already Marked
        if(!markedSet.contains(entry.getKey()))
        {
          // Use the current weight of the storage plus the edge weight as new weight
          dijkstraStorage<E> newStorage = new dijkstraStorage<E>(currentStorage, entry.getKey(), currentStorage.getWeight() + entry.getValue());
          internalPriorityQueue.add(newStorage);
        } 
      }
    }
    
    // If the queue is empty and the element wasnt found return null
    return null;
  }

  public List<Vertex<E>> dijkstraSearch(Vertex<E> startVertex, Vertex<E> toSearch)
  {
    // Create a PriorityQueue and a marketSet
    PriorityQueue<dijkstraStorage<E>> internalPriorityQueue = new PriorityQueue<dijkstraStorage<E>>();
    HashSet<Vertex<E>> markedSet = new HashSet<Vertex<E>>();
    dijkstraStorage<E> currentStorage;

    // Start with a visited- zero cost- start vertex
    dijkstraStorage<E> startStorage = new dijkstraStorage<E>(null, startVertex, 0);

    // put the startVertex into the Queue
    internalPriorityQueue.add(startStorage);

    // Aslong as the internalqueue isnt empty
    while(internalPriorityQueue.size() > 0)
    {
      // Dequeue the internal priority
      currentStorage = internalPriorityQueue.poll();
      markedSet.add(currentStorage.getVertex());

      // If the current vertex contains the toSearch Element, stop and return the Path
      if(currentStorage.getVertex() == toSearch)
      {
        List<Vertex<E>> prevList = new LinkedList<Vertex<E>>();

        while(currentStorage != null)
        {
          prevList.add(0, currentStorage.getVertex());
          currentStorage = currentStorage.getPrevStorage();
        }

        return prevList;
      }

      // else add all Neighbours into the PriorityQueue that werent visited
      for(Map.Entry<Vertex<E>, Integer> entry : currentStorage.getVertex().getNeighbours().entrySet())
      {
        // Dont add the Vertex if its already Marked
        if(!markedSet.contains(entry.getKey()))
        {
          // Use the current weight of the storage plus the edge weight as new weight
          dijkstraStorage<E> newStorage = new dijkstraStorage<E>(currentStorage, entry.getKey(), currentStorage.getWeight() + entry.getValue());
          internalPriorityQueue.add(newStorage);
        } 
      }
    }
    
    // If the queue is empty and the element wasnt found return null
    return null;
  }

  public static long randomDijkstraSearchBenchmark(int graphSize)
  {
    Graph<Integer> graph = new Graph<Integer>();
    List<Vertex<Integer>> bestPath;

    Vertex<Integer> start = new Vertex<>(0);
    Vertex<Integer> destination = new Vertex<>(graphSize+1);

    /* Create a random Graph first */
    Vertex<Integer> lastVertex = start;
    Vertex<Integer> newVertex;
    List<Vertex<Integer>> addedVertices = new ArrayList<Vertex<Integer>>();
    
    /* add the start vertex to the list of total vertices in the graph */
    addedVertices.add(start);
    graph.addVertex(start);

    for(int i = 1; i < graphSize+1; i++)
    {
      // Get random values to generate a random graph
      int random_weight1 = new Random().nextInt(15);
      int random_weight2 = new Random().nextInt(15);
      int random_vertex = new Random().nextInt(addedVertices.size());

      // Create a new Vertex and add it to the total amount of vertexes, content doesnt matter
      newVertex = new Vertex<Integer>(i);
      addedVertices.add(newVertex);
      graph.addVertex(newVertex);

      // Add a link to the last vertex and a random one, ensures theres no gaps in the graph and gives alternative paths
      graph.addLink(newVertex, lastVertex, random_weight1);
      
      try
      {
        graph.addLink(addedVertices.get(random_vertex), newVertex, random_weight2);
      }
      catch(Exception IllegalArgumentException)
      {
        // If the Link already exists try just skip it
      }

      lastVertex = newVertex;
    }

    //System.out.println(graph.toString());

    // Benchmark the dijkstra search
    long startSearchTime = System.currentTimeMillis();
    bestPath = graph.dijkstraSearch(start, graphSize);
    long doneSearchTime = System.currentTimeMillis() - startSearchTime;

    // Print the Path
    /*
    System.out.print("0 -> ");
    int path_weight = 0;
    for(int i = 0; i < bestPath.size(); i++)
    {
      System.out.print(bestPath.get(i).getContent());
      if(i != bestPath.size()-1)
      {
        //System.out.print(" -> ");
        path_weight += bestPath.get(i).getNeighbourWeight(bestPath.get(i + 1));
      }
      else
      {
        path_weight += start.getNeighbourWeight(bestPath.get(0));
        System.out.println(" Overall Weight: " + path_weight);
      }
    }
    */

    // return the time it took for the random dijkstra
    return doneSearchTime;
  }

  @Override
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
