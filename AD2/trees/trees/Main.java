package trees;

public class Main
{
	public static void main(String[] args)
	{
		BinarySearchTree<String, Integer> tree = new BSTree<String, Integer>();

		tree.insert("Hello", 12);
		tree.insert("LOL", 12);

		System.out.println("Hello : " + tree.get("Hello"));

		System.out.println("LOL : " + tree.get("LOL"));

		System.out.println("Hello world");
	}
}
