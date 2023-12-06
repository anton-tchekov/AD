package sortingworkbench;

import sortingworkbench.sorter.Sorter;

import java.util.List;

public class WorkbenchConfig
{
	private final int elementsStart;
	private final int elementsIncrement;
	private final int partialOrderedListSize;
	private final List<Sorter> sorter;

	private final RunInfo[] runs;

	private int currentRun = -1;

	public WorkbenchConfig(List<Sorter> sorter, int numRuns,
		int elementsStart, int elementsIncrement,
		int partialOrderedListSize)
	{
		this.runs = new RunInfo[numRuns];
		this.elementsStart = elementsStart;
		this.elementsIncrement = elementsIncrement;
		this.sorter = sorter;
		this.partialOrderedListSize = partialOrderedListSize;
	}

	public RunInfo getNextRunConfig()
	{
		currentRun++;
		runs[currentRun] = new RunInfo(this.sorter,
			currentRun * elementsIncrement + elementsStart,
			this.partialOrderedListSize);

		return runs[currentRun];
	}

	public boolean isFinished()
	{
		return currentRun >= runs.length - 1;
	}

	public int getCurrentRun()
	{
		return currentRun + 1;
	}
}
