package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import persistence.DataReaderWriter;
import classification.Gesture;
import data.LabeledFeatureVector;
import data.SignalEntry;
import extractors.MeanAbsoluteValue;
import extractors.MeanAbsoluteValueSlope;
import extractors.SimpleSquareIntegral;
import extractors.SingleFeatureExtractor;

public class Testing {
	
	
	
	public static void main(String[] args) {
		
//		double[] data = new double[10];
//		for (int i = 0; i < 10; i++) {
//			
//			data[i] = Math.random();
//			
//		}
		
//		LabeledFeatureVector lfv = new LabeledFeatureVector("1", data);
//		LinkedList<LabeledFeatureVector> linkedList = new LinkedList<LabeledFeatureVector>();
//		linkedList.add(lfv);
//		TrainData td = new TrainData("yolo", linkedList);
//		LinkedList<TrainData> linkedList2 = new LinkedList<TrainData>();
//		linkedList2.add(td);
//		DataReaderWriter.writeData(linkedList2);
//		
//		
//		DataReaderWriter.readData();
		
//		String q = "a b";
//		String[] split = q.split(" ");
//		System.out.println(split.length + "");
		
//		LinkedList<Integer> que = new LinkedList<Integer>();
		
//		que.add(5);
//		que.add(3);
//		que.add(1);
//		
//		que.poll();
//		
//		for (Integer integer : que) {
//			System.out.println(integer);
//		}
//		
//		System.out.println(Math.sqrt(5 - 4.345));
//		
//		
		
//		Baseline b = new Baseline(3, 5);
//		for (int i = 0; i < 5; i++) {
//			b.update(5,5,5);
//		}
//		
//		System.out.println(b.getAvg()[0]);
//		System.out.println(b.getVar()[0]);
//		
//		b.update(2,2,8);
//		
//		System.out.println(b.getAvg()[0]);
//		System.out.println(b.getVar()[0]);
		
		
//		SampleRecognizer sr = new SampleRecognizer(null);
//		Random r = new Random();
//		
//		for (int i = 0; i < 1000; i++) {
//			double sig = r.nextGaussian();
//			int sig1 = (int)(sig * 1023);
//			sr.recognizeSample(sig1,sig1,sig1);
//		}
//		
//		LinkedList<SignalEntry> sign = new LinkedList<SignalEntry>();
//		for (int i = 0; i < 100; i++) {
//			sign.add(new SignalEntry(true, Gesture.DOWN, new int[]{1213,1223,12}));
//		}
//		
//		DataReaderWriter.writeSignal(sign);
//		ArrayList<SingleFeatureExtractor> ex = new ArrayList<SingleFeatureExtractor>();
//		ex.add(new MeanAbsoluteValue());
//		ex.add(new MeanAbsoluteValueSlope());
//		ex.add(new SimpleSquareIntegral());
//		
//		List<LabeledFeatureVector> trainingLabledFeatureVectors = DataReaderWriter.getTrainingLabledFeatureVectors(30, ex);
//		
//		for (LabeledFeatureVector labeledFeatureVector : trainingLabledFeatureVectors) {
//			System.out.println("Label" + labeledFeatureVector.getLabel());
//		}
		
//		LinkedList<SignalEntry> onsetSignal = DataReaderWriter.getOnsetSignal();
//		int size = onsetSignal.size();
//		System.out.println(size);
		
		byte[] yolo = new byte[]{-1,3,-1,2,-1,3,-1,-1};
		
		int x = 0; 
		
		int[] yoloswag = new int[3];
		for (int i = 0; i < 3; i++) {

			for (int j = 1; j >= 0; j--) {

				yoloswag[i] = (yoloswag[i] << 8) | (yolo[(i*2)+j] & 0xff);
				
			}
			
		}
		
		
//		for (byte b : yolo) {
//			x = (x << 8) |( b & 0xff);
//			System.out.println(Integer.toBinaryString(x));
//		}
//		System.out.println(x);
		
		
	}
	
	
	

}
