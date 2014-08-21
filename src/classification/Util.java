package classification;

import java.io.File;
import java.util.List;

import classification.Config.CCTestParameter;
import persistence.DataReaderWriter;
import test.CrossCorrelation;
import data.LabeledFeatureVector;
import libsvm.svm_model;

public class Util {

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

		trainer.lfvList.addAll(trainSamples);
		svm_model createModel = trainer.createModel(crossValidation[0], crossValidation[1]);
		
		return createModel;

	}

}
