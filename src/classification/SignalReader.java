package classification;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import data.Signal;

public class SignalReader implements SerialPortEventListener {

	public interface ObservableSignalListener {
		public void notifySignal(int... sig);
	}

	Manager manager;
	private Signal sig1;
	private Signal sig2;
	private Signal sig3;

	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/cu.usbmodem641",
			"/dev/tty.usbmodem641", "/dev/cu.usbmodem441",
			"/dev/tty.usbmodem441", "/dev/cu.usbmodem411",
			"/dev/tty.usbmodem411", "/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", "COM2", "COM1", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	private InputStream inputStream;
	private boolean guiInit = false;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public SignalReader(Manager manager) {
		this.manager = manager;
		sig1 = new Signal();
		sig2 = new Signal();
		sig3 = new Signal();

		initialize();
		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("Reading Started");

		try {
			Thread.sleep(3000);
			output.write(123);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initialize() {
		// the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		// System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			// input = new BufferedReader(new InputStreamReader(
			// serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

			serialPort.enableReceiveThreshold(6);
			serialPort.enableReceiveTimeout(Integer.MAX_VALUE);

			inputStream = serialPort.getInputStream();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			try {
				System.out.println("close reader");
				sendOutput();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public void sendOutput() {
		// TODO Auto-generated method stub
		try {
			output.write(10);
			output.flush();
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public void serialEvent(SerialPortEvent oEvent) {

		if (isGuiInit()) {
			switch (oEvent.getEventType()) {
			case SerialPortEvent.BI:
			case SerialPortEvent.OE:
			case SerialPortEvent.FE:
			case SerialPortEvent.PE:
			case SerialPortEvent.CD:
			case SerialPortEvent.CTS:
			case SerialPortEvent.DSR:
			case SerialPortEvent.RI:
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.DATA_AVAILABLE:
				byte[] readBuffer = new byte[8];

				try {

					while (inputStream.available() > 0) {

						Thread.sleep(2);
						int total = 0;
						int read = 0;
						while (total < 8
								&& (read = inputStream.read(readBuffer, total,
										8 - total)) >= 0) {
							total += read;
						}

						// int numBytes = inputStream.read(readBuffer, 0, 6);
						// if (numBytes == 6) {
						System.out.print("bytes: " + read + " - ");
						for (byte b : readBuffer) {
							System.out.print(b + " ");
						}
						System.out.println(" ");

						// String s = new String(readBuffer); //
						// System.out.print(s);
						// convertSignal(s);
						// }
					}

				} catch (IOException e) {
					System.out.println(e);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			// if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			// try {
			// String inputLine = input.readLine();
			// convertSignal(inputLine);
			// // System.out.println("ReadPort: " + inputLine);
			// } catch (Exception e) {
			// System.err.println(e.toString());
			// }
			// }
			// Ignore all the other eventTypes, but you should consider the
			// other
			// ones.
		}
	}

	private void convertSignal(String inputLine) {
		// System.out.println("convert: " + inputLine + " (end)");
		inputLine.trim();
		String[] values = inputLine.split(",");

		// System.out.println("valued: " + values[0] + ", "
		// + values[1] + ", " + values[2]);

		try {
			sig1.setValue(Integer.parseInt(values[0].trim()));
			sig2.setValue(Integer.parseInt(values[1].trim()));
			sig3.setValue(Integer.parseInt(values[2].trim()));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// System.out.println("notify");

		manager.notifySignal(sig1.getValue(), sig2.getValue(), sig3.getValue());

	}

	public boolean isGuiInit() {
		return guiInit;
	}

	public void setGuiInit(boolean guiInit) {
		this.guiInit = guiInit;
	}

}
