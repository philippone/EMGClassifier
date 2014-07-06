package classification;

import gui.EMGClassifierGUI;
import classification.SampleRecognizer.ObservableSampleListener;
import classification.SignalReader.ObservableSignalListener;

public class Manager implements ObservableSignalListener,
		ObservableSampleListener {

	EMGClassifierGUI gui;

	public void setGui(EMGClassifierGUI gui) {
		this.gui = gui;
	}

	SignalReader signalReader;

	public Manager() {
		// read signal from sensors
		signalReader = new SignalReader(this);

		// FeatureExtractor

		// classify

		// Classifier classify = new Classifier();
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

	}

	/**
	 * manager will be notified by SampleRecognizer if she recognized a sample
	 * */
	@Override
	public void notifySample(Sample s) {
		// TODO forward Sample to Trainer or Classifier

	}

}
