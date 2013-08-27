package clusteringAlgoritm;

import java.util.ArrayList;

public class MidiDataCentroid extends MidiFileData{
	String clusterName;
	boolean hasMoved = true;
	
	ArrayList<MidiFileData> pointsInCluster = new ArrayList<>();
	
	public MidiDataCentroid(String name) {
		this.clusterName = name;
	}
	
	public MidiDataCentroid(String name, MidiFileData...datas ) {
		this.clusterName = name;
	}
	
	public boolean addMidiData(MidiFileData d){
		boolean added = false;
		
		if(!pointsInCluster.contains(d)){
			pointsInCluster.add(d);
			repositionCentroid();
			added = true;
		}
		return true;
	}
	
	public void repositionCentroid(){
		this.BPM = getAverageBPM();
		this.averageNoteDuration = getAverageNoteDuration();
	}
	
	private double getAverageBPM(){
		int total = pointsInCluster.size();
		double bpm = 0.0;
		
		for(MidiFileData d : pointsInCluster) {
			bpm += d.getBPM();
		}
		
		return bpm / total;
	}
	
	private double getAverageNoteDuration(){
		int total = pointsInCluster.size();
		double averageNoteDuration = 0.0;
		
		for(MidiFileData d : pointsInCluster) {
			
		}
	}
	
	public boolean hasMoved() {
		boolean moved = hasMoved;
		hasMoved = false;
		return moved;
	}
	
	
}
