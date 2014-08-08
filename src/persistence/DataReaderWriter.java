package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import data.LabeledFeatureVector;
import data.SignalEntry;
import data.TrainData;

public class DataReaderWriter {

	public static void writeData(List<TrainData> data) {

		for (TrainData trainData : data) {

			try {
				FileWriter fw = new FileWriter(new File("resources/"
						+ trainData.getId() + ".train"));

				List<LabeledFeatureVector> fvectors = trainData.getData();

				for (LabeledFeatureVector labeledFeatureVector : fvectors) {

					fw.append(labeledFeatureVector.getLabel() + " ");

					double[] features = labeledFeatureVector.getFeatures();
					int i = 1;
					for (double d : features) {
						fw.append(i++ + ":" + d + " ");
					}

					fw.append("\n");

				}

				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static List<TrainData> readData() {

		LinkedList<TrainData> result = new LinkedList<TrainData>();

		File file = new File("resources/");
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {

					if (name.endsWith(".train")) {
						return true;
					}
					return false;
				}
			});

			for (File file2 : listFiles) {

				try {
					FileReader fileReader = new FileReader(file2);

					BufferedReader br = new BufferedReader(fileReader);

					String readLine;

					List<LabeledFeatureVector> datalist = new LinkedList<LabeledFeatureVector>();
					while ((readLine = br.readLine()) != null) {

						String[] split = readLine.split(" ");

						if (split.length >= 2) {

							System.out.println(split.length);
							String label = split[0];

							double[] features = new double[split.length - 1];
							for (int i = 1; i < split.length; i++) {
								features[i - 1] = Double.parseDouble(split[i]
										.split(":")[1]);
							}

							LabeledFeatureVector lfv = new LabeledFeatureVector(
									Double.valueOf(label), features);
							datalist.add(lfv);
						}

					}

					TrainData trainData = new TrainData(file2.getName(),
							datalist);
					result.add(trainData);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		return result;

	}
	
	public static void writeSignal(LinkedList<SignalEntry> record){
		
		LocalDateTime now = LocalDateTime.now();
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		
		try {
			
			FileWriter fw = new FileWriter(new File("resources/"
					+ dtf.format(now)  + "_recording.csv"));
			
			for (SignalEntry signalEntry : record) {
				
				fw.append(signalEntry.toString() + "\n");
				
			}
			
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void readSignal(File file){
		
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			
			String readLine;
			
			List<LabeledFeatureVector> datalist = new LinkedList<LabeledFeatureVector>();
			LinkedList<SignalEntry> signal = new LinkedList<SignalEntry>();
			while ((readLine = br.readLine()) != null) {
				String[] split = readLine.split(" ");
				
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		


		
	} 

}
