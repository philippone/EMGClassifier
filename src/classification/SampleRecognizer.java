package classification;

import java.util.ArrayList;

import data.Sample;

public class SampleRecognizer {

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
			// record x-ms and send back to manager
			if (System.currentTimeMillis() - startRecTime < recordTime) {
				for (int i = 0; i < sig.length; i++) {
					sample.get(i).add(sig[i]);
				}

			} else {
				// stop recording
				recording = false;

				// notify manager
				manager.notifySample(new Sample(sample));
				sample = null;
			}

		}

	}

	/*
	 * detect sample if all signals bigger than 0
	 */
	private boolean detect(int... sig) {
		// if all signals > 0

		
//		for (int i = 0; i < sig.length; i++) {
//			if (sig[i] <= 0)
//				return false;
//		}
		return false;
	}

	public void setDetection() {
		recording = true;
		startRecTime = System.currentTimeMillis();

		sample = new ArrayList<ArrayList<Integer>>();
	}

}
