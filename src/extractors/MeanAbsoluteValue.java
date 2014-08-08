package extractors;

import data.FeatureVector;
import data.Sample;

public class MeanAbsoluteValue implements SingleFeatureExtractor {

	@Override
	public FeatureVector extract(Sample sample) {

		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(computeAverage(signal));
		}

		return fv;
	}

	private double computeAverage(int[] signal) {
		double avg = 0;
		for (int i : signal) {
			avg += i;
		}
		
		avg /= signal.length;
		
		// scale: divide by maximum
		return avg /= 1024;
	}

}
