package test;

import classification.Manager;
import data.Sample;

public class ManagerMock extends Manager {

	int samples = 0;
	
	@Override
	public void notifySample(Sample s) {
		System.out.println("Sample notified");
		samples++;
	}

}
