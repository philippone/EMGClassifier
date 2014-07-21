package extractors;

import data.FeatureVector;
import data.Sample;

public class MeanAbsoluteValueSlope implements SingleFeatureExtractor {

	// TODO
	private int segments = 4;

	@Override
	public FeatureVector extract(Sample sample) {

		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(computeMAVSLP(signal));
		}
		return fv;
	}

	private double computeMAVSLP(int[] signal) {

		int segSize = (int) Math.floor(signal.length / segments);

		double mavslp = 0;

		for (int i = 0; i < segments - 1; i++) {

			for (int j = 0; j < signal.length; j++) {
				int a_start = (i + 1) * segSize;
				int a_end = (i + 2) * segSize;

				int b_start = i * segSize;
				int b_end = (i + 1) * segSize;

				double a = computeMAV(subSignal(signal, a_start, a_end));
				double b = computeMAV(subSignal(signal, b_start, a_end));

				// TODO each value is a feature??
				// kann schief gehen wenn signale unterschiedlich lang und somit
				// unterschiedlich viele segmente
				mavslp += (a - b);
			}
		}

		return mavslp;
	}

	private double computeMAV(int[] signal) {
		double avg = 0;
		for (int i : signal) {
			avg += i;
		}

		avg /= signal.length;

		// scale
		double scale = 1024 * signal.length;

		return avg /= scale;
	}

	/*
	 * return sequence from start to end (included) of a signal *
	 */
	private int[] subSignal(int[] signal, int start, int end) {
		int[] subsig = new int[end - start + 1];

		for (int i = 0; i < subsig.length; i++) {
			subsig[i] = signal[start + i];
		}

		return subsig;
	}

}
