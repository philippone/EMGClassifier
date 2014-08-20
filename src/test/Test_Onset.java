package test;

import java.util.Iterator;
import java.util.LinkedList;

import classification.Manager;
import classification.SampleRecognizer;
import data.SignalEntry;
import persistence.DataReaderWriter;

public class Test_Onset {

	public static void main(String[] args) {

		LinkedList<SignalEntry> onsetSignal = DataReaderWriter.getOnsetSignal();
		ManagerMock m = new ManagerMock();
		SampleRecognizer sr = new SampleRecognizer(m);

		for (Iterator<SignalEntry> iterator = onsetSignal.iterator(); iterator
				.hasNext();) {
			SignalEntry signalEntry = (SignalEntry) iterator.next();
			sr.recognizeSample(signalEntry.getSignals());

		}
		
		System.out.println(m.samples);
		

	}

}
