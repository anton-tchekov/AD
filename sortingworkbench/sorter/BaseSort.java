package sortingworkbench.sorter;

public abstract class BaseSort implements Sorter
{
	@Override
	public String toString()
	{
		return this.getName();
	}
}
