package trees;

import trees.BinarySearchTree;

import java.util.NoSuchElementException;

public class BSTree<K extends Comparable<K>, E>
	implements BinarySearchTree<K, E>
{
	private K _key;
	private E _value;
	private BSTree<K, E> _left, _right;
	private int _count;

	@Override
	public void insert(K k, E e)
	{
		int cv;

		if(_key == null)
		{
			_key = k;
			_value = e;
			++_count;
		}

		cv = k.compareTo(_key);

		if(cv == 0)
		{
			_value = e;
		}
		else if(cv < 0)
		{
			if(_left == null)
			{
				_left = new BSTree<K, E>();
			}

			_left.insert(k, e);
		}
		else
		{
			if(_right == null)
			{
				_right = new BSTree<K, E>();
			}

			_right.insert(k, e);
		}
	}

	@Override
	public void remove(K k) throws NoSuchElementException
	{
	}

	private E _get(K k)
	{
		int cv;
		BSTree<K, E> cur, next;

		cur = this;
		if(cur._key == null)
		{
			return null;
		}

		while((cv = k.compareTo(cur._key)) != 0)
		{
			if((next = (cv < 0) ? cur._left : cur._right) == null)
			{
				return null;
			}

			cur = next;
		}

		return cur._value;
	}

	@Override
	public E get(K k) throws NoSuchElementException {
		E e = _get(k);
		if(e == null) {
			throw new NoSuchElementException();
		}

		return e;
	}

	@Override
	public int size() {
		return _count;
	}

	@Override
	public boolean contains(K k) {
		return _get(k) != null;
	}
}
