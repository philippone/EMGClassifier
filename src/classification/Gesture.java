package classification;

public enum Gesture {
	UP 		(1),
	DOWN 	(2),
	LEFT 	(3),
	RIGHT	(4);

	
	private final double value;
	
	
	Gesture(double d) {
		value = d;
	}
	
	
	public static Gesture labelToGesture(double v) {
		// TODO Auto-generated method stub
		return null;
	}

}
