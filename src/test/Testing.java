package test;

import java.util.LinkedList;

import persistence.DataReaderWriter;
import data.FeatureVector;
import data.LabeledFeatureVector;
import data.TrainData;

public class Testing {
	
	
	
	public static void main(String[] args) {
		
		double[] data = new double[10];
		for (int i = 0; i < 10; i++) {
			
			data[i] = Math.random();
			
		}
		
//		LabeledFeatureVector lfv = new LabeledFeatureVector("1", data);
//		LinkedList<LabeledFeatureVector> linkedList = new LinkedList<LabeledFeatureVector>();
//		linkedList.add(lfv);
//		TrainData td = new TrainData("yolo", linkedList);
//		LinkedList<TrainData> linkedList2 = new LinkedList<TrainData>();
//		linkedList2.add(td);
//		DataReaderWriter.writeData(linkedList2);
//		
//		
		DataReaderWriter.readData();
		
//		String q = "a b";
//		String[] split = q.split(" ");
//		System.out.println(split.length + "");
		
	}
	
	
	

}
