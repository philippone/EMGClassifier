package classification;

import java.util.ArrayList;

import data.Baseline;
import data.Sample;
import data.Window;

public class SampleRecognizer {

	// Bonato Threshold for test-function
	private final static double BONATO_THRESHOLD_H = 0;

	// Bonato Threshold for M consecutive signal pairs
	private final static double BONATO_THRESHOLD_M = 0;

	private final static boolean BONATO_MOVING_BASELINE = false;

	private Baseline baseline = new Baseline(3, 100);
	private Window window = new Window(3, 10, BONATO_THRESHOLD_H,
			BONATO_THRESHOLD_M);
	private int recordTime = 400;
	private boolean recording = false;
	private long startRecTime = 0;
	ArrayList<ArrayList<Integer>> sample = null;

	public interface ObservableSampleListener {
		public void notifySample(Sample s);
	}

	Manager manager;

	public SampleRecognizer(Manager m) {
		manager = m;
		variances = new ArrayList<Double>();
	}

	public void recognizeSample(int... sig) {

		if (!recording) {
			// detect sample
			boolean detected = detect(sig);

			if (detected) {
				recording = true;
				startRecTime = System.currentTimeMillis();

				sample = new ArrayList<ArrayList<Integer>>();
				for (int i = 0; i < sig.length; i++) {
					ArrayList<Integer> sensor = new ArrayList<Integer>();
					sample.add(sensor);
				}

			}
		} else {
			System.out.println("recording");
			// record x-ms and send back to manager
			if (System.currentTimeMillis() - startRecTime < recordTime) {
				for (int i = 0; i < sig.length; i++) {
					sample.get(i).add(sig[i]);
					// System.out.println("add to sample " + i + ": " + sig[i]);
				}

			} else {
				System.out.println("stop recording");
				// stop recording
				recording = false;

				// notify manager
				manager.notifySample(new Sample(sample));
				System.out.println("notify Sample");
				sample = null;
			}

		}

	}

	private ArrayList<Double> variances;

	/*
	 * detect sample if all signals bigger than 0
	 */
	private boolean detect(int... sig) {

		if (baseline.isFilled()) {
			
			if (BONATO_MOVING_BASELINE) {

				 baseline.update(sig);
			}
			
			window.update(sig, baseline.getVar());

		} else {

			baseline.update(sig);

		}

		// if all signals > 0

		// for (int i = 0; i < sig.length; i++) {
		// if (sig[i] <= 0)
		// return false;
		// }

		return false;
	}

	public void setDetection() {
		recording = true;
		startRecTime = System.currentTimeMillis();

		sample = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 3; i++) {
			ArrayList<Integer> sensor = new ArrayList<Integer>();
			sample.add(sensor);
		}
	}

}
