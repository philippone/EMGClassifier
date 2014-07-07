package classification;

import java.util.ArrayList;

public class Signal {
	
	private ArrayList<Integer> amplitude;
	private int v = 0;

	public Signal() {
		amplitude = new ArrayList<Integer>();
		setValue(0);
		
	}

	public Signal(int i) {
		amplitude = new ArrayList<Integer>();
		v = i;
	}

	public int getValue() {
		return v;
	}

	public void setValue(int v) {
		this.v = v;
	}

}
