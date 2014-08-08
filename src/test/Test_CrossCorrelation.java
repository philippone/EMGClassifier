package test;

import java.util.ArrayList;

import data.LabeledFeatureVector;
import extractors.SignalToFeatureConverter;
import extractors.SingleFeatureExtractor;

public class Test_CrossCorrelation {

	private static ArrayList<SingleFeatureExtractor> extractorList;
	private static float granularity = 0.1f;
	
	static {

	}

	public final static void main(int argc, String[] args) {

		// parse Signal to LableledFeatureVector
		ArrayList<LabeledFeatureVector> trainSamples = null;
		
		// validate
		CrossCorrelation cc = new CrossCorrelation(trainSamples);

		cc.crossValidation(granularity);
	}

}
