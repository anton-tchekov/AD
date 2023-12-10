package trees;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

class TreeTest
{
	public TreeTest()
	{
	}

	public void testBST()
	{
		/* Test insert */
		BSTree<Character, Integer> bst = new BSTree<Character, Integer>();
		bst.insert('A', 10);
		bst.insert('F', 15);
		bst.insert('C', 12);
		bst.insert('6', 7);
		bst.insert('6', 6);

		/* Test contains */
		test(bst.contains('A'), "contains");
		test(bst.contains('F'), "contains");
		test(bst.contains('6'), "contains");
		test(!bst.contains('7'), "not contains");
		test(!bst.contains('B'), "not contains");

		/* Test size */
		test(bst.size() == 4, "size");

		/* Test get */
		test(bst.get('A') == 10, "get");
		test(bst.get('F') == 15, "get");
		test(bst.get('C') == 12, "get");
		test(bst.get('6') == 6, "get");

		/* Test get exception */
		try
		{
			bst.get('X');
			test(false, "get nonexistant");
		}
		catch(NoSuchElementException e)
		{
			test(true, "get nonexistant");
		}

		/* Test remove nonexistant */
		try
		{
			bst.remove('X');
			test(false, "remove nonexistant");
		}
		catch(NoSuchElementException e)
		{
			test(true, "remove nonexistant");
		}

		/* Test remove */
		bst.remove('A');
		test(!bst.contains('A'), "remove A");
		test(bst.get('F') == 15, "get");
		test(bst.get('C') == 12, "get");
		test(bst.get('6') == 6, "get");
		test(bst.size() == 3, "remove size");
		bst.print();

		bst.remove('F');
		test(!bst.contains('F'), "remove F");
		test(bst.get('6') == 6, "get");
		test(bst.get('C') == 12, "get");
		test(bst.size() == 2, "remove size");
		bst.print();

		bst.remove('C');
		test(!bst.contains('C'), "remove C");
		test(bst.get('6') == 6, "get");
		test(bst.size() == 1, "remove size");
		bst.print();

		bst.remove('6');
		test(!bst.contains('6'), "remove 6");
		test(bst.size() == 0, "remove all -> size 0");
		bst.print();
	}

	private BT<Character> createLetterNode(char c)
	{
		BT<Character> b = new BT<Character>();
		b.setData(c);
		return b;
	}

	private void test(boolean cond, String str)
	{
		System.out.println(str + " : " + (cond ? "success" : "failed"));
		if(!cond) { System.exit(1); }
	}

	private void testEq(String str, Object a, Object b)
	{
		boolean cond = a.equals(b);
		System.out.println(str + " - " + a + " == " + b + " : " + (cond ? "success" : "failed"));
		if(!cond) { System.exit(1); }
	}

	public void testBT()
	{
		/* Create Tree */
		BT<Character> btA = createLetterNode('A');
		BT<Character> btB = createLetterNode('B');
		BT<Character> btC = createLetterNode('C');
		BT<Character> btD = createLetterNode('D');
		BT<Character> btE = createLetterNode('E');
		BT<Character> btF = createLetterNode('F');
		BT<Character> btG = createLetterNode('G');
		BT<Character> btH = createLetterNode('H');
		BT<Character> btI = createLetterNode('I');

		btA.setLeftNode(btB);
		btA.setRightNode(btC);

		btB.setLeftNode(btD);
		btB.setRightNode(btE);

		btC.setLeftNode(btF);
		btC.setRightNode(btG);

		btD.setRightNode(btH);

		btG.setLeftNode(btI);

		/* Test IsLeaf */
		test(btI.isLeaf() == true, "btI.isLeaf() == true");
		test(btH.isLeaf() == true, "btH.isLeaf() == true");
		test(btG.isLeaf() == false, "btG.isLeaf() == false");
		test(btF.isLeaf() == true, "btF.isLeaf() == true");
		test(btE.isLeaf() == true, "btE.isLeaf() == true");
		test(btD.isLeaf() == false, "btD.isLeaf() == false");
		test(btC.isLeaf() == false, "btC.isLeaf() == false");
		test(btB.isLeaf() == false, "btB.isLeaf() == false");
		test(btA.isLeaf() == false, "btA.isLeaf() == false");

		/* Test Traversal */
		{
			/* PreOrder */
			String[] sum = new String[] { "" };
			Consumer<BinaryTree<Character>> con = x -> { sum[0] += x.getData(); };
			btA.visitPreOrder(con);
			testEq("PreOrder", sum[0], "ABDHECFGI");
		}

		{
			/* InOrder */
			String[] sum = new String[] { "" };
			Consumer<BinaryTree<Character>> con = x -> { sum[0] += x.getData(); };
			btA.visitInOrder(con);
			testEq("InOrder", sum[0], "DHBEAFCIG");
		}

		{
			/* PostOrder */
			String[] sum = new String[] { "" };
			Consumer<BinaryTree<Character>> con = x -> { sum[0] += x.getData(); };
			btA.visitPostOrder(con);
			testEq("PostOrder", sum[0], "HDEBFIGCA");
		}
	}

	public void runTests()
	{
		System.out.println("#######################################################");
		System.out.println("###                    Tree Test                    ###");
		System.out.println("#######################################################\n\n");

		testBT();
		testBST();

		ThreadLocalRandom rng = ThreadLocalRandom.current();
		for(int n = 100; n <= 10000; n += 100)
		{
			int count = 1000;
			double avg = 0;
			for(int r = 0; r < count; ++r)
			{
				BSTree<Integer, Integer> bst = new BSTree<Integer, Integer>();
				ArrayList<Integer> keys = new ArrayList<Integer>();
				for(int i = 0; i < n; ++i)
				{
					int rnd = rng.nextInt(
						Integer.MIN_VALUE, Integer.MAX_VALUE);

					bst.insert(rnd, 0);
					keys.add(rnd);
				}

				int rndKey = keys.get(rng.nextInt(0, keys.size()));

				avg += ((double)bst.pathlen(rndKey) / bst.size()) + 1;
			}

			avg /= count;
			System.out.println(n + "," + avg);
		}

		System.out.println("\n\n#######################################################\n\n\n\n");
	}
}
