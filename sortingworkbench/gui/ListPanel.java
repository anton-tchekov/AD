package sortingworkbench.gui;

import sortingworkbench.SortingMetrics;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ListPanel extends JPanel
{
	private List<Integer> listToShow = Collections.emptyList();

	private SortingMetrics metrics;

	private final int numElements;

	public ListPanel(int numElements)
	{
		this.numElements = numElements;
	}

	public void setListToShow(List<Integer> listToShow)
	{
		this.listToShow = listToShow;
	}

	public void setMetrics(SortingMetrics metrics)
	{
		this.metrics = metrics;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();

		g.clearRect(0, 0, numElements + 2, numElements + 2);
		g2.setStroke(new BasicStroke(2));

		g.drawRect(1, 1, numElements + 2, numElements + 2);
		g2.setStroke(oldStroke);

		g.setColor(Color.BLUE);

		// The list might be empty at the beginning, so we use list.size() instead of numElements
		for(int i = 0; i < this.listToShow.size(); ++i)
		{
			// g.drawRect(2 + i,  numElements - listToShow.get(i), 1, 1);
			g.drawRect(2 + i,  numElements - listToShow.get(i), 1, listToShow.get(i));
		}

		if(this.metrics != null)
		{
			g.setColor(Color.BLACK);
			g.drawString("Compares: %d".formatted(metrics.getNumCompares()), 5, 20);
			g.drawString("Moves: %d".formatted(metrics.getNumMoves()), 24, 35);
		}
	}
}
