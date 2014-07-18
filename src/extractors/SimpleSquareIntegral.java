package extractors;

import data.FeatureVector;
import data.Sample;

public class SimpleSquareIntegral implements SingleFeatureExtractor {

	@Override
	public FeatureVector extract(Sample sample) {
		FeatureVector fv = new FeatureVector();

		for (int[] signal : sample.getSignals()) {
			fv.add(integrate(signal));
		}

		return fv;
	}

	private double integrate(int[] signal) {

		double integr = 0;
		
		for (int s = 0; s < signal.length; s++) {
			integr += Math.pow(signal[s], 2);
		}
	
		
		// scale: divide by maximum: (signalsize(1024)^2) * lentgh
		return integr /= (signal.length * 1048576);
	}

}
