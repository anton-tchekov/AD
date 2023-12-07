package trees;

import java.util.Set;
import java.util.HashSet;

public class Graph
{
	public Set<Graph> links;

	public Graph()
	{
		links = new HashSet<Graph>();
	}

	public void addLink(Graph g)
	{
		links.add(g);
	}

	public void removeLink(Graph g)
	{
		links.remove(g);
	}
}
