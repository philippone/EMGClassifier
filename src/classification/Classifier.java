package classification;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import data.FeatureVector;
import data.LabeledFeatureVector;
import data.Sample;
import extractors.SignalToFeatureConverter;

public class Classifier {

	private svm_model model;
	private SignalToFeatureConverter converter;

	public Classifier(svm_model model) {
		this.model = model;
		converter = new SignalToFeatureConverter();
	}

	public Gesture classifySample(Sample s) {

		// extract features
		FeatureVector fv = convertSample(s);

		// evaluate/classify
		return evaluate(fv);

	}

	private FeatureVector convertSample(Sample s) {
		// extract features
		FeatureVector features = converter.convert(s);
		// cretate labeled feature vector
		FeatureVector lfv = new FeatureVector(features);
		return lfv;
	}

	public Gesture evaluate(FeatureVector fv) {
		return evaluate(Gesture.UNDEFINED, fv);
	}

	public Gesture evaluate(Gesture actual, FeatureVector fv) {
		double[] features = fv.getFeatures();

		svm_node[] nodes = new svm_node[features.length];
		for (int i = 1; i <= features.length; i++) {
			svm_node node = new svm_node();
			node.index = i;
			node.value = features[i - 1];

			nodes[i - 1] = node;
		}

		int nr_class = svm.svm_get_nr_class(model);

		int[] labels = new int[nr_class];
		svm.svm_get_labels(model, labels);
		double[] prob_estimates = new double[nr_class];

		double v1 = svm.svm_predict(model, nodes);
		double v = svm.svm_predict_probability(model, nodes, prob_estimates);

		for (int i = 0; i < nr_class; i++) {
			System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
		}
		System.out.println("Actual: " + actual + "; Prediction:" + v + ", "
				+ v1 + ")");

		return Gesture.labelToGesture(v1);
	}

}
