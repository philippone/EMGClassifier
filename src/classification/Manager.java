package classification;

import libsvm.svm_model;
import gui.EMGClassifierGUI;
import classification.SampleRecognizer.ObservableSampleListener;
import classification.SignalReader.ObservableSignalListener;
import data.Sample;
import data.Signal;

public class Manager implements ObservableSignalListener,
		ObservableSampleListener {

	private SignalReader signalReader;
	private SampleRecognizer sampleRecog;
	private Mode mode;
	private Trainer trainer;

	EMGClassifierGUI gui;
	private Classifier classifier;
	private long time = 0;

	public void setGui(EMGClassifierGUI gui) {
		this.gui = gui;
	}

	public Manager() {
		mode = Mode.IDLE;

		// read signal from sensors
		signalReader = new SignalReader(this);
		sampleRecog = new SampleRecognizer(this);

		// init trainer
		trainer = new Trainer();
	}

	/**
	 * notify manager when new signal appears
	 * */
	@Override
	public void notifySignal(int... sig) {
		System.out.println("sig1 " + sig[0]);
		System.out.println("sig2 " + sig[1]);
		System.out.println("sig3 " + sig[2]);

		// notify gui
		
		gui.notify(new Signal(sig[0]), new Signal(sig[1]), new Signal(sig[2]));

		// forward Signals to SampleRecognizer
		sampleRecog.recognizeSample(sig);
	}

	/**
	 * manager will be notified by SampleRecognizer if she recognized a sample
	 * */
	public void notifyManager(Signal sig1, Signal sig2, Signal sig3) {
		// System.out.println("sig1 " + sig1.getValue());
		// System.out.println("sig2 " + sig2.getValue());
		// System.out.println("sig3 " + sig3.getValue());

		long t = System.currentTimeMillis();
		//
		if ( t - time > 15) {
			time  = System.currentTimeMillis();
			gui.notify(sig1, sig2, sig3);
		}
	}

	@Override
	public void notifySample(Sample s) {

		if (mode == Mode.IDLE) {
			// TODO

		} else if (mode == Mode.TRAINING) {
			// forward Sample to Trainer
			// TODO
			Gesture currentGesture = gui.getCurrentGesture();
			trainer.addSample(s, currentGesture);

		} else if (mode == Mode.CLASSIFYING) {
			// forward Sample to Classifier
			Gesture g = classifier.classifySample(s);
			gui.showClassifiedGesture(g);
			// TODO notify gui, invoke actoin etc.
		}

	}

	public void changeToClassifyMode() {
		mode = Mode.CLASSIFYING;
		svm_model svm_model = trainer.createModel();
		classifier = new Classifier(svm_model);
	}

	public void changeToTrainMode() {
		mode = Mode.TRAINING;
	}

	public void changeToIdleMode() {
		mode = Mode.IDLE;
	}

	public void changeToRecordMode() {
		mode = Mode.RECORDING;
	}

	public void setDetection() {
		sampleRecog.setDetection();
	}


}
