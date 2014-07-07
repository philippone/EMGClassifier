package data;

import classification.Gesture;

public class LabeledFeatureVector extends FeatureVector {
	
	private double label;

	public double getLabel() {
		return label;
	}

	public LabeledFeatureVector(double label, double... features) {
		super(features);
		this.label = label;
		
	}

	public LabeledFeatureVector(Gesture g, FeatureVector features) {
		super(features.getFeatures());
		//TODO
		this.label = -1; //TODO
		//TODO
	}

}
