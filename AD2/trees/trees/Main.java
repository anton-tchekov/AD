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
    
		
    dijkstraBenchmarkCSV(5000, 10, 10);

	}

  public static void dijkstraBenchmarkCSV(int repeats, int increment, int samplesPerIncrement)
  {
    int graph_size = 0;
    Semaphore sem = new Semaphore(12);
    System.out.println("Graphsize,time in ms");
    for(int i = 0; i < repeats; i++)
    {
      graph_size += increment;
      final int size = graph_size;
      try {
        sem.acquire();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      new Thread(new Runnable() 
      {
        @Override
        public void run() 
        {
          threadTest(10, size);
        }
      }).start();
      sem.release();
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
