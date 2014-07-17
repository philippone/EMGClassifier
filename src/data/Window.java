package data;

import java.util.ArrayList;
import java.util.LinkedList;

public class Window {

	private int windowSize;
	private int sensorSize;
	private ArrayList<LinkedList<Integer>> signals;
	private ArrayList<LinkedList<Boolean>> bonatoSigns;
	private int[] cumulativeSignsCounter;
	private double threshold_m;
	private double threshold_h;

	public Window(int sensorSize, int windowSize,double threshold_h, double threshold_m) {
		this.sensorSize = sensorSize;
		this.windowSize = windowSize;
		this.threshold_h = threshold_h;
		this.threshold_m = threshold_m;
		
		cumulativeSignsCounter = new int[sensorSize];

		signals = new ArrayList<LinkedList<Integer>>();
		for (int i = 0; i < sensorSize; i++) {

			signals.add(new LinkedList<Integer>());
			bonatoSigns.add(new LinkedList<Boolean>());

		}

	}

	public void update(int[] sig, double[] variances) {

		for (int i = 0; i < sig.length; i++) {
			signals.get(i).add(sig[i]);
		}

		if (isFilled()) {

			for (int i = 0; i < sig.length; i++) {

				signals.get(i).poll();
				applyBonatoTest(variances);
				
			}

		}

	}

	private boolean isFilled() {

		return signals.get(0).size() >= windowSize;

	}

	public void applyBonatoTest(double[] variances) {
		
		int i = signals.get(0).size() / 2;
		int onset = Integer.MAX_VALUE;
		
		for (int k = 0; k < sensorSize; k++) {
			
			bonatoSigns.get(k).clear();
			cumulativeSignsCounter[k] = 0;
			
			for (int j = 0; j < i; j++) {
				
				int j_k1 = signals.get(k).get(j*2);
				int j_k2 = signals.get(k).get((j*2)+1);
				
				double g_j = Math.pow((j_k1 + j_k2), 2) / variances[k];
				
				if (g_j > this.threshold_h) {
					
					bonatoSigns.get(k).add(true);
					cumulativeSignsCounter[k]++;
					
				} else {
					
					bonatoSigns.get(k).add(false);
					cumulativeSignsCounter[k] = 0;
					
				}
						
			}
			
			
			for (int j = 0; j < sensorSize; j++) {
				
				if (cumulativeSignsCounter[j] > threshold_m) {
				
					//onset detected
					for (int j2 = 0; j2 < i; j2++) {
						
						if (bonatoSigns.get(k).get(j2)) {

							if (j2*2 < onset) {
								onset = j2*2;
								
							}
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		

	}

}
