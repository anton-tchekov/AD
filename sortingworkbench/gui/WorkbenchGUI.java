package sortingworkbench.gui;

import sortingworkbench.DataRetriever;
import sortingworkbench.SortingMetrics;
import sortingworkbench.sorter.Sorter;
import sortingworkbench.util.ListValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class WorkbenchGUI extends JFrame
{
	private final JComboBox<Sorter> comboboxAlgorithms;
	private final JButton buttonStartStopSort;

	/**
	 * Merkt sich den Sorter für die Dauer des Sortiervorgangs, um
	 * den Sorter ausblenden zu können.
	 */
	private Sorter choosenSorter;

	private final JComboBox<SortingMetrics.ListType> comboBoxListtype;

	private final ListPanel animationPane;

	private Timer timer;

	private List<Integer> elements;

	private final int numElements;
	private Thread sortingThread;

	public WorkbenchGUI(Sorter[] sorter, int numElements)
	{
		//Create and set up the window.
		super("Sorting Workbench 1.0");

		this.numElements = numElements;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel settingsPane = new JPanel();
		new BoxLayout(settingsPane, BoxLayout.LINE_AXIS);

		settingsPane.add(new JLabel("Algorithmus:"));
		comboboxAlgorithms = new JComboBox<>(sorter);
		settingsPane.add(comboboxAlgorithms);

		settingsPane.add(new JLabel("Ausgangsliste:"));
		comboBoxListtype = new JComboBox<>(SortingMetrics.ListType.values());
		settingsPane.add(comboBoxListtype);

		buttonStartStopSort = new JButton(new AbstractAction("Sort")
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(sortingThread == null)
				{
					executeSort();
				}
				else
				{
					// Das harte stoppen eines Threads ist eigentlich nicht mehr gewünscht (siehe Doku).
					// Hier "befreit" es uns aber davon, in den Sortieralgorithmen auf ein Flag zu prüfen
					sortingThread.stop();
					sortingEnded();
				}
			}
		});

		settingsPane.add(buttonStartStopSort);

		animationPane = new ListPanel(numElements);
		animationPane.setDoubleBuffered(true);
		animationPane.setPreferredSize(new Dimension(numElements, numElements));

		this.getContentPane().add(settingsPane, BorderLayout.NORTH);
		this.getContentPane().add(animationPane, BorderLayout.CENTER);

		this.pack();
	}

	private void executeSort()
	{
		SortingMetrics.ListType listType = (SortingMetrics.ListType)this.comboBoxListtype.getSelectedItem();
		final Sorter sorter = (Sorter)this.comboboxAlgorithms.getSelectedItem();
		this.choosenSorter = sorter;

		assert listType != null;
		assert sorter != null;

		this.elements = new DataRetriever().createIntegerList(listType, numElements);
		this.animationPane.setListToShow(this.elements);
		DelayedSortingMetrics m = new DelayedSortingMetrics(listType, 5, 1);
		this.animationPane.setMetrics(m);

		this.timer = new Timer(50, e -> WorkbenchGUI.this.repaint());

		this.sortingThread = new Thread(() -> {
			sortingStarted();

			sorter.sort(elements, m);
			assert ListValidator.validateOrder(elements);

			sortingEnded();
		}, "Sorting thread");

		this.sortingThread.start();
	}

	private void sortingStarted()
	{
		this.buttonStartStopSort.setText("Stop");
		this.comboboxAlgorithms.setSelectedItem(null);
		this.timer.start();
	}

	private void sortingEnded()
	{
		this.buttonStartStopSort.setText("Start");
		this.comboboxAlgorithms.setSelectedItem(this.choosenSorter);
		this.choosenSorter = null;
		this.timer.stop();
		this.sortingThread = null;
		this.repaint();
	}
}
