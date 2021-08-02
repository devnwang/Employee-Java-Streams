package com.cognixia.jump.intermediatejava.assignments.employeejavastreams;

public class DataEntryException extends Exception {

	/**
	 * Required SerialVersion UID when extending from teh Exception class
	 */
	private static final long serialVersionUID = -3748351733135801505L;
	
	// Expected number of data fields
	static final int expectedFieldCnt = 5;
	
	// Incorrect number of data fields
	public DataEntryException(int receivedFieldCnt) {
		super("Data Entry Error! A data entry has an incorrect number of fields. (Expected: " +
				expectedFieldCnt + ", Received: " + receivedFieldCnt + ")");
	}
	
	// Unable to parse data
	public DataEntryException(NumberFormatException e) {
		super("Data Entry Error! A data entry has an incorrect type for one of its fields.");
	}
	
	// Input is not within the bounds of the options
	public DataEntryException(int start, int end) {
		super("Option Index Out of Bounds! Your input must be within the range of " + start + " to " + end + ".");
	}
}
