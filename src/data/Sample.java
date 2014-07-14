package data;

import java.util.ArrayList;

public class Sample {

	private int size;
	private int[][] signals;

	public Sample(int[]... nSignals) {
		setSignals(nSignals);
		size = nSignals.length;
	}

	public Sample(int length) {
		size = length;
	}

	public Sample(ArrayList<ArrayList<Integer>> sample) {
		signals = new int[sample.size()][];

		for (int i = 0; i < sample.size(); i++) {

			ArrayList<Integer> sig = sample.get(i);

			signals[i] = new int[sig.size()];

			for (int j = 0; j < sig.size(); j++) {
				signals[i][j] = sig.get(j);
			}
		}
	}
	
	
	public int[] getSignal(int sensor) {
		return getSignals()[sensor];
	}

	public int[][] getSignals() {
		return signals;
	}

	public void setSignals(int[][] signals) {
		this.signals = signals;
	}

}
