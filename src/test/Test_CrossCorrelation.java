package test;

import java.util.ArrayList;
import java.util.List;

import persistence.DataReaderWriter;
import data.LabeledFeatureVector;
import data.TrainData;
import extractors.MeanAbsoluteValue;
import extractors.SignalToFeatureConverter;
import extractors.SimpleSquareIntegral;
import extractors.SingleFeatureExtractor;
import extractors.SlopeSignChanges;
import extractors.Variance;
import extractors.WaveformLength;
import extractors.WillsonAmplitude;

public class Test_CrossCorrelation {

	private static ArrayList<SingleFeatureExtractor> extractors;
	private static float granularity = 0.1f;
	private static int windowSize = 30;

	private static double gammaStart = 1.9002;
	private static double gammaEnd = 1.9005;
	private static double gammaGranularity = 0.0001;
	private static double CStart = 124;
	private static double CEnd = 126;
	private static double CGranularity = 1;

	static {

		extractors = new ArrayList<SingleFeatureExtractor>();

		// MAV
		MeanAbsoluteValue mav = new MeanAbsoluteValue();
		extractors.add(mav);

		// WAMP
		WillsonAmplitude wamp = new WillsonAmplitude();
		extractors.add(wamp);

		// Varaince
		Variance v = new Variance();
		extractors.add(v);

		// WaveformLength
		WaveformLength wfl = new WaveformLength();
		extractors.add(wfl);

		// SlopeSignChanges
		SlopeSignChanges ssc = new SlopeSignChanges();
		extractors.add(ssc);

		// SimpleSquareIntegral
		SimpleSquareIntegral ssi = new SimpleSquareIntegral();
		extractors.add(ssi);

	}

	public final static void main(String[] args) {

		// parse Signal to LableledFeatureVector
		 List<LabeledFeatureVector> trainSamples =
		 DataReaderWriter.getTrainingLabledFeatureVectors(windowSize,
		 extractors);

//		List<LabeledFeatureVector> trainSamples = new ArrayList<LabeledFeatureVector>();
//		List<TrainData> trainingData = DataReaderWriter.readData();
//		for (TrainData trainData : trainingData) {
//			trainSamples.addAll(trainData.getData());
//		}

		// validate
		CrossCorrelation cc = new CrossCorrelation(trainSamples);

		cc.crossValidation(gammaStart, gammaEnd, gammaGranularity, CStart,
				CEnd, CGranularity);
	}

}
