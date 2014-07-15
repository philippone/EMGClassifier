package extractors;

import java.util.ArrayList;

import data.FeatureVector;
import data.Sample;

public class SignalToFeatureConverter {
	
	ArrayList<SingleFeatureExtractor> extractors;

	public SignalToFeatureConverter() {
		extractors = new ArrayList<SingleFeatureExtractor>();
		addExtractors();
	}

	/*
	 * init SingleFeatureExtractor and add to list
	 * */
	private void addExtractors() {
		// add extractors
		
		//MAV
		MeanAbsoluteValue mav = new MeanAbsoluteValue();
		extractors.add(mav);
		
		//WAMP
		WillsonAmplitude wamp = new WillsonAmplitude();
		extractors.add(wamp);
	}

	public FeatureVector convert(Sample sample) {
		System.out.println("Converter convert");
		
		FeatureVector fv = new FeatureVector();
		
		
		// ruft alle extractor auf und erstellt den kompletten FeatureVecotr
		for (int i = 0; i < extractors.size(); i++) {
			System.out.println("converter execute all extractors");
			SingleFeatureExtractor ex = extractors.get(i);
			fv.add(ex.extract(sample));
		}
		
		return fv;
		
	}

}
