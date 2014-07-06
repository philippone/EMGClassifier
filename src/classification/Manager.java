package classification;

import gui.EMGClassifierGUI;
import classification.SignalReader.ObservableSignalListener;


public class Manager implements ObservableSignalListener {
	
	EMGClassifierGUI gui;
	
	public void setGui(EMGClassifierGUI gui) {
		this.gui = gui;
	}


	SignalReader signalReader;

	public Manager() {
		signalReader = new SignalReader(this);
		// start serial port reading and get signals
		
//		FeatrueExtractor
		
		// classify
		
		Classifier classify = new Classifier();
	}


	@Override
	public void notifyManager(Signal sig1, Signal sig2, Signal sig3) {
		System.out.println("sig1 " + sig1.getValue());
		System.out.println("sig2 " + sig2.getValue());
		System.out.println("sig3 " + sig3.getValue());
		
		gui.notify(sig1, sig2, sig3);
		
	}


}
