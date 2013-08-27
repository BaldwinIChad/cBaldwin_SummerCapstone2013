package clusteringAlgoritm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Clumper {
	private final int NUMBER_OF_ERAS = 3;
	private Random gen = new Random();
	
	MidiDataCentroid[] centroids = new MidiDataCentroid[NUMBER_OF_ERAS];
	
	
	public void addDataPoint(MidiFileData d) {
		if(!data.containsKey(d.getSongTitle())) {
			MidiDataCentroid centroid = findNearestCentroid(d);
			centroid.addPoint(d);
		}
	}
	
	private MidiDataCentroid findNearestCentroid(MidiFileData data) {
		ArrayList<MidiDataCentroid> shortest = new ArrayList<>();
		double shortestDistance = Double.MAX_VALUE;
		
		for(MidiDataCentroid c : centroids) {
			double distance = c.getDistance(data);
			
			if(distance < shortestDistance){
				shortest.clear();
				shortest.add(c);
				shortestDistance = distance;
			}else if(distance == shortestDistance){
				shortest.add(c);
			}
		}
		
		MidiDataCentroid toReturn;
		if(shortest.size() > 1){
			toReturn = shortest.get(gen.nextInt(shortest.size()));
		} else toReturn = shortest.get(0);
		
		return toReturn;
	}
}
