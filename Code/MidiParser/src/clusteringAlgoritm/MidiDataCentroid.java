package clusteringAlgoritm;

import java.util.ArrayList;

public class MidiDataCentroid {
	String clusterName;
	boolean hasMoved = true;
	ArrayList<MidiFileData> cluster = new ArrayList<>();
	
	public MidiDataCentroid(String name) {
		this.clusterName = name;
	}
	
	public MidiDataCentroid(String name, MidiFileData...datas ) {
		this.clusterName = name;
	}
	
	public boolean hasMoved() {
		boolean moved = hasMoved;
		hasMoved = false;
		return moved;
	}
}
