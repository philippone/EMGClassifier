package data;

import java.util.ArrayList;
import java.util.LinkedList;

public class Baseline {

	public static int windowSize = 100;
	private double counter;
	private ArrayList<LinkedList<Integer>> signals;
	private double[] avg;
	private double[] var;
	private int sensorSize;

	public Baseline(int sensorSize) {

		this.sensorSize = sensorSize;
		counter = 1;

		signals = new ArrayList<LinkedList<Integer>>(sensorSize);
		for (int i = 0; i < sensorSize; i++) {
			signals.add(new LinkedList<Integer>());
		}
		avg = new double[sensorSize];
		var = new double[sensorSize];

	}

	public void update(int... sig) {

		if (signals.get(0).size() < windowSize) {
			for (int i = 0; i < sensorSize; i++) {
				signals.get(i).add(sig[i]);
			}

		} else {
			for (int i = 0; i < sensorSize; i++) {
				// remove first
				int remove = signals.get(i).poll();
				// add to the end
				signals.get(i).add(sig[i]);
				
				
				updateAverage(i, remove, sig[i]);
			}
		}
	}

	private void updateAverage(int... sig) {

		for (int i = 0; i < sig.length; i++) {
			avg[i] += sig[i] / counter;
		}

		counter++;
	}

}
