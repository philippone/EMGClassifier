package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import classification.Gesture;
import classification.SampleRecognizer;
import data.FeatureVector;
import data.LabeledFeatureVector;
import data.Sample;
import data.SignalEntry;
import data.TrainData;
import extractors.SingleFeatureExtractor;

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

	public static File writeSignal(LinkedList<SignalEntry> record) {

		LocalDateTime now = LocalDateTime.now();
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
		File result = null;
		try {

			result = new File("resources/" + dtf.format(now) + "_recording.csv");
			FileWriter fw = new FileWriter(result);

			for (SignalEntry signalEntry : record) {

				fw.append(signalEntry.toString() + "\n");

			}

			fw.flush();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}

//	public static void readSignal(File file) {
//
//		FileReader fileReader;
//		try {
//			fileReader = new FileReader(file);
//			BufferedReader br = new BufferedReader(fileReader);
//
//			String readLine;
//
//			List<LabeledFeatureVector> datalist = new LinkedList<LabeledFeatureVector>();
//			LinkedList<SignalEntry> signal = new LinkedList<SignalEntry>();
//			while ((readLine = br.readLine()) != null) {
//				String[] split = readLine.split(" ");
//
//			}
//
//			br.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

	public static List<LabeledFeatureVector> getTrainingLabledFeatureVectors(
			int windowSize, ArrayList<SingleFeatureExtractor> extractors,
			File trainingFile) {

		LinkedList<LabeledFeatureVector> trainingLabeledFeatureVectors = new LinkedList<LabeledFeatureVector>();
		boolean sampling = false;
		Sample sample = null;
		ArrayList<LinkedList<Integer>> signals = new ArrayList<LinkedList<Integer>>();
		double label = 0.0;
		File[] listFiles = new File[] { trainingFile };

		if (trainingFile == null) {

			File file = new File("resources/crosscorrelation");
			if (file.exists() && file.isDirectory()) {

				listFiles = file.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".csv");
					}
				});

			}
		}

		for (File sfile : listFiles) {

			try {
				FileReader fr = new FileReader(sfile);

				BufferedReader br = new BufferedReader(fr);
				String line = "";
				while ((line = br.readLine()) != null) {

					String[] split = line.split(",");

					// If sampling just record the signals, else check for
					// onset
					if (!sampling) {
						int parseInt = Integer.parseInt(split[0]);
						if (parseInt == 1) {
							System.out.println("gesture found");

							signals.clear();
							for (int i = 0; i < split.length - 2; i++) {
								signals.add(new LinkedList<Integer>());
								signals.get(i).add(
										Integer.parseInt(split[i + 2]));
							}
							System.out.println(split[0] + "," + split[1] + ","
									+ split[2] + "," + split[3] + ","
									+ split[4]);

							label = Double.parseDouble(split[1]);
							sampling = true;

						} else {

							// skip

						}

					} else {

						if (signals.get(0).size() < windowSize) {

							for (int i = 0; i < split.length - 2; i++) {
								Integer sig = Integer.parseInt(split[i + 2]);
								signals.get(i).add(sig);
							}

						} else {

							System.out.println("Samplesize "
									+ signals.get(0).size());
							sample = new Sample(signals);
							FeatureVector fv = new FeatureVector(
									new double[] {});
							for (SingleFeatureExtractor extractor : extractors) {

								fv.add(extractor.extract(sample));

							}
							trainingLabeledFeatureVectors
									.add(new LabeledFeatureVector(Gesture
											.labelToGesture(label), fv));
							sampling = false;
						}

					}

				}

				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return trainingLabeledFeatureVectors;
	}

	public static LinkedList<SignalEntry> getOnsetSignal() {

		LinkedList<SignalEntry> result = new LinkedList<SignalEntry>();

		File file = new File("resources/onset");

		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {

			try {
				FileReader reader = new FileReader(file2);
				BufferedReader br = new BufferedReader(reader);

				String newLine = "";

				while ((newLine = br.readLine()) != null) {

					String[] split = newLine.split(",");

					boolean onset = split[0] == "1";
					Gesture gesture = (onset ? Gesture.labelToGesture(Double
							.parseDouble(split[1])) : Gesture.UNDEFINED);
					SignalEntry se = new SignalEntry(split[0] == "1", gesture,
							new int[] { Integer.parseInt(split[2]),
									Integer.parseInt(split[3]),
									Integer.parseInt(split[4]) });

					result.add(se);

				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;

	}
}
