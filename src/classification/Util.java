package classification;

import java.io.File;
import java.util.List;

import persistence.DataReaderWriter;
import test.CrossCorrelation;
import test.Test_CrossCorrelation.CCTestParameter;
import data.LabeledFeatureVector;
import libsvm.svm_model;

public class Util {

	public static svm_model getTraingingModel(File trainingFile) {

		// parse Signal to LableledFeatureVector
		List<LabeledFeatureVector> trainSamples = DataReaderWriter
				.getTrainingLabledFeatureVectors(
						CCTestParameter.getWindowSize(),
						CCTestParameter.getExtractors(), trainingFile);

		// List<LabeledFeatureVector> trainSamples = new
		// ArrayList<LabeledFeatureVector>();
		// List<TrainData> trainingData = DataReaderWriter.readData();
		// for (TrainData trainData : trainingData) {
		// trainSamples.addAll(trainData.getData());
		// }

		// validate
		CrossCorrelation cc = new CrossCorrelation(trainSamples);

		double[] crossValidation = cc.crossValidation(
				CCTestParameter.getGammaStart(), CCTestParameter.getGammaEnd(),
				CCTestParameter.getGammaGranularity(),
				CCTestParameter.getCStart(), CCTestParameter.getCEnd(),
				CCTestParameter.getCGranularity(),
				CCTestParameter.getFoldingFactor());

		Trainer trainer = new Trainer();

		trainer.lfvList.addAll(trainSamples);
		svm_model createModel = trainer.createModel(crossValidation[0], crossValidation[1]);
		
		return createModel;

	}

}
