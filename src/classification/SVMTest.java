package classification;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

public class SVMTest {

	static double[][] train = new double[1500][];

	public SVMTest() {

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		setup();
		svm_model model = svmTrain();

		double[][] testing = positiveTest(false, true);
		for (int j = 0; j < testing.length; j++) {
			evaluate(testing[j], model);
		}

	}

	private static void setup() {
		for (int i = 0; i < train.length; i++) {
			if (i + 1 > 500 && i+1 <= 1000) {// (train.length / (1 / 3))) { // 50% positive
				double[] vals = { 2, 0, i + i + i, 0 };
				train[i] = vals;
			} else if (i + 1 > 1000) {// train.length / (2 / 3)) {
				double[] vals = { 1, 1000, 10000, 10000 };
				train[i] = vals;

			} else {
				double[] vals = { 0, 0, i - i - i - 2,0 }; // 50% negative
				train[i] = vals;
			}
		}
	}

	private static double[][] positiveTest(boolean positiveObjects,
			boolean mixed) {
		double[][] test = new double[15][];

		if (mixed) {
			for (int i = 0; i < test.length; i++) {
				if (i + 1 > (5) && i+1 <= 10) { // 50% positive
					double[] vals = { 2, 0, i + i, 0  };
					test[i] = vals;
				} else if (i + 1 > 10) {// train.length / (2 / 3)) {
					double[] vals = { 1, 1000, 1000, 1000 };
					test[i] = vals;
				} else {
					double[] vals = { 0, 0, i - i - i - 2, 0 }; // 50% negative
					test[i] = vals;
				}
			}
		} else {
			for (int i = 0; i < test.length; i++) {
				if (positiveObjects) {// 50% positive
					double[] vals = { 1, 0, i + i };
					test[i] = vals;
				} else {
					double[] vals = { 0, 0, i - i - i - 2 }; // 50% negative
					test[i] = vals;
				}
			}
		}
		return test;
	}

	private static svm_model svmTrain() {
		svm_problem prob = new svm_problem();
		int dataCount = train.length;
		prob.y = new double[dataCount];
		prob.l = dataCount;
		prob.x = new svm_node[dataCount][];

		for (int i = 0; i < dataCount; i++) {
			double[] features = train[i];
			prob.x[i] = new svm_node[features.length - 1];
			for (int j = 1; j < features.length; j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = features[j];
				prob.x[i][j - 1] = node;
			}
			prob.y[i] = features[0];
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

	public static double evaluate(double[] features, svm_model model) {
		svm_node[] nodes = new svm_node[features.length - 1];
		for (int i = 1; i < features.length; i++) {
			svm_node node = new svm_node();
			node.index = i;
			node.value = features[i];

			nodes[i - 1] = node;
		}

		int totalClasses = 3;
		int[] labels = new int[totalClasses];
		svm.svm_get_labels(model, labels);

		double[] prob_estimates = new double[totalClasses];
		double v = svm.svm_predict_probability(model, nodes, prob_estimates);

		for (int i = 0; i < totalClasses; i++) {
			System.out.print("(" + labels[i] + ":" + prob_estimates[i] + ")");
		}
		System.out.println("(Actual:" + features[0] + " Prediction:" + v + ")");

		return v;
	}

}
