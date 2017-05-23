/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

/* This class ties together all the information for a particular name.
Given a NameSurferEntry object, you can find out what name it corresponds to and
what its popularity rank was in each decade. */
public class NameSurferEntry implements NameSurferConstants {

	/* Private instance variables */
	private String name;
	private int[] decadeRank = new int[NDECADES];
	private String[] parts;
	String givenLine;
	
/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		givenLine = line;
		parts = line.split(" ");
		this.name = parts[0];
		for (int i = 0; i < NDECADES; i++) {
			decadeRank[i] = Integer.parseInt(parts[i+1]);
		}
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		if (decade >= 0 && decade < NDECADES) {
			return decadeRank[decade];
		}
		return 0;
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		return givenLine;
	}
}

