package classification;

public enum Gesture {
	UP 		(1),
	DOWN 	(2),
	LEFT 	(3),
	RIGHT	(4),
	ENTER (5),
	UNDEFINED (6);

	
	private final double value;
	
	
	Gesture(double d) {
		value = d;
	}
	
	public double getValue(){
		return value;
	}
	
	public static Gesture labelToGesture(double v) {
		return Gesture.values()[(int)v - 1];
	}

}
