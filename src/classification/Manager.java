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
		mode = mode.CLASSIFYING;
		svm_model svm_model = trainer.createModel();
		classifier = new Classifier(svm_model);
	}
	
	public void changeToTrainMode() {
		mode = mode.TRAINING;
	}

}
