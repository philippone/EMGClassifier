package extractors;

import java.util.ArrayList;

import classification.Config;
import data.FeatureVector;
import data.Sample;

public class SignalToFeatureConverter {

	ArrayList<SingleFeatureExtractor> extractors = Config.CLASSIFICATION_EXTRACTORS;

	/**
	 * create converter with extractors specified in transfered list
	 * 
	 * */
	public SignalToFeatureConverter(
			ArrayList<SingleFeatureExtractor> extractorList) {
		extractors = extractorList;
	}

	/**
	 * create converter with extractors specified in this class
	 * 
	 * */
	public SignalToFeatureConverter() {
	}

	public FeatureVector convert(Sample sample) {
		// System.out.println("Converter convert");

		FeatureVector fv = new FeatureVector();

		// ruft alle extractor auf und erstellt den kompletten FeatureVecotr
		for (int i = 0; i < extractors.size(); i++) {
			// System.out.println("converter execute all extractors");
			SingleFeatureExtractor ex = extractors.get(i);
			fv.add(ex.extract(sample));
		}

		return fv;

	}

}
