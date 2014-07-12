package extractors;

import data.FeatureVector;
import data.Sample;

public class WillsonAmplitude implements SingleFeatureExtractor {

	// TODO
	private int threshold = 30;

	@Override
	public FeatureVector extract(Sample sample) {

		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(wamp(signal));
		}

		return fv;
	}

	private double wamp(int[] signal) {
		int sum = 0;
		for (int i = 0; i < signal.length - 1; i++) {
			double val = Math.abs(signal[i] - signal[i + 1]);

			if (val > threshold)
				sum += 1;
		}

		// TODO scale sum

		return sum;
	}

}
