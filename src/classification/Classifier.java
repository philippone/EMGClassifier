package classification;

import libsvm.svm_model;

public class Classifier {

	private svm_model model;
	
	public Classifier(svm_model model) {
		this.model = model;
	}

}
