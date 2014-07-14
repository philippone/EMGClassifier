package extractors;

import data.FeatureVector;
import data.Sample;

public class MeanAbsoluteValue implements SingleFeatureExtractor {

	@Override
	public FeatureVector extract(Sample sample) {
		System.out.println("MAV extract " + sample.getSignals().length);

		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			System.out.println("MAV each sample");
			fv.add(computeAverage(signal));
		}

		return fv;
	}

	private double computeAverage(int[] signal) {
		System.out.println("MAV compute");
		double avg = 0;
		for (int i : signal) {
			avg += i;
		}
		return avg / signal.length;
	}

}
