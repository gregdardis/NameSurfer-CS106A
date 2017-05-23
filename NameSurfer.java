/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

/* This is the main program class that ties together the application. It has
the responsibility for creating the other objects and for responding to the buttons at the
bottom of the window, but only to the point of redirecting those events to the objects
represented by the other classes. */
public class NameSurfer extends Program implements NameSurferConstants {
	
	/* Private instance variables */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private NameSurferDataBase database;
	private NameSurferGraph graph;

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		createController();
		addActionListeners();
		database = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);
	}
	
	/* Creates the control strip at the bottom of the window */
	private void createController() {
		addTextField();
		addButtons();
	}
	
	/* Adds the buttons to the SOUTH region of the canvas */
	private void addButtons() {
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);
	}
	
	/* Adds the TextField to the SOUTH region of the canvas */
	private void addTextField() {
		nameField = new JTextField(MAX_NAME_LENGTH);
		nameField.addActionListener(this);
		add(new JLabel("Name"), SOUTH);
		add(nameField, SOUTH);
	}

	/* What happens when the buttons are clicked */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == graphButton || source == nameField) {
			/* Graph name to screen */
			String beforeCaseChange = nameField.getText();
			String afterCaseChange = beforeCaseChange;
			if (beforeCaseChange.length() >= 2) {
				afterCaseChange = beforeCaseChange.substring(0, 1).toUpperCase() + beforeCaseChange.substring(1).toLowerCase();
			}
			/* Because findEntry returns null if the entry isn't in the database */
			if (database.findEntry(afterCaseChange) != null) {
				NameSurferEntry entry = database.findEntry(afterCaseChange);
				System.out.println(entry.toString());
			}
		} else if (source == clearButton) {
			/* Clear all graphed stuff */
			
		} 
	}
}
