package test;

import java.util.LinkedList;

import data.Baseline;

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
		
		LinkedList<Integer> que = new LinkedList<Integer>();
		
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
		
		Baseline b = new Baseline(3, 5);
		for (int i = 0; i < 5; i++) {
			b.update(5,5,5);
		}
		
		System.out.println(b.getAvg()[0]);
		System.out.println(b.getVar()[0]);
		
		b.update(2,2,8);
		
		System.out.println(b.getAvg()[0]);
		System.out.println(b.getVar()[0]);
	}
	
	
	

}
