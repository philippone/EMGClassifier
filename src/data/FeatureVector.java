package data;

public class FeatureVector {
	
	private double[] features;
	
	public double[] getFeatures() {
		return features;
	}

	public FeatureVector(double...features) {
		this.features = features;
	
	}

}
