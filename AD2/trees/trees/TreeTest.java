package trees;

class TreeTest
{
	public TreeTest()
	{
	}

	public void runTests()
	{
		System.out.println("#######################################################");
		System.out.println("###                    Tree Test                    ###");
		System.out.println("#######################################################\n\n");

		BinarySearchTree<String, Integer> tree = new BSTree<String, Integer>();

		tree.insert("Hello", 12);
		tree.insert("LOL", 12);

		System.out.println("Hello : " + tree.get("Hello"));

		System.out.println("LOL : " + tree.get("LOL"));

		System.out.println("Hello world");

		for(int n = 100; n <= 10000; n += 100)
		{
			for(int i = 0; i < 1000; ++i)
			{
				BinarySearchTree<Integer, Integer> bst = new BSTree<Integer, Integer>();
				//for(int ; )
				//bst.
			}
		}

		System.out.println("\n\n#######################################################\n\n\n\n");
	}
}