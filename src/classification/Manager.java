package classification;

import gui.EMGClassifierGUI;
import classification.SampleRecognizer.ObservableSampleListener;
import classification.SignalReader.ObservableSignalListener;


public class Manager implements ObservableSignalListener,
		ObservableSampleListener {

	private SignalReader signalReader;
	private SampleRecognizer sampleRecog;

	EMGClassifierGUI gui;

	public void setGui(EMGClassifierGUI gui) {
		this.gui = gui;
	}

	public Manager() {
		// read signal from sensors
		signalReader = new SignalReader(this);
		sampleRecog = new SampleRecognizer(this);
		// FeatureExtractor

		// classify
	}

	/**
	 * notify manager when new signal appears
	 * */
	@Override
	public void notifySignal(Signal... sig) {
		System.out.println("sig1 " + sig[0].getValue());
		System.out.println("sig2 " + sig[1].getValue());
		System.out.println("sig3 " + sig[2].getValue());

		// notify gui
		gui.notify(sig[0], sig[1], sig[2]);
		// forward Signals to SampleRecognizer
		sampleRecog.recognizeSample(sig);
	}

	/**
	 * manager will be notified by SampleRecognizer if she recognized a sample
	 * */
	public void notifyManager(Signal sig1, Signal sig2, Signal sig3) {
		System.out.println("sig1 " + sig1.getValue());
		System.out.println("sig2 " + sig2.getValue());
		System.out.println("sig3 " + sig3.getValue());
		
		gui.notify(sig1, sig2, sig3);
		
	}

	@Override
	public void notifySample(Sample s) {
		// TODO Auto-generated method stub
		
	}


}
