package classification;

import java.util.ArrayList;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import data.FeatureVector;
import data.LabeledFeatureVector;

public class Trainer {

	/** holds labled feature vector of training samples */
	ArrayList<LabeledFeatureVector> lfvList;
	SignalToFeatureConverter converter;

	public Trainer() {
		lfvList = new ArrayList<LabeledFeatureVector>();
		converter = new SignalToFeatureConverter();
	}

	
	public void addSample(Sample s, Gesture g) {
		// extract features
		LabeledFeatureVector lfv = convertSample(s,g);
		// add to training set
		lfvList.add(lfv);
	}
	
	private LabeledFeatureVector convertSample(Sample s, Gesture g) {
		// extract features
		FeatureVector features = converter.convert(s);
		// cretate labeled feature vector
		LabeledFeatureVector lfv = new LabeledFeatureVector(g, features);
		return lfv;
	}

	public svm_model createModel(ArrayList<LabeledFeatureVector> lfvList) {
		svm_problem prob = new svm_problem();
		int dataCount = lfvList.size();
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];

		for (int i = 0; i < dataCount; i++) {
			double[] features = lfvList.get(i).getFeatures();
			prob.x[i] = new svm_node[features.length - 1];
			for (int j = 1; j < features.length; j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = features[j];
				prob.x[i][j - 1] = node;
			}
			prob.y[i] = lfvList.get(i).getLabel();
		}

		svm_parameter param = new svm_parameter();
		param.probability = 1;
		param.gamma = 0.5;
		param.nu = 0.5;
		param.C = 1;
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.cache_size = 20000;
		param.eps = 0.001;

		svm_model model = svm.svm_train(prob, param);

		return model;
	}

}
