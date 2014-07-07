package data;

import java.util.Arrays;

public class FeatureVector {

	private double[] features;

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

}
