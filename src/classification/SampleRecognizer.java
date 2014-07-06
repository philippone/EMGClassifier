package classification;

public class SampleRecognizer {

	public interface ObservableSampleListener {
		public void notifySample(Sample s);
	}

	Manager manager;
	
	public SampleRecognizer(Manager m) {
		manager = m;
	}

	public void recognizeSample(Signal[]... sig) {
		// TODO detect sample
		
		// notify manager if sample is detected
		manager.notifySample(new Sample(sig));
	}

}
