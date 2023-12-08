package trees;

import java.util.List;
import java.util.concurrent.Semaphore;

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
    
		
    dijkstraBenchmarkCSV(1000, 1000, 10);

	}

    public static void dijkstraBenchmarkCSV(int repeats, int increment, int samplesPerIncrement)
  {
    int graph_size = 0;
    int average = 0;
    System.out.println("Graphsize,time in ms");
    for(int i = 0; i < repeats; i++)
    {
      graph_size += increment;
      average = 0;
      for(int j = 0; j < samplesPerIncrement; j++)
      {
        average += Graph.randomDijkstraSearchBenchmark(graph_size);
      }
      System.out.println(graph_size + "," + average/samplesPerIncrement);
    }
  }

  public static void threadTest(int samplesPerIncrement, int graph_size)
  {
    int average = 0;

    for(int j = 0; j < samplesPerIncrement; j++)
    {
      average += Graph.randomDijkstraSearchBenchmark(graph_size);
    }
    System.out.println(graph_size + "," + average/samplesPerIncrement);
  }
}
