echo "Building ... \(^_^)/"

javac -Xlint:deprecation \
	sortingworkbench/sorter/SorterTester.java \
	sortingworkbench/DataRetriever.java \
	sortingworkbench/gui/DelayedSortingMetrics.java \
	sortingworkbench/gui/ListPanel.java \
	sortingworkbench/gui/WorkbenchGUI.java \
	sortingworkbench/RunInfo.java \
	sortingworkbench/RunMetrics.java \
	sortingworkbench/sorter/BaseSort.java \
	sortingworkbench/sorter/BubbleSort.java \
	sortingworkbench/sorter/InsertionSort.java \
	sortingworkbench/sorter/MergeSort.java \
	sortingworkbench/sorter/QuickSort.java \
	sortingworkbench/sorter/SelectionSort.java \
	sortingworkbench/sorter/Sorter.java \
	sortingworkbench/SortingMetrics.java \
	sortingworkbench/SortingWorkbench.java \
	sortingworkbench/util/ListValidator.java \
	sortingworkbench/util/ProgressEvent.java \
	sortingworkbench/util/WorkbenchProgressListener.java \
	sortingworkbench/WorkbenchConfig.java \
