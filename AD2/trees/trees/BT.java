package trees;

import java.util.function.Consumer;

public class BT<Data> implements BinaryTree<Data>
{
	BT<Data> left, right;
	Data data;

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	public BinaryTree<Data> getLeftNode()
	{
		return left;
	}

	public BinaryTree<Data> getRightNode()
	{
		return right;
	}

	public boolean isLeaf()
	{
		return left == null && right == null;
	}

	public void visitPreOrder(Consumer<BinaryTree<Data>> visitor)
	{
		visitor.accept(this);
		if(left != null)
		{
			left.visitPreOrder(visitor);
		}

		if(right != null)
		{
			right.visitPreOrder(visitor);
		}
	}

	public void visitInOrder(Consumer<BinaryTree<Data>> visitor)
	{
		if(left != null)
		{
			left.visitInOrder(visitor);
		}

		visitor.accept(this);
		if(right != null)
		{
			right.visitInOrder(visitor);
		}
	}

	public void visitPostOrder(Consumer<BinaryTree<Data>> visitor)
	{
		if(left != null)
		{
			left.visitPostOrder(visitor);
		}

		if(right != null)
		{
			right.visitPostOrder(visitor);
		}

		visitor.accept(this);
	}
}
