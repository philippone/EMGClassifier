package classification;

import java.util.ArrayList;

import libsvm.svm_model;
import data.LabeledFeatureVector;

public class Trainer {

	/** holds labled feature vector of training samples */
	ArrayList<LabeledFeatureVector> lfvList;

	public Trainer() {
		lfvList = new ArrayList<LabeledFeatureVector>();
	}

	public LabeledFeatureVector convertSample(Sample s, Gesture g) {
		// TODO
		return null;
	}

	public svm_model createModel(ArrayList<LabeledFeatureVector> lfvList) {
		return null;
	}

}
