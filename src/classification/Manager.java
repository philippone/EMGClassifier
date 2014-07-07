package classification;

import libsvm.svm_model;
import gui.EMGClassifierGUI;
import classification.SampleRecognizer.ObservableSampleListener;
import classification.SignalReader.ObservableSignalListener;


public class Manager implements ObservableSignalListener,
		ObservableSampleListener {

	private SignalReader signalReader;
	private SampleRecognizer sampleRecog;
	private Mode mode;
	private Trainer trainer;

	EMGClassifierGUI gui;
	private Classifier classifier;
	

	public void setGui(EMGClassifierGUI gui) {
		this.gui = gui;
	}

	public Manager() {
		mode = Mode.IDLE;
		
		// read signal from sensors
		signalReader = new SignalReader(this);
		sampleRecog = new SampleRecognizer(this);

		// init trainer and classifier
		trainer = new Trainer();
//		classifier = new Classifier(model)
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
		System.out.println("sig1 " + sig1.getValue());
		System.out.println("sig2 " + sig2.getValue());
		System.out.println("sig3 " + sig3.getValue());
		
		gui.notify(sig1, sig2, sig3);
		
	}

	@Override
	public void notifySample(Sample s) {

		if (mode == Mode.IDLE) {

			
		} else if (mode == Mode.TRAINING) {
			// forward Sample to Trainer
			Gesture currentGesture = Gesture.DOWN;
			trainer.addSample(s, currentGesture);

		} else if (mode == Mode.CLASSIFYING) {
			// forward Sample to Classifier
			classifier.classifySample(s);
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


}
