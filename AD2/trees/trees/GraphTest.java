package trees;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GraphTest
{
	Graph<String> g;

	@Before
	public void setup()
	{
		g = new Graph<String>();
	}

	@Test
	public void testAddVertexPositive()
	{
		Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");
		Vertex<String> D = new Vertex<String>("D");

		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);

		assertTrue(g.containsVertex(A));
		assertTrue(g.containsVertex(B));
		assertTrue(g.containsVertex(C));
		assertFalse(g.containsVertex(D));
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddVertexDuplicates()
	{
		Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");

		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);
		g.addVertex(A);
	}

	@Test (expected = NullPointerException.class)
	public void testAddVertexNullPointer()
	{
		Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");
		Vertex<String> D = null;
		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);
		g.addVertex(D);
	}
}
