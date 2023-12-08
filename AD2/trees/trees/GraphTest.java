package trees;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;
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

  public void testBreadthSearchVorlesung()
  {
    Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");
    Vertex<String> D = new Vertex<String>("D");
		Vertex<String> E = new Vertex<String>("E");

    g.addVertex(A);
    g.addVertex(B);
    g.addVertex(C);
    g.addVertex(D);
    g.addVertex(E);

    g.addLink(A, B, 0);
    g.addLink(A, C, 0);
    g.addLink(C, D, 0);
    g.addLink(D, B, 0);
    g.addLink(C, E, 0);

    assertTrue(g.breadthSearch(A, "A") != null);
    assertTrue(g.breadthSearch(A, "B") != null);
    assertTrue(g.breadthSearch(A, "C") != null);
    assertTrue(g.breadthSearch(A, "D") != null);
    assertTrue(g.breadthSearch(A, "E") != null);

    assertFalse(g.breadthSearch(A, "F") != null);
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

  @Test
  public void testDepthSearchVorlesung()
  {
    Vertex<String> A = new Vertex<String>("A");
		Vertex<String> B = new Vertex<String>("B");
		Vertex<String> C = new Vertex<String>("C");
    Vertex<String> D = new Vertex<String>("D");
		Vertex<String> E = new Vertex<String>("E");

    g.addVertex(A);
    g.addVertex(B);
    g.addVertex(C);
    g.addVertex(D);
    g.addVertex(E);

    g.addLink(A, B, 0);
    g.addLink(A, C, 0);
    g.addLink(C, D, 0);
    g.addLink(D, B, 0);
    g.addLink(C, E, 0);

    assertTrue(g.depthSearch(A, "A") != null);
    assertTrue(g.depthSearch(A, "B") != null);
    assertTrue(g.depthSearch(A, "C") != null);
    assertTrue(g.depthSearch(A, "D") != null);
    assertTrue(g.depthSearch(A, "E") != null);

    assertFalse(g.depthSearch(A, "F") != null);
  }

  @Test
  public void testDijkstraSearch()
  {
    // Adding 10 vertices
    Vertex<String> A = new Vertex<>("A");
    Vertex<String> B = new Vertex<>("B");
    Vertex<String> C = new Vertex<>("C");
    Vertex<String> D = new Vertex<>("D");
    Vertex<String> E = new Vertex<>("E");
    Vertex<String> F = new Vertex<>("F");
    Vertex<String> G = new Vertex<>("G");
    Vertex<String> H = new Vertex<>("H");
    Vertex<String> I = new Vertex<>("I");
    Vertex<String> J = new Vertex<>("J");

    g.addVertex(A);
    g.addVertex(B);
    g.addVertex(C);
    g.addVertex(D);
    g.addVertex(E);
    g.addVertex(F);
    g.addVertex(G);
    g.addVertex(H);
    g.addVertex(I);
    g.addVertex(J);

    // Adding links between vertices
    g.addLink(A, B, 3);
    g.addLink(B, C, 2);
    g.addLink(C, D, 5);
    g.addLink(D, E, 4);
    g.addLink(E, F, 1);
    g.addLink(F, G, 7);
    g.addLink(G, H, 2);
    g.addLink(H, I, 3);
    g.addLink(I, J, 6);

    List<Vertex<String>> result = g.dijkstraSearch(A, "E");

    assertTrue(result.get(0) == B);
    assertTrue(result.get(1) == C);
    assertTrue(result.get(2) == D);
    assertTrue(result.get(3) == E);
  }
}
