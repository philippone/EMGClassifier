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
		this.label = g.getValue();
	}

	@Override
	public String toString() {
		String s = Gesture.labelToGesture(label) + ": [ ";
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
