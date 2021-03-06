package extractors;

import data.FeatureVector;
import data.Sample;

public class Variance implements SingleFeatureExtractor {

	@Override
	public FeatureVector extract(Sample sample) {
		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(var(signal));
		}

		return fv;
	}

	private double var(int[] signal) {

		int n = signal.length;

		// compute average x'
		double avg = 0;
		for (int i = 0; i < n; i++) {
			avg += signal[i];
		}
		avg /= n;
		
		// compute variance
		double x = 0;
		for (int i = 0; i < n; i++) {
			x += Math.pow(signal[i] - avg, 2); 
		}
		x /= n-1;

		
		// TODO scale
		return x /= (1024 * 1024) ;
	}
}
