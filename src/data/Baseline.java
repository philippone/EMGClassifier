package data;

import java.util.ArrayList;
import java.util.LinkedList;

public class Baseline {

	private double counter;
	private int windowSize;
	private ArrayList<LinkedList<Integer>> signals;
	private double[] avg;
	public int getWindowSize() {
		return windowSize;
	}

	public double[] getAvg() {
		return avg;
	}

	public double[] getVar() {
		return var;
	}

	private double[] var;
	private int sensorSize;

	public Baseline(int sensorSize, int windowSize) {

		this.windowSize = windowSize;
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

		if (signals.get(0).size() < windowSize ) {
			for (int i = 0; i < sensorSize; i++) {
				signals.get(i).add(sig[i]);
				updateAverage(i, 0, sig[i]);
				if (signals.get(0).size() == windowSize) {
					updateVariance(i);
				}
			}

		} else {
			for (int i = 0; i < sensorSize; i++) {
				// remove first
				int remove = signals.get(i).poll();
				// add to the end
				signals.get(i).add(sig[i]);
				
				updateAverage(i, remove, sig[i]);
				updateVariance(i);
			}
		}
	}

	private void updateAverage(int i, int removed, int newsig) {

//		for (int i = 0; i < sig.length; i++) {
			avg[i] += (double) newsig / (double) windowSize;
			avg[i] -= (double) removed / (double) windowSize;
//		}

	}
	
	private void updateVariance(int i){
		
		LinkedList<Integer> signal = signals.get(i);
		double var = 0;
		for (Integer integer : signal) {
			var += Math.pow((integer - avg[i]), 2);
		}
		this.var[i] = var / (double) windowSize;
	}
	
	public boolean isFilled(){
		return (signals.get(0).size() >= windowSize);
	}

}
