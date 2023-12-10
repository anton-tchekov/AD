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
  
    dijkstraBenchmarkCSV(10, 10, 20);
    Graph<Integer> graph = new Graph<Integer>();

    graph = Graph.createSquareGraph(4);
    System.out.println(graph.toString());
	}

  public static void dijkstraBenchmarkCSV(int repeats, int increment, int samplesPerIncrement)
  {
    int graph_size = 10;

    System.out.println("Graphsize,time in ms");
    for(int i = 0; i < repeats; i++)
    {
      threadTest(samplesPerIncrement, graph_size);
      graph_size += increment;
      //increment += increment/10;
    }
  }

  public static void threadTest(int samplesPerIncrement, int graph_size)
  {
    long average = 0;
    long min = 1000000000;
    long max = 0;

    long currentSample = 0;

    for(int j = 0; j < samplesPerIncrement; j++)
    {
      currentSample = Graph.randomDijkstraSearchBenchmark(graph_size);
      //average += currentSample;

      if(currentSample > max)
      {
        max = currentSample;
      }

      if(currentSample < min)
      {
        min = currentSample;
      }
    }
    //average /= samplesPerIncrement;

    System.out.println(graph_size + "," + max);
    System.gc();
  }
}
