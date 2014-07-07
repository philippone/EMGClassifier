package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import classification.Manager;
import classification.Signal;

public class EMGClassifierGUI extends Application {

	private static final int CHARTRATE = 250;
	private static final int CHART_MAXVALUE = 1024;
	private static Manager man;
	private Series<Number, Number> sensor1Series;
	private Series<Number, Number> sensor2Series;
	private Series<Number, Number> sensor3Series;
	private int i = 0;

	@Override
	public void start(Stage arg0) throws Exception {

		GridPane root = new GridPane();
		Scene scene = new Scene(root, 1000, 800);
		GridPane.setHgrow(root, Priority.ALWAYS);
		GridPane.setVgrow(root, Priority.ALWAYS);
		root.gridLinesVisibleProperty().set(true);

		LineChart<Number, Number> sensor1 = new LineChart<Number, Number>(
				new NumberAxis(), new NumberAxis());
		LineChart<Number, Number> sensor2 = new LineChart<Number, Number>(
				new NumberAxis(), new NumberAxis());
		LineChart<Number, Number> sensor3 = new LineChart<Number, Number>(
				new NumberAxis(), new NumberAxis());

		GridPane.setVgrow(sensor1, Priority.SOMETIMES);
		GridPane.setVgrow(sensor2, Priority.SOMETIMES);
		GridPane.setVgrow(sensor3, Priority.SOMETIMES);
		GridPane.setHgrow(sensor1, Priority.SOMETIMES);
		GridPane.setHgrow(sensor2, Priority.SOMETIMES);
		GridPane.setHgrow(sensor3, Priority.SOMETIMES);

		sensor1.setTitle("Sensor 1 Input");
		sensor1.setAnimated(false);
		sensor1.setCreateSymbols(false);
		((NumberAxis) sensor1.getXAxis()).setUpperBound(CHARTRATE);
		((NumberAxis) sensor1.getYAxis()).setUpperBound(CHART_MAXVALUE);
		((NumberAxis) sensor1.getXAxis()).setAutoRanging(false);
		((NumberAxis) sensor1.getYAxis()).setAutoRanging(false);

		sensor2.setTitle("Sensor 2 Input");
		sensor2.setAnimated(false);
		sensor2.setCreateSymbols(false);
		((NumberAxis) sensor2.getXAxis()).setUpperBound(CHARTRATE);
		((NumberAxis) sensor2.getYAxis()).setUpperBound(CHART_MAXVALUE);
		((NumberAxis) sensor2.getXAxis()).setAutoRanging(false);
		((NumberAxis) sensor2.getYAxis()).setAutoRanging(false);

		sensor3.setTitle("Sensor 3 Input");
		sensor3.setAnimated(false);
		sensor3.setCreateSymbols(false);
		((NumberAxis) sensor3.getXAxis()).setUpperBound(CHARTRATE);
		((NumberAxis) sensor3.getYAxis()).setUpperBound(CHART_MAXVALUE);
		((NumberAxis) sensor3.getXAxis()).setAutoRanging(false);
		((NumberAxis) sensor3.getYAxis()).setAutoRanging(false);

		sensor1Series = new Series<Number, Number>();
		sensor2Series = new Series<Number, Number>();
		sensor3Series = new Series<Number, Number>();

		ObservableList<XYChart.Data<Number, Number>> sensor1Data = FXCollections
				.observableArrayList();
		ObservableList<XYChart.Data<Number, Number>> sensor2Data = FXCollections
				.observableArrayList();
		ObservableList<XYChart.Data<Number, Number>> sensor3Data = FXCollections
				.observableArrayList();

		sensor1Series.setData(sensor1Data);
		sensor2Series.setData(sensor2Data);
		sensor3Series.setData(sensor3Data);

		sensor1.setData(FXCollections.observableArrayList(sensor1Series));
		sensor2.setData(FXCollections.observableArrayList(sensor2Series));
		sensor3.setData(FXCollections.observableArrayList(sensor3Series));

		root.addColumn(0, sensor1, sensor2, sensor3);

		arg0.setScene(scene);
		arg0.show();

		man.setGui(this);
	}

	public static void main(String[] args) {

		man = new Manager();
		System.out.println("after launch");
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(2000);

					System.out.println("Testing");
					while (true) {
						Thread.sleep(20);

//						Signal sig1 = new Signal();
//						Signal sig2 = new Signal();
//						Signal sig3 = new Signal();

//						sig1.setValue((int) (Math.random() * 100));
//						sig2.setValue((int) (Math.random() * 100));
//						sig3.setValue((int) (Math.random() * 100));

						man.notifySignal((int) (Math.random() * 100), (int) (Math.random() * 100), (int) (Math.random() * 100));

					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

//		t.start();

		launch(args);

//		t.interrupt();

	}

	public void notify(final Signal sig1, final Signal sig2, final Signal sig3) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (sig1 != null && sig2 != null && sig3 != null)
					drawSignals(sig1, sig2, sig3);

			}

		});

	}

	private void drawSignals(Signal sig1, Signal sig2, Signal sig3) {

		if (i >= CHARTRATE) {

			sensor1Series.getData().clear();
			sensor2Series.getData().clear();
			sensor3Series.getData().clear();

			i = 0;
		}

		sensor1Series.getData().add(
				new XYChart.Data<Number, Number>(i, sig1.getValue()));
		sensor2Series.getData().add(
				new XYChart.Data<Number, Number>(i, sig2.getValue()));
		sensor3Series.getData().add(
				new XYChart.Data<Number, Number>(i, sig3.getValue()));
		i++;

	}

}
