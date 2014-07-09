package extractors;

import data.FeatureVector;
import data.Sample;

public class WaveformLength implements SingleFeatureExtractor {

	@Override
	public FeatureVector extract(Sample sample) {
		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(computeLength(signal));
		}
		return fv;
	}

	/*
	 * compute waveform length of signal
	 * **/
	private double computeLength(int[] signal) {

		double l = 0;

		for (int i = 1; i < signal.length; i++) {
			l += Math.abs(signal[i] - signal[i - 1]);
		}

		// TODO scale
		return l;
	}

}
