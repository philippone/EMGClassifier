package classification;

import java.util.ArrayList;
import java.util.LinkedList;

import classification.Config.CCTestParameter;
import data.Baseline;
import data.Sample;
import data.Window;

public class SampleRecognizer {

	private Baseline baseline = new Baseline(3, 100);
	private Window window = new Window(3, 10, Config.ONSET_THRESHOLD_H,
			Config.ONSET_THRESHOLD_M);
	private int recordTime = Config.CCTestParameter.getWindowSize() * 15;
	private boolean recording = false;
	private long startRecTime = 0;
	ArrayList<LinkedList<Integer>> sample = null;
	private boolean check = true;

	public interface ObservableSampleListener {
		public void notifySample(Sample s);
	}

	Manager manager;

	public SampleRecognizer(Manager m) {
		manager = m;
//		variances = new ArrayList<Double>();
	}

	public void recognizeSample(int... sig) {

		if (!recording) {
			// detect sample
			boolean detected = detect(sig);

			if (detected) {
				recording = true;
				startRecTime = System.currentTimeMillis();

//				sample = new ArrayList<ArrayList<Integer>>();
//				for (int i = 0; i < sig.length; i++) {
//					ArrayList<Integer> sensor = new ArrayList<Integer>();
//					sample.add(sensor);
//				}

			}
		} else {
//			System.out.println("recording");
			// record x-ms and send back to manager
			if (System.currentTimeMillis() - startRecTime < recordTime) {
				for (int i = 0; i < sig.length; i++) {
					sample.get(i).add(sig[i]);
					// System.out.println("add to sample " + i + ": " + sig[i]);
				}

			} else {
//				System.out.println("stop recording");
				// stop recording
				recording = false;

				// notify manager
				manager.notifySample(new Sample(sample));
//				System.out.println("notify Sample");
				sample = null;
			}

		}

	}

//	private ArrayList<Double> variances;

	/*
	 * detect sample if all signals bigger than 0
	 */
	private boolean detect(int... sig) {

		if (baseline.isFilled()) {
			
			System.out.print(check ? "filled\n" : "");
			check = false;
			
			if (Config.ONSET_MOVING_BASELINE) {

				 baseline.update(sig);
			}
			
			int onset = window.update(sig, baseline.getVar());
			if (onset != Integer.MAX_VALUE && onset >= 0 && onset <= window.getWindowSize()) {
				
				ArrayList<LinkedList<Integer>> signalsFromOnset = window.getSignalsFromOnset(onset);
				sample = signalsFromOnset;
				return true;
				
			}

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

		sample = new ArrayList<LinkedList<Integer>>();
		for (int i = 0; i < 3; i++) {
			LinkedList<Integer> sensor = new LinkedList<Integer>();
			sample.add(sensor);
		}
	}

}
