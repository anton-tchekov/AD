package trees;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Map;

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

	@Test (expected = NullPointerException.class)
  public void testAddVertexWithLink()
  {
    Vertex<String> A = new Vertex<String>("A");
    g.addVertex(A);
    g.addVertex("B", A, 5);

    assertTrue(A.getNeighbours().size() > 0);

    g.addVertex(null, A, 5);
    g.addVertex("C", null, 5);
  }

  @Test (expected = NullPointerException.class)
  public void testRemoveVertex()
  {
    Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");

		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);

		assertTrue(g.containsVertex(A));
		assertTrue(g.containsVertex(B));
		assertTrue(g.containsVertex(C));

    g.removeVertex(C);

    assertFalse(g.containsVertex(C));

    g.removeVertex(A);
    g.removeVertex(B);

    assertFalse(g.containsVertex(A));
		assertFalse(g.containsVertex(B));

    g.removeVertex(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddLink()
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

    assertFalse(A.getNeighbours().size() > 0);
    assertFalse(B.getNeighbours().size() > 0);
    assertFalse(C.getNeighbours().size() > 0);

    g.addLink(B, C, 5);
    g.addLink(A, B, 3);
    g.addLink(A, D, 6);

    assertTrue(A.getNeighbours().size() > 0);
    assertTrue(B.getNeighbours().size() > 0);
    assertTrue(C.getNeighbours().size() > 0);

    assertTrue(A.getNeighbourWeight(B) == 3);
    assertTrue(B.getNeighbourWeight(A) == 3);
    assertTrue(C.getNeighbourWeight(B) == 5);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNeighbourWeight()
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

    g.addLink(B, C, 5);

    B.getNeighbourWeight(C);
    A.getNeighbourWeight(B);
  }

  @Test (expected = NullPointerException.class)
  public void testBreadthSearch()
  {
    Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");

		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);

		g.addLink(A, B, 3);
		g.addLink(C, A, 2);

		g.addVertex("D", A, 2);
		g.addVertex("E", A, 3);
		g.addVertex("F", A, 5);

    g.breadthSearch(A, null);
    g.breadthSearch(null, "A");

    assertTrue(g.breadthSearch(A, "B") != null);
    assertTrue(g.breadthSearch(A, "E") != null);

    assertFalse(g.breadthSearch(A, "Z") != null);
  }

  @Test (expected = NullPointerException.class)
  public void testDepthSearch()
  {
    Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");

		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);

		g.addLink(A, B, 3);
		g.addLink(C, A, 2);

		g.addVertex("D", A, 2);
		g.addVertex("E", A, 3);
		g.addVertex("F", A, 5);

    g.depthSearch(A, null);
    g.depthSearch(null, "A");

    assertTrue(g.depthSearch(A, "B") != null);
    assertTrue(g.depthSearch(A, "E") != null);

    assertFalse(g.depthSearch(A, "Z") != null);
  }
}
