package gui;


import java.util.LinkedList;

import classification.Gesture;
import classification.Manager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import classification.Manager;
import data.Signal;

public class EMGClassifierGUI extends Application {

	private static final int CHARTRATE = 250;
	private static final int CHART_MAXVALUE = 1024;
	private static Manager man;
	private Series<Number, Number> sensor1Series;
	private Series<Number, Number> sensor2Series;
	private Series<Number, Number> sensor3Series;
	private int i = 0;
	private Gesture currentGesture = Gesture.UNDEFINED;
	private ToggleGroup tgGesture;
	private ToggleButton gestureUpButton;
	private ToggleButton gestureDownButton;
	private ToggleButton gestureRightButton;
	private ToggleButton gestureLeftButton;
	private ToggleButton gestureEnterButton;
	private ToggleButton gestureBackButton;

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
		sensor1.getStylesheets().add(".chart-series-line {" + "    -fx-stroke-width: 2px;"
				+ "}");
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

		// Vbox with buttons to select mode

		// GridPane modePane = new GridPane();
		VBox modeBox = new VBox();
		modeBox.setSpacing(5);

		ToggleGroup tgMode = new ToggleGroup();
		ToggleButton modeIdleButton = new ToggleButton("Idle");
		ToggleButton modeTrainingButton = new ToggleButton("Training");
		ToggleButton modeClassificationButton = new ToggleButton(
				"Classification");
		ToggleButton modeRecordButton = new ToggleButton("Record");

		modeIdleButton.setToggleGroup(tgMode);
		modeTrainingButton.setToggleGroup(tgMode);
		modeClassificationButton.setToggleGroup(tgMode);
		modeRecordButton.setToggleGroup(tgMode);

		modeIdleButton.setMaxWidth(Double.MAX_VALUE);
		modeIdleButton.setMaxHeight(Double.MAX_VALUE);
		modeTrainingButton.setMaxWidth(Double.MAX_VALUE);
		modeTrainingButton.setMaxHeight(Double.MAX_VALUE);
		modeClassificationButton.setMaxWidth(Double.MAX_VALUE);
		modeClassificationButton.setMaxHeight(Double.MAX_VALUE);
		modeRecordButton.setMaxWidth(Double.MAX_VALUE);
		modeRecordButton.setMaxHeight(Double.MAX_VALUE);

		modeBox.getChildren().add(modeIdleButton);
		modeBox.getChildren().add(modeTrainingButton);
		modeBox.getChildren().add(modeClassificationButton);
		modeBox.getChildren().add(modeRecordButton);

		VBox.setVgrow(modeIdleButton, Priority.ALWAYS);
		VBox.setVgrow(modeTrainingButton, Priority.ALWAYS);
		VBox.setVgrow(modeClassificationButton, Priority.ALWAYS);
		VBox.setVgrow(modeRecordButton, Priority.ALWAYS);

		modeIdleButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						man.changeToIdleMode();
					}
				});
		modeTrainingButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						man.changeToTrainMode();
					}
				});
		modeClassificationButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						man.changeToClassifyMode();
					}
				});
		modeRecordButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						man.changeToRecordMode();
					}
				});
		
		modeRecordButton.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if (!arg2) {
					man.stopRecord();
				}
			}
		});

		// modeBox.fillWidthProperty().set(true);

		VBox gestureBox = new VBox();
		gestureBox.setSpacing(5);
		gestureBox.setPrefWidth(200);
		tgGesture = new ToggleGroup();

		gestureUpButton = new ToggleButton("Up");
		gestureDownButton = new ToggleButton("Down");
		gestureRightButton = new ToggleButton("Right");
		gestureLeftButton = new ToggleButton("Left");
		gestureEnterButton = new ToggleButton("Enter");
		gestureBackButton = new ToggleButton("Undefined");

		gestureBackButton.setToggleGroup(tgGesture);
		gestureEnterButton.setToggleGroup(tgGesture);
		gestureLeftButton.setToggleGroup(tgGesture);
		gestureRightButton.setToggleGroup(tgGesture);
		gestureUpButton.setToggleGroup(tgGesture);
		gestureDownButton.setToggleGroup(tgGesture);

		gestureBackButton.setMaxWidth(Double.MAX_VALUE);
		gestureBackButton.setMaxHeight(Double.MAX_VALUE);
		gestureEnterButton.setMaxWidth(Double.MAX_VALUE);
		gestureEnterButton.setMaxHeight(Double.MAX_VALUE);
		gestureLeftButton.setMaxWidth(Double.MAX_VALUE);
		gestureLeftButton.setMaxHeight(Double.MAX_VALUE);
		gestureRightButton.setMaxWidth(Double.MAX_VALUE);
		gestureRightButton.setMaxHeight(Double.MAX_VALUE);
		gestureUpButton.setMaxWidth(Double.MAX_VALUE);
		gestureUpButton.setMaxHeight(Double.MAX_VALUE);
		gestureDownButton.setMaxWidth(Double.MAX_VALUE);
		gestureDownButton.setMaxHeight(Double.MAX_VALUE);

		VBox.setVgrow(gestureBackButton, Priority.ALWAYS);
		VBox.setVgrow(gestureEnterButton, Priority.ALWAYS);
		VBox.setVgrow(gestureLeftButton, Priority.ALWAYS);
		VBox.setVgrow(gestureRightButton, Priority.ALWAYS);
		VBox.setVgrow(gestureUpButton, Priority.ALWAYS);
		VBox.setVgrow(gestureDownButton, Priority.ALWAYS);

		gestureBox.getChildren().add(gestureUpButton);
		gestureBox.getChildren().add(gestureDownButton);
		gestureBox.getChildren().add(gestureLeftButton);
		gestureBox.getChildren().add(gestureRightButton);
		gestureBox.getChildren().add(gestureEnterButton);
		gestureBox.getChildren().add(gestureBackButton);

		gestureUpButton.setUserData(Gesture.UP);
		gestureDownButton.setUserData(Gesture.DOWN);
		gestureLeftButton.setUserData(Gesture.LEFT);
		gestureRightButton.setUserData(Gesture.RIGHT);
		gestureEnterButton.setUserData(Gesture.ENTER);
		gestureBackButton.setUserData(Gesture.UNDEFINED);

		tgGesture.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {

					@Override
					public void changed(ObservableValue<? extends Toggle> arg0,
							Toggle arg1, Toggle arg2) {

						if (arg2 == null) {
							currentGesture = Gesture.UNDEFINED;
						} else {
							currentGesture = (Gesture) arg2.getUserData();
						}

					}
				});
		
		Button manualDetectionButton = new Button("Detection");
		manualDetectionButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				man.setDetection();
			}
		});

		root.addColumn(0, sensor1, sensor2, sensor3);
		root.addColumn(1, modeBox, gestureBox, manualDetectionButton);

		GridPane.setMargin(modeBox, new Insets(5));
		GridPane.setMargin(gestureBox, new Insets(5));

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
					Thread.sleep(4000);

					System.out.println("Testing");
					while (true) {

						Thread.sleep(15);

						// Signal sig1 = new Signal();
						// Signal sig2 = new Signal();
						// Signal sig3 = new Signal();

						// sig1.setValue((int) (Math.random() * 100));
						// sig2.setValue((int) (Math.random() * 100));
						// sig3.setValue((int) (Math.random() * 100));

						man.notifySignal((int) (Math.random() * 1023),
								(int) (Math.random() * 1023),
								(int) (Math.random() * 1023));

					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});

//		t.start();

		launch(args);

		System.exit(0);
		
//		t.interrupt();


	}
	

	public void notify(final Signal sig1, final Signal sig2, final Signal sig3) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

//				drawSignals(sig1, sig2, sig3);

			}

		});

	}

	private void drawSignals(final Signal sig1, final Signal sig2,
			final Signal sig3) {

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

	public Gesture getCurrentGesture() {
		return currentGesture;
	}

	public void showClassifiedGesture(final Gesture gesture) {

		System.out.println("showing gesture in gui");
		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				switch (gesture) {
				case DOWN:
					tgGesture.selectToggle(gestureDownButton);
					break;
				case ENTER:
					tgGesture.selectToggle(gestureEnterButton);
					break;
				case LEFT:
					tgGesture.selectToggle(gestureLeftButton);
					break;
				case RIGHT:
					tgGesture.selectToggle(gestureRightButton);
					break;
				case UNDEFINED:
					tgGesture.selectToggle(gestureBackButton);
					break;
				case UP:
					tgGesture.selectToggle(gestureUpButton);
					break;

				default:
					break;
				}
			}
		});
	}

}
