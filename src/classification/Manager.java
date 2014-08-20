package classification;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;

import persistence.DataReaderWriter;
import libsvm.svm_model;
import gui.EMGClassifierGUI;
import classification.SampleRecognizer.ObservableSampleListener;
import classification.SignalReader.ObservableSignalListener;
import data.Sample;
import data.Signal;
import data.SignalEntry;

public class Manager implements ObservableSignalListener,
		ObservableSampleListener {

	private SignalReader signalReader;
	private LinkedList<SignalEntry> record;
	private SampleRecognizer sampleRecog;
	private Mode mode;
	private Trainer trainer;

	EMGClassifierGUI gui;
	private Classifier classifier;
	private long time = 0;
	private boolean isNextSignalOnset = false;
	private boolean waitForOnset = false;

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

	private int j = 0;
	private boolean recording = false;
	private int simpleOnsetThreshold;
	private File lastRecordFile = null;
	private boolean useCurrent = true;

	/**
	 * notify manager when new signal appears
	 * */
	@Override
	public void notifySignal(int... sig) {
		j++;

		// notify gui

		// gui.notify(new Signal(sig[0]), new Signal(sig[1]), new
		// Signal(sig[2]));

		switch (mode) {

		case TRAINING:
		case IDLE:
			if (j % 5 == 0) {
				System.out.println("sig1 " + sig[0]);
				System.out.println("sig2 " + sig[1]);
				System.out.println("sig3 " + sig[2]);

			}
		case CLASSIFYING:
			// forward Signals to SampleRecognizer
			sampleRecog.recognizeSample(sig);
			break;
		case RECORDING:
			if (recording) {

				// if (waitForOnset) {
				//
				// for (int i : sig) {
				//
				// if (i >= simpleOnsetThreshold) {
				//
				// isNextSignalOnset = true;
				// waitForOnset = false;
				// break;
				//
				// }
				//
				// }
				//
				//
				// }

				SignalEntry se = null;
				if (isNextSignalOnset) {

					se = new SignalEntry(true, gui.getCurrentGesture(), sig);
					isNextSignalOnset = false;

				} else {

					se = new SignalEntry(false, Gesture.UNDEFINED, sig);

				}
				record.add(se);
			}
			break;

		default:
			break;
		}

	}

	/**
	 * manager will be notified by SampleRecognizer if she recognized a sample
	 * */
	public void notifyManager(Signal sig1, Signal sig2, Signal sig3) {
		// System.out.println("sig1 " + sig1.getValue());
		// System.out.println("sig2 " + sig2.getValue());
		// System.out.println("sig3 " + sig3.getValue());

		// long t = System.currentTimeMillis();
		//
		// if ( t - time > 15) {
		// time = System.currentTimeMillis();
		gui.notify(sig1, sig2, sig3);
		// }
	}

	@Override
	public void notifySample(Sample s) {

		switch (mode) {

		case CLASSIFYING:
			System.out.println("add sample to classify");
			// forward Sample to Classifier
			Gesture g = classifier.classifySample(s);
			gui.showClassifiedGesture(g);
			// TODO notify gui, invoke actoin etc.
			break;
		case IDLE:
			System.out.println("Idle Sample");
			break;
		case RECORDING:

			break;
		case TRAINING:
			System.out.println("add Training Sample");
			// forward Sample to Trainer
			Gesture currentGesture = gui.getCurrentGesture();
			trainer.addSample(s, currentGesture);
			break;

		default:
			break;
		}

		// System.out.println("ajfslkdfj");

	}

	public void changeToClassifyMode() {
		if (mode == Mode.TRAINING) {
			mode = Mode.CLASSIFYING;
			svm_model svm_model = trainer.createModel();
			classifier = new Classifier(svm_model);
		} else if (mode == Mode.RECORDING) {
			mode = Mode.CLASSIFYING;
			if (useCurrent  && lastRecordFile != null) {

				svm_model traingingModel = Util
						.getTraingingModel(lastRecordFile);
				classifier = new Classifier(traingingModel);

			} else {
				
				File file = new File("resources/training");
				File[] listFiles = null;
				if (file.exists() && file.isDirectory()) {


					listFiles = file.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".csv");
						}
					});

				}
				
				if (listFiles != null && listFiles.length > 0) {
					
					svm_model traingingModel = Util
							.getTraingingModel(listFiles[0]);
					classifier = new Classifier(traingingModel);
					
				}
				
			}
		}
		
	}

	public void changeToTrainMode() {
		mode = Mode.TRAINING;
	}

	public void changeToIdleMode() {
		mode = Mode.IDLE;
	}

	public void changeToRecordMode() {
		startRecord();
		mode = Mode.RECORDING;
	}

	public void setDetection() {
		System.out.println("setdetection");
		sampleRecog.setDetection();
		isNextSignalOnset = true;
		// waitForOnset = true;
	}

	public void startRecord() {
		record = new LinkedList<SignalEntry>();
		recording = true;
	}

	public void stopRecord() {
		recording = false;
		lastRecordFile = DataReaderWriter.writeSignal(record);
	}

}
