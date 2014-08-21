package test;

import java.util.List;

import classification.Config;
import persistence.DataReaderWriter;
import data.LabeledFeatureVector;
import extractors.WillsonAmplitude;

public class Test_CrossCorrelation {

	public final static void main(String[] args) {

		// parse Signal to LableledFeatureVector
		List<LabeledFeatureVector> trainSamples = DataReaderWriter
				.getTrainingLabledFeatureVectors(
						Config.CCTestParameter.windowSize,
						Config.CCTestParameter.extractors, null);

		// List<LabeledFeatureVector> trainSamples = new
		// ArrayList<LabeledFeatureVector>();
		// List<TrainData> trainingData = DataReaderWriter.readData();
		// for (TrainData trainData : trainingData) {
		// trainSamples.addAll(trainData.getData());
		// }

		// validate
		CrossCorrelation cc = new CrossCorrelation(trainSamples);

		cc.crossValidation(Config.CCTestParameter.gammaStart,
				Config.CCTestParameter.gammaEnd,
				Config.CCTestParameter.gammaGranularity,
				Config.CCTestParameter.CStart, Config.CCTestParameter.CEnd,
				Config.CCTestParameter.CGranularity,
				Config.CCTestParameter.foldingFactor);
	}

}
