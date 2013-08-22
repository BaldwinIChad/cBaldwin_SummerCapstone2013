package clusteringAlgoritm;

public class MidiDataCentroid {
	String clusterName;
	boolean hasMoved = true;
	
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
