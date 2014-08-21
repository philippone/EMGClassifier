package classification;

import java.util.ArrayList;

import extractors.MeanAbsoluteValue;
import extractors.SimpleSquareIntegral;
import extractors.SingleFeatureExtractor;
import extractors.SlopeSignChanges;
import extractors.Variance;
import extractors.WaveformLength;

/**
 * @author Thomas
 * 
 */
public class Config {
	
	/**
	 * Common Parameter
	 */
	final static int EMG_RATE = 1;
    final static int WINDOW_SIZE = 450;	

	/**
	 * Onset Detection Parameter
	 */

	// Bonato Threshold for test-function
	final static double ONSET_THRESHOLD_H = 2;
	// Bonato Threshold for M consecutive signal pairs
	final static double ONSET_THRESHOLD_M = 4;
	final static boolean ONSET_MOVING_BASELINE = false;
	final static int ONSET_WINDOW_SIZE = WINDOW_SIZE / EMG_RATE;

	/**
	 * Classification Parameter
	 */

	final public static ArrayList<SingleFeatureExtractor> CLASSIFICATION_EXTRACTORS;

	static {

		CLASSIFICATION_EXTRACTORS = new ArrayList<SingleFeatureExtractor>();

		// // MAV
		MeanAbsoluteValue mav = new MeanAbsoluteValue();
		CLASSIFICATION_EXTRACTORS.add(mav);

		// // WAMP
		// WillsonAmplitude wamp = new WillsonAmplitude();
		// CLASSIFICATION_EXTRACTORS.add(wamp);
		//
		// Varaince
		Variance v = new Variance();
		CLASSIFICATION_EXTRACTORS.add(v);

		// WaveformLength
		WaveformLength wfl = new WaveformLength();
		CLASSIFICATION_EXTRACTORS.add(wfl);

		// SlopeSignChanges
		SlopeSignChanges ssc = new SlopeSignChanges();
		CLASSIFICATION_EXTRACTORS.add(ssc);

		// SimpleSquareIntegral
		SimpleSquareIntegral ssi = new SimpleSquareIntegral();
		CLASSIFICATION_EXTRACTORS.add(ssi);

	}

	/**
	 * Cross Correlation Parameter
	 * 
	 */
	public static class CCTestParameter {

		public static ArrayList<SingleFeatureExtractor> extractors = CLASSIFICATION_EXTRACTORS;
		public static int windowSize = ONSET_WINDOW_SIZE;
		public static double gammaStart = 0.6;
		public static double gammaEnd = 6;
		public static double gammaGranularity = 0.1;
		public static double CStart = 70;
		public static double CEnd = 140;
		public static double CGranularity = 1;
		public static int foldingFactor = 5;

		public static int getFoldingFactor() {
			return foldingFactor;
		}

		public static ArrayList<SingleFeatureExtractor> getExtractors() {
			return extractors;
		}

		public static int getWindowSize() {
			return windowSize;
		}

		public static double getGammaStart() {
			return gammaStart;
		}

		public static double getGammaEnd() {
			return gammaEnd;
		}

		public static double getGammaGranularity() {
			return gammaGranularity;
		}

		public static double getCStart() {
			return CStart;
		}

		public static double getCEnd() {
			return CEnd;
		}

		public static double getCGranularity() {
			return CGranularity;
		}

	}

}
