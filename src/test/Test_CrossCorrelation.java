package test;

import java.util.ArrayList;

import data.LabeledFeatureVector;
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

	public final static void main(int argc, String[] args) {

		// parse Signal to LableledFeatureVector
		ArrayList<LabeledFeatureVector> trainSamples = null;

		// validate
		CrossCorrelation cc = new CrossCorrelation(trainSamples);

		cc.crossValidation(granularity);
	}

}