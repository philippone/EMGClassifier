package classification;

import java.util.ArrayList;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import data.FeatureVector;
import data.LabeledFeatureVector;
import data.Sample;
import extractors.SignalToFeatureConverter;

public class Trainer {

	/** holds labled feature vector of training samples */
	ArrayList<LabeledFeatureVector> lfvList;
	SignalToFeatureConverter converter;

	public Trainer() {
		lfvList = new ArrayList<LabeledFeatureVector>();
		converter = new SignalToFeatureConverter();
	}

	
	public void addSample(Sample s, Gesture g) {
		System.out.println("Trainer: add sample");
		// extract features
		LabeledFeatureVector lfv = convertSample(s,g);
		// add to training set
		lfvList.add(lfv);
		
		System.out.println("TrainVector " + lfv.toString());
	}
	
	private LabeledFeatureVector convertSample(Sample s, Gesture g) {
		// extract features
		FeatureVector features = converter.convert(s);
		// cretate labeled feature vector
		LabeledFeatureVector lfv = new LabeledFeatureVector(g, features);
		return lfv;
	}

	
	public svm_model createModel() {
		
		return createModel(0.5, 0.8);
		
	}
	
	public svm_model createModel(double gamma, double cost){
		
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
				node.value = features[j-1];
				prob.x[i][j - 1] = node;
			}
			prob.y[i] = lfvList.get(i).getLabel();
		}

		svm_parameter param = new svm_parameter();
		param.probability = 1;
		param.gamma = gamma;
		param.nu = 0.5;
		param.C = cost;
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.cache_size = 20000;
		param.eps = 0.001;

//		double[] target = new double[prob.l];
//		
//		svm.svm_cross_validation(prob, param, 10, target);
//		
//		for (int i = 0; i < target.length; i++) {
//			System.out.println("target " +  target[i]);
//		}
//		
//		
//		System.out.println("---------2---------");
//		param.gamma = 0.4;
//		param.C = 0.8;
//		svm.svm_cross_validation(prob, param, 10, target);
//		
//		for (int i = 0; i < target.length; i++) {
//			System.out.println("target " +  target[i]);
//		}
//		
//		
//		System.out.println("---------3---------");
//		param.gamma = 0.7;
//		param.C = 1.2;
//		svm.svm_cross_validation(prob, param, 10, target);
//		
//		for (int i = 0; i < target.length; i++) {
//			System.out.println("target " +  target[i]);
//		}
//		
//		
//		System.out.println("---------4---------");
//		param.gamma = 0.9;
//		param.C = 1.4;
//
//		svm.svm_cross_validation(prob, param, 10, target);
//		
//		for (int i = 0; i < target.length; i++) {
//			System.out.println("target " +  target[i]);
//		}
//		
//		System.out.println("---------5---------");
//		param.gamma = 0.8;
//		param.C = 1.4;
//
//		svm.svm_cross_validation(prob, param, 10, target);
//
//		
//		for (int i = 0; i < target.length; i++) {
//			System.out.println("target " +  target[i]);
//		}
//		
		svm_model model = svm.svm_train(prob, param);
		
		
//		for (int i = 0; i < model.label.length; i++) {
//			System.out.println("model label " + i + " " + model.label[i]);
//		}

		return model;
		
	}

}
