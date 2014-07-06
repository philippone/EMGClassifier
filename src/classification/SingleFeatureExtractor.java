package classification;

import data.FeatureVector;

public interface SingleFeatureExtractor {
	
	public FeatureVector extract(Sample sample);

}
