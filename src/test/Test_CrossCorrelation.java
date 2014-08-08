package test;

import java.util.ArrayList;

import data.LabeledFeatureVector;
import extractors.SignalToFeatureConverter;
import extractors.SingleFeatureExtractor;

public class Test_CrossCorrelation {

	private static ArrayList<SingleFeatureExtractor> extractorList;

	static {

	}

	public final static void main(int argc, String[] args) {

		// parse Signal to Samples
		ArrayList<LabeledFeatureVector> trainSamples = null;
		
		// extract features
		SignalToFeatureConverter converter = new SignalToFeatureConverter(extractorList);
		
		
		// validate
		new CrossCorrelation(trainSamples);

	}

}
