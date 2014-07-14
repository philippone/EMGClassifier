package data;

import java.util.Arrays;

import classification.Gesture;

public class FeatureVector {

	protected double[] features;

	public double[] getFeatures() {
		return features;
	}

	public FeatureVector(double... features) {
		this.features = features;

	}

	public FeatureVector(FeatureVector features) {
		this.features = features.getFeatures();
	}

	public void add(FeatureVector fv) {
		features = concat(this.features, fv.getFeatures());
	}

	public void add(double... feature) {
		features = concat(this.features, features);

	}

	public double[] concat(double[] first, double[] second) {
		double[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	@Override
	public String toString() {
		String s = "? : [ ";
		for (int i = 0; i < features.length; i++) {
			if (i < features.length - 1)
				s += " " + features[i] + ", ";
			else
				s += " " + features[i];
		}
		s += " ]";

		return s;
	}

}
