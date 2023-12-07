package trees;

import java.io.Serial;
import java.util.HashSet;

public class Main
{
	public static void main(String[] args)
	{
    /* Es ist nicht genug platz für uns beide in der Main
         ___
     __|___|__
      ('o_o')
      _\~-~/_    ______.
     //\__/\ \ ~(_]---'
    / )O  O( .\/_)
    \ \    / \_/
    )/_|  |_\
   // /(\/)\ \
   /_/      \_\
  (_||      ||_)
    \| |__| |/
     | |  | |
     | |  | |
     |_|  |_|
    /_\  /_\



		BinarySearchTree<String, Integer> tree = new BSTree<String, Integer>();

		tree.insert("Hello", 12);
		tree.insert("LOL", 12);

		System.out.println("Hello : " + tree.get("Hello"));

		System.out.println("LOL : " + tree.get("LOL"));

		System.out.println("Hello world");
    */

    Graph<String> g = new Graph<String>();

    Vertex<String> A = new Vertex<String>("A");
    Vertex<String> B = new Vertex<String>("B");
    Vertex<String> C = new Vertex<String>("C");

    g.addVertex(A);
    g.addVertex(B);
    g.addVertex(C);

    g.addLink(A, B);
    g.addLink(C, A);

    System.out.println(g.toString());
    

	}
}
