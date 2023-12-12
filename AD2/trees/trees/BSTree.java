package trees;

import trees.BinarySearchTree;

import java.util.NoSuchElementException;

public class BSTree<K extends Comparable<K>, E>
	implements BinarySearchTree<K, E>
{
	class Node<K, E>
	{
		private K key;
		private E value;
		private Node<K, E> left, right;

		public Node(K k, E e)
		{
			key = k;
			value = e;
		}

		public void print(int spaces)
		{
			for(int i = 0; i < spaces; ++i)
			{
				System.out.print(" ");
			}

			System.out.println(key + ": " + value);

			spaces += 5;
			if(left != null)
			{
				left.print(spaces);
			}

			if(right != null)
			{
				right.print(spaces);
			}
		}
	}

	private int count;
	private Node<K, E> root;

	@Override
	public void insert(K k, E e)
	{
		int cv;
		Node<K, E> cur;

		assert k != null;

		if(root == null)
		{
			root = new Node<K, E>(k, e);
			++count;
			return;
		}

		cur = root;
		for(;;)
		{
			cv = k.compareTo(cur.key);
			if(cv == 0)
			{
				cur.value = e;
				return;
			}
			else if(cv < 0)
			{
				if(cur.left == null)
				{
					cur.left = new Node<K, E>(k, e);
					++count;
					return;
				}

				cur = cur.left;
			}
			else
			{
				if(cur.right == null)
				{
					cur.right = new Node<K, E>(k, e);
					++count;
					return;
				}

				cur = cur.right;
			}
		}
	}

	public void remove(K key)
	{
		Node<K, E> cur = root;
		Node<K, E> prev = null;
		while(cur != null && cur.key != key)
		{
			prev = cur;
			cur = (key.compareTo(cur.key) < 0) ? cur.left : cur.right;
		}

		if(cur == null) { throw new NoSuchElementException(); }
		--count;
		if(cur.left == null || cur.right == null)
		{
			Node<K, E> n = (cur.left == null) ? cur.right : cur.left;
			if(prev == null)
			{
				root = n;
				return;
			}

			if(cur == prev.left) { prev.left = n; }
			else { prev.right = n; }
		}
		else
		{
			Node<K, E> p = null;
			Node<K, E> temp = cur.right;
			while(temp.left != null)
			{
				p = temp;
				temp = temp.left;
			}

			if(p != null) { p.left = temp.right; }
			else { cur.right = temp.right; }

			cur.key = temp.key;
			cur.value = temp.value;
		}
	}

	private E _get(K k)
	{
		int cv;
		Node<K, E> cur, next;

		assert k != null;

		cur = root;
		if(cur == null)
		{
			return null;
		}

		while((cv = k.compareTo(cur.key)) != 0)
		{
			if((next = (cv < 0) ? cur.left : cur.right) == null)
			{
				return null;
			}

			cur = next;
		}

		return cur.value;
	}

	public int pathlen(K k) throws NoSuchElementException
	{
		int cv;
		Node<K, E> cur, next;
		int len;

		assert k != null;

		len = 0;
		cur = root;
		if(cur.key == null)
		{
			throw new NoSuchElementException();
		}

		while((cv = k.compareTo(cur.key)) != 0)
		{
			if((next = (cv < 0) ? cur.left : cur.right) == null)
			{
				throw new NoSuchElementException();
			}

			++len;
			cur = next;
		}

		return len;
	}

	@Override
	public E get(K k) throws NoSuchElementException
	{
		E e;
		if((e = _get(k)) == null)
		{
			throw new NoSuchElementException();
		}

		return e;
	}

	@Override
	public int size()
	{
		return count;
	}

	@Override
	public boolean contains(K k)
	{
		return _get(k) != null;
	}

	public void print()
	{
		if(root != null)
		{
			root.print(0);
		}
	}
}
