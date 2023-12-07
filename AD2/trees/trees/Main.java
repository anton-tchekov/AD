package trees;

import java.util.List;

public class Main
{
	public static void main(String[] args)
	{
		/* Es ist nicht genug Platz für uns beide in der Main
		 *
		 *        ___
		 *     __|___|__
		 *      ('o_o')
		 *      _\~-~/_    ______.
		 *     //\__/\ \ ~(_]---'
		 *    / )O  O( .\/_)
		 *    \ \    / \_/
		 *    )/_|  |_\
		 *   // /(\/)\ \
		 *   /_/      \_\
		 *  (_||      ||_)
		 *    \| |__| |/
		 *     | |  | |
		 *     | |  | |
		 *     |_|  |_|
		 *    /_\  /_\
		 *
		 * Das stimmt xD, deswegen gehe ich in ne eigene Datei, bitte die
		 * nachfolgende Zeile nicht erschiessen.
		 */
		new TreeTest().runTests();

		Graph<String> g = new Graph<String>();

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

    String anschaulich = "";
    List<Vertex<String>> result = g.dijkstraSearch(A, "E");

    for(int i = 0; i < result.size(); i++)
    {
      anschaulich += result.get(i).getContent().toString() + " ";
    }

		System.out.println(g.toString());

    System.out.println(anschaulich);
	}
}
