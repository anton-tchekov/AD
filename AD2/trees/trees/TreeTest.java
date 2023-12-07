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

		System.out.println("\n\n#######################################################\n\n\n\n");
	}
}