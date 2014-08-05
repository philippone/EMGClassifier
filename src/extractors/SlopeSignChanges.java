package extractors;

import data.FeatureVector;
import data.Sample;

public class SlopeSignChanges implements SingleFeatureExtractor {

	// TODO
	double threshold = 30;

	@Override
	public FeatureVector extract(Sample sample) {
		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(slopeSignChanges(signal));
		}

		return fv;
	}

	private double slopeSignChanges(int[] signal) {

		double counter = 0;

		for (int k = 1; k < signal.length - 1; k++) {
			int a = signal[k] - signal[k - 1];
			int b = signal[k] - signal[k + 1];

			if (a * b >= threshold)
				counter++;
		}

		// scale: divide by signal size/2 (slope change every signal)
		return counter /= (signal.length /2);
	}

}
