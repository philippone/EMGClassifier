package data;

import java.util.LinkedList;
import java.util.List;

public class TrainData {
	
	private String id;
	private List<LabeledFeatureVector> data;
	
	
	public String getId() {
		return id;
	}


	public List<LabeledFeatureVector> getData() {
		return data;
	}

	public TrainData(String id, List<LabeledFeatureVector> data) {
		this.id = id;
		this.data = data;
	}

}
