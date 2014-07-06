package data;

public class LabeledFeatureVector extends FeatureVector {
	
	private String label;

	public String getLabel() {
		return label;
	}

	public LabeledFeatureVector(String label, double... features) {
		super(features);
		this.label = label;
		
	}

}
