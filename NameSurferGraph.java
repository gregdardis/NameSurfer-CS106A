/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

/* This class is a subclass of GCanvas that displays the graph of the
various names by arranging the appropriate GLine and GLabel objects on the screen */
public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	
	private static final int LABEL_OFFSET_FROM_VERTICAL_LINE = 2;
	private static final int YEARS_PER_DECADE = 10;
	private static final int LABEL_OFFSET_FROM_BOTTOM_HORIZONTAL_LINE = 15;
	private static final int BLACK = 0;
	private static final int RED = 1;
	private static final int BLUE = 2;
	private static final int MAGENTA = 3;
	
	/* Private instance variables */
	ArrayList<NameSurferEntry> entries = new ArrayList<NameSurferEntry>();
	
	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		//	 You fill in the rest //
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entries.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		if (!entries.contains(entry)) {
			entries.add(entry);
		}
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		double topOfGraph = GRAPH_MARGIN_SIZE;
		double bottomOfGraph = getHeight() - GRAPH_MARGIN_SIZE;
		
		removeAll();
		drawVerticalLines();
		drawHorizontalLines(topOfGraph, bottomOfGraph);
		drawYearLabels(bottomOfGraph);
		drawEntriesOnGraph();
		
	}
	
	private void drawEntriesOnGraph() {
		double y2 = 0;
		Color color = Color.BLACK;
		for (int j = 0; j < entries.size(); j++) {
			color = chooseColor(j);
			for (int i = 0; i < NDECADES; i++) {
				NameSurferEntry entry = entries.get(j);
				double y1 = findLabelYCoordinate(entry, i);
				
				/* Don't have data for ahead of the year 2000 so can't assign a new y coordinate for the rank of that decade */
				if (i < NDECADES - 1) {
					y2 = findLabelYCoordinate(entry, i + 1);
				}
				GLabel nameAndRank = new GLabel(entry.getName() + " " + entry.getRank(i), i * (getWidth() / NDECADES) + LABEL_OFFSET_FROM_VERTICAL_LINE, y1);
				GLine lineAcrossDecade = new GLine(i * (getWidth() / NDECADES), y1, (i+1) * (getWidth() / NDECADES), y2);
				nameAndRank.setColor(color);
				lineAcrossDecade.setColor(color);
				add(nameAndRank);
				add(lineAcrossDecade);
			} 
		}
	}
	
	/* Returns a color for the next labels and graph lines to be printed, cycling through them for each name searched */
	private Color chooseColor(int arrayListIndex) {
		int whichColor = 0;
		whichColor = arrayListIndex % 4;
		if (whichColor == BLACK) {
			return Color.BLACK;
		}
		if (whichColor == RED) {
			return Color.RED;
		}
		if (whichColor == BLUE) {
			return Color.BLUE;
		}
		if (whichColor == MAGENTA) {
			return Color.MAGENTA;
		}
		return Color.BLACK;
	}
	
	/* Finds at what height a label and graph line should be based on that entries rank
	 * and which part of the graph it's in (index) */
	private double findLabelYCoordinate(NameSurferEntry entry, int index) {
		if (entry.getRank(index) == 0) {
			return getHeight() - GRAPH_MARGIN_SIZE;
		}
		double y = GRAPH_MARGIN_SIZE + ((entry.getRank(index) / MAX_RANK) * (getHeight() - (2 * GRAPH_MARGIN_SIZE)));
		return y;
	}
	
	/* Labels the years at the bottom of the graph */
	private void drawYearLabels(double bottomOfGraph) {
		for (int i = 1; i < NDECADES - 1; i++) {
			String year = "19" + (i * YEARS_PER_DECADE);
			add(new GLabel(year, i * (getWidth() / NDECADES) + LABEL_OFFSET_FROM_VERTICAL_LINE, bottomOfGraph + LABEL_OFFSET_FROM_BOTTOM_HORIZONTAL_LINE));
		}
		add(new GLabel("1900", LABEL_OFFSET_FROM_VERTICAL_LINE, bottomOfGraph + LABEL_OFFSET_FROM_BOTTOM_HORIZONTAL_LINE));
		add(new GLabel("2000", LABEL_OFFSET_FROM_VERTICAL_LINE + ((NDECADES - 1) * (getWidth() / NDECADES)), bottomOfGraph + LABEL_OFFSET_FROM_BOTTOM_HORIZONTAL_LINE));
	}
	
	/* Draws the vertical lines for the graph */
	private void drawVerticalLines() {
		for (int i = 0; i < NDECADES + 1; i++) {
			add(new GLine(i * (getWidth() / NDECADES), 0, i * (getWidth() / NDECADES), getHeight()));
		}
	}
	
	/* Draws the horizontal lines for the graph */
	private void drawHorizontalLines(double topOfGraph, double bottomOfGraph) {
			add(new GLine(0, topOfGraph, getWidth(), topOfGraph));
			add(new GLine(0, bottomOfGraph, getWidth(), bottomOfGraph));
	}
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
//TODO: FIX COLOR PICKING BY USING AN ARRAY INSTEAD OF IF ELSE