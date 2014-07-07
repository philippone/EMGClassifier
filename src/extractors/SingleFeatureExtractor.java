package extractors;

import data.FeatureVector;
import data.Sample;

public interface SingleFeatureExtractor {
	
	public FeatureVector extract(Sample sample);

}
