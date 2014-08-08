package data;

import classification.Gesture;

public class SignalEntry {
	
	private boolean onset;
	private Gesture gesture;
	private int[] signals;

	public SignalEntry(boolean onset, Gesture gesture, int[] signals) {
		this.onset = onset;
		this.gesture = gesture;
		this.signals = signals;
	}

	@Override
	public String toString() {

		String csv = (onset ? "1" : "0") + ","
				+ (onset ? gesture.getValue() : "-1");

		for (int sig : signals) {

			csv += "," + sig;

		}
		return csv;
	}

}
