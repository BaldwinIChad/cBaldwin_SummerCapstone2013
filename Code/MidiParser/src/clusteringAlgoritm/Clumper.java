package clusteringAlgoritm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Clumper {
	private final int NUMBER_OF_ERAS = 3;
	
	private Random gen = new Random();
	
	MidiDataCentroid[] centroids = new MidiDataCentroid[NUMBER_OF_ERAS];
	
	public void addDataPoint(MidiFileData d) {
		generateRandomCentroids();
		
		while(hasCentroidMoved()){
			MidiDataCentroid center = findNearestCentroid(d);
			center.addMidiData(d);
		}
	}
	
	public void addData(MidiFileData[] d) {
		
	}
	
	private boolean hasCentroidMoved() {
		boolean hasMoved = false;
		
		for(MidiDataCentroid c : centroids){
			if(c.hasMoved() == true){
				hasMoved = true;
				break;
			}
		}
		
		return hasMoved;		
	}
	
	private int getCentroidIndexContainPoint(MidiFileData d){
		int index = -1;
		
		for(int i = 0; i < centroids.length; i++) {
			if(centroids[i].containsSongTitle(d.getSongTitle())){
				index = i;
				break;				
			}
		}
		
		return index;
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
