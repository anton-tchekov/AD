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
    
		
    dijkstraBenchmarkCSV(20, 1000000);

	}

  public static void dijkstraBenchmarkCSV(int repeats, int increment)
  {
    int graph_size = 0;
    System.out.println("Graphsize,time in ms");
    for(int i = 0; i < repeats; i++)
    {
      graph_size += increment;
      System.out.println(graph_size + "," + Graph.randomDijkstraSearchBenchmark(graph_size));
    }
  }
}
