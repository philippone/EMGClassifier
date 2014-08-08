package test;

import java.util.List;

import libsvm.svm;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import data.LabeledFeatureVector;

public class CrossCorrelation {

	private List<LabeledFeatureVector> trainSamples;
	private double[] labels;
	private svm_problem problem;

	public CrossCorrelation(List<LabeledFeatureVector> trainSamples) {
		this.trainSamples = trainSamples;

		problem = createProblem(trainSamples);

	}

	private svm_problem createProblem(List<LabeledFeatureVector> lfvList) {
		svm_problem prob = new svm_problem();
		int dataCount = lfvList.size();
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];

		for (int i = 0; i < dataCount; i++) {
			double[] features = lfvList.get(i).getFeatures();
			prob.x[i] = new svm_node[features.length - 1];
			for (int j = 0; j < features.length; j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = features[j];
				prob.x[i][j] = node;
			}
			prob.y[i] = lfvList.get(i).getLabel();
		}

		// save label array
		this.labels = prob.y;

		return prob;
	}

	private svm_parameter createParam(float gamma, float nu, float C) {
		svm_parameter param = new svm_parameter();
		param.probability = 1;
		param.gamma = gamma;// 0.5;
		param.nu = nu;// 0.5;
		param.C = C;// 0.8;
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.cache_size = 20000;
		param.eps = 0.001;

		return param;

	}

	public void crossValidation(float granularity) {

		float gammaMax = 1.0f;
		float CMax = 1.0f;

		for (float gamma = 0; gamma < gammaMax; gamma += granularity) {
			for (float C = 0; C < CMax; C += granularity) {

				double[] target = new double[problem.l];

				// create params
				svm_parameter param = createParam(gamma, 0.5f, C);

				// exec cros validation
				svm.svm_cross_validation(problem, param, 10, target);

				// how much are correct
				float v = validate(labels, target);

				// sysout
				output(v, param);
			}
		}

	}

	/**
	 * check validated results against training (real) gestures
	 * 
	 * */
	private float validate(double[] train, double[] validated) {

		if (train.length != validated.length) {
			System.out.println("Validation failed - not same length");
			return -1;
		}

		float correct = 0;

		for (int i = 0; i < validated.length; i++) {
			if (validated[i] == train[i])
				correct++;
		}

		return correct / validated.length;
	}

	private void output(float v, svm_parameter param) {
		System.out.println("Validation: " + v * 100 + " (" + param.toString()
				+ ")");
	}
}
