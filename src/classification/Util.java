package classification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import libsvm.svm_model;
import persistence.DataReaderWriter;
import test.CrossCorrelation;
import data.FeatureVector;
import data.LabeledFeatureVector;

public class Util {

	public static boolean divideTrainingClassify = false;

	public static svm_model getTraingingModel(File trainingFile) {

		// parse Signal to LableledFeatureVector
		List<LabeledFeatureVector> trainSamples = DataReaderWriter
				.getTrainingLabledFeatureVectors(
						Config.CCTestParameter.getWindowSize(),
						Config.CCTestParameter.getExtractors(), trainingFile);

		// List<LabeledFeatureVector> trainSamples = new
		// ArrayList<LabeledFeatureVector>();
		// List<TrainData> trainingData = DataReaderWriter.readData();
		// for (TrainData trainData : trainingData) {
		// trainSamples.addAll(trainData.getData());
		// }

		// validate
		CrossCorrelation cc = new CrossCorrelation(trainSamples);

		double[] crossValidation = cc.crossValidation(
				Config.CCTestParameter.getGammaStart(), Config.CCTestParameter.getGammaEnd(),
				Config.CCTestParameter.getGammaGranularity(),
				Config.CCTestParameter.getCStart(), Config.CCTestParameter.getCEnd(),
				Config.CCTestParameter.getCGranularity(),
				Config.CCTestParameter.getFoldingFactor());

		Trainer trainer = new Trainer();

		ArrayList<LabeledFeatureVector> trainList = new ArrayList<LabeledFeatureVector>();
		ArrayList<LabeledFeatureVector> classifyList = new ArrayList<LabeledFeatureVector>();

		if (divideTrainingClassify) {
			for (int i = 0; i < trainSamples.size(); i++) {
				if (i % 2 == 0)
					trainList.add(trainSamples.get(i));
				else
					classifyList.add(trainSamples.get(i));
			}

			trainer.lfvList.addAll(trainList);
		} else {
			trainer.lfvList.addAll(trainSamples);
		}
		svm_model createModel = trainer.createModel(crossValidation[0],
				crossValidation[1]);

		Classifier classifier = new Classifier(createModel);

		float a = 0;
		if (divideTrainingClassify) {

			for (LabeledFeatureVector v : classifyList) {
				Gesture g = classifier.evaluate(
						Gesture.labelToGesture(v.getLabel()),
						new FeatureVector(v.getFeatures()));

				if (g == Gesture.labelToGesture(v.getLabel())) {
					a++;
				}
			}
			System.out.println("Accuracy: " + a + " " + trainList.size() + ":"
					+ a / trainList.size() * 100);
		}
		return createModel;

	}
}
