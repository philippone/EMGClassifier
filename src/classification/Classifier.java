package classification;

import libsvm.svm_model;
import data.FeatureVector;

public class Classifier {

	private svm_model model;
	
	public Classifier(svm_model model) {
		this.model = model;
	}
	
	public Gesture evaluate(Sample sample) {
		FeatureVector fv = convertSample(sample);
		// TODO
		return null;
	}

	private FeatureVector convertSample(Sample sample) {
		// TODO
		return null;
	}

}
